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
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  registerForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  handleSubmitRegister() {
    console.log(this.registerForm.value);
    this._authService.register(this.registerForm).subscribe({
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
