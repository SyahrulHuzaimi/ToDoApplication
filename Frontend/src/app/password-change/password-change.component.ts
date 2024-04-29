import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { HttpClient } from '@angular/common/http';




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


  handleChangePassword() {
    //check password inputs => send http request => valid ? logout : display error,stay there
    this.hasError = false;  //reset
    if(this.changeForm.get('oldPassword')?.errors?.['required'] || this.changeForm.get('password')?.errors?.['required']){
      this.hasError = true;
      this.errorMessage = "All fields must be filled";
      return;
    }
    if(this.changeForm?.errors?.['mismatch']){
      this.hasError = true;
      this.errorMessage = "New password must match";
      return;
    }
    if(!this.changeForm?.valid){
      this.hasError = true;
      this.errorMessage = "Error somewhere";
      return;
    }
    console.log("Changing password");
    console.log(this.changeForm.value.confirmPassword);
  }
  constructor(private http: HttpClient) {
    this.changeForm = new FormGroup(
      {
        oldPassword: new FormControl("", [Validators.required]),
        password: new FormControl("", [Validators.required]),
        confirmPassword: new FormControl("", [Validators.required]),
      },{
        validators: this.passwordMatchValidator
      });
  }

  passwordMatchValidator(control: AbstractControl){
    return control.get('password')?.value === control.get('confirmPassword')?.value
    ? null
    : {mismatch: true};
  }
}
