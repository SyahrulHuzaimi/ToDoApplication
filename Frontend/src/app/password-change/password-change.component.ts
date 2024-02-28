import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
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
  constructor(private http: HttpClient, private formBuilder: FormBuilder) {}

  changeFrom = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
    repeatPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
  });

  
}
