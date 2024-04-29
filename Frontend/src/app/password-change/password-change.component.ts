import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';




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
      'username' : localStorage.getItem('Username'),
      'password' : this.changeForm.value.oldPassword,
      'newPassword' : this.changeForm.value.password
    });

    console.log(message);

    console.log("Changing password");
    console.log(this.changeForm.value.confirmPassword);
    /* this.sub = this._authService.refreshToken().subscribe({
      next: (token) => {
        if(token){
          // Token is refreshed, or not needed to be refreshed

          let httpRequest: Observable<any> = this.http.post(
            this._authService.getChangeUrl(),

          );


          console.log(this.taskForm.value);
          let httpRequest: Observable<any> = this.http.post(
            this._authService.getTaskUrl(),
            this.taskForm.value,
            { headers: this._authService.getAuthHeader() }
          );
          
          httpRequest.subscribe({
            next: (response: TasksInt) => {
              this.tasks = response;
              this.taskForm.patchValue({ task: '' }); // Reset form value here on success
            },
            error: (error) => {
              console.log(error.value);
            },
          });


        }
        
      },
      error: (error) => {
        console.error('Failed to refresh token', error);
      }
    }); */

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


  constructor(private _authService: AuthService, private router: Router, private http: HttpClient) {
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
