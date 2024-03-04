import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MaterialModule } from '../material/material.module';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  hasError = false;
  errorMessage = '';
  hide = true;
  content = '';

  constructor(
    private _authService: AuthService,
    private http: HttpClient,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  loginForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  handleSubmitLogin() {
    let httpRequest: Observable<any> = this.http.post(
      this._authService.getLoginUrl(),
      this.loginForm.value
    );
    httpRequest.subscribe({
      next: (response) => {
        if (response.accessToken) {
          console.log('Value: ');
          console.log(response.admin);
          localStorage.setItem('JWTToken', response.accessToken);
          localStorage.setItem('RefreshToken', response.refreshToken);
          localStorage.setItem('expiration', response.expireDate);

          this._authService.setAdmin(response.admin);
          this.hasError = false;
          this._authService.setLogin(true);

          this.router.navigateByUrl('/dashboard');
        }
      },
      error: (error) => {
        console.log('Bad Request');
        this.hasError = true;
        this.errorMessage = error.error.responseMessage;
      },
    });
  }
}
