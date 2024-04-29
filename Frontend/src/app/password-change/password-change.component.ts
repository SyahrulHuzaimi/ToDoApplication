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
  hide = true;
  content = '';
  changeForm: FormGroup;


  handleChangePassword() {
    console.log("Changing password");
    console.log(this.changeForm.value.confirmPassword);
    if(!this.changeForm?.valid){
      console.log("Invalid form");
    }else{
      console.log("Valid form");
    }
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
