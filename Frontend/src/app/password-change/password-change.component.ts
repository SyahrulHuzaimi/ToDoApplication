import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { HttpClient } from '@angular/common/http';


const checkEquality = (source : string, target : string) => {
  return (group: AbstractControl): ValidationErrors | null => {
    const sourceValue = group.get(source)?.value;
    const targetValue = group.get(target)?.value;
    if(sourceValue !== targetValue){
      return{
        notEqual: true
      }
    }
    return null;
  } 
}

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


  handleChangePassword() {
    console.log("Changing password");
  }
  constructor(private http: HttpClient, private formBuilder: FormBuilder) {}

  
  changeForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
    repeatNewPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
  }, [checkEquality('newPassword', 'repeatNewPassword')]);
}
