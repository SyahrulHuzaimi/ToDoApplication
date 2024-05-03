import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { DialogLogoutComponent } from '../dialog-logout/dialog-logout.component';




@Component({
  selector: 'app-password-change',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './password-change.component.html',
  styleUrl: './password-change.component.css',
})
export class PasswordChangeComponent {

  hasError = false;
  errorMessage = '';
  hide1 = true;
  hide2 = true;
  hide3 = true;
  content = '';
  changeForm: FormGroup;
  sub: Subscription;


  handleChangePassword() {
    //check password inputs => send http request => valid ? logout : display error,stay there
    this.hasError = false;  //reset
    if (this.checkError()) {
      return;
    }

    let message = JSON.stringify({
      'username': localStorage.getItem('Username'),
      'password': this.changeForm.value.oldPassword,
      'newPassword': this.changeForm.value.password
    });

    console.log(message);

    console.log("Changing password");
    console.log(this.changeForm.value.confirmPassword);
    this.sub = this._authService.refreshToken().subscribe({
      next: (token) => {
        if (token) {
          // Token is refreshed, or not needed to be refreshed

          let httpRequest: Observable<any> = this.http.post(
            this._authService.getChangeUrl(),
            message,
            { headers:  this._authService.getAuthHeaderJSON()}
          );

          httpRequest.subscribe({
            next: (response) => {
              //Prompt the user to logout?, maybe call the logout component
              this.openDialog();
              console.log("ChPa Succ");
            },
            error: (error) => {
              //Just display error message
              this.hasError = true;
              this.errorMessage = "Wrong original password.";
              console.log("ChPa Error");
            },
          });
        }
      },
      error: (error) => {
        console.error('Failed to refresh token', error);
      }
    });

  }

  checkError() {
    if (this.changeForm.get('oldPassword')?.errors?.['required'] || this.changeForm.get('password')?.errors?.['required']) {
      this.hasError = true;
      this.errorMessage = "All fields must be filled";
      return true;
    }
    if (this.changeForm?.errors?.['mismatch']) {
      this.hasError = true;
      this.errorMessage = "New password must match";
      return true;
    }
    if (!this.changeForm?.valid) {
      this.hasError = true;
      this.errorMessage = "Error somewhere";
      return true;
    }
    return false;
  }

  openDialog(){
    this.dialog.open(DialogLogoutComponent);
  }


  constructor(private _authService: AuthService, private router: Router, private http: HttpClient, public dialog: MatDialog) {
    this.changeForm = new FormGroup(
      {
        oldPassword: new FormControl("", [Validators.required]),
        password: new FormControl("", [Validators.required]),
        confirmPassword: new FormControl("", [Validators.required]),
      }, {
      validators: this.passwordMatchValidator
    });
  }

  passwordMatchValidator(control: AbstractControl) {
    return control.get('password')?.value === control.get('confirmPassword')?.value
      ? null
      : { mismatch: true };
  }
}
