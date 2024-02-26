import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MaterialModule } from '../material/material.module';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  hasError = false;
  errorMessage = '';
  hide = true;

  constructor(
    private _authService: AuthService,
    private http: HttpClient,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  registerForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  handleSubmitRegister() {
    let httpRequest: Observable<any> = this.http.post(
      this._authService.getRegisterUrl(),
      this.registerForm.value
    );
    httpRequest.subscribe({
      next: (response) => {
        console.log(response.responseMessage);
        this.hasError = false;
        this.router.navigateByUrl('/login');
      },
      error: (error) => {
        console.log('Bad Request');
        console.log(error.error.responseMessage);
        this.hasError = true;
        this.errorMessage = error.error.responseMessage;
      },
    });
  }
}
