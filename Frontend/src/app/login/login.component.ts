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
  content ='';

  constructor(
    private _authService: AuthService,
    private http: HttpClient,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  loginForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  handleSubmitLogin() {
    console.log(this.loginForm.value);
    this._authService.login(this.loginForm).subscribe({
      next: (response) => {
        if (response.accessToken) {
          console.log('Value: ');
          console.log(response.accessToken);
          localStorage.setItem('JWTToken', response.accessToken);

          this.hasError = false;

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

  hello() {
    this.http.get<any>('http://localhost:8080/hello').subscribe(data => {
      this.content = data;
      console.log("Value: ");
      console.log(this.content);
    })

    console.log("xyz1");

    /* this._authService
      .hello()
      .subscribe((response) => (this.content = response));
    console.log('Value: ');
    console.log(this.content); */
   

    /* this._authService.hello().subscribe((response) => {
      console.log('Value: ');
      console.log(response.responseMessage);
    }); */
  }
}
