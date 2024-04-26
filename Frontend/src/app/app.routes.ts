import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { authGuard } from './guards/auth-guard.guard';
import { HomepageComponent } from './homepage/homepage.component';
import { AdminpageComponent } from './adminpage/adminpage.component';
import { PasswordChangeComponent } from './password-change/password-change.component';

export const routes: Routes = [
  { path: '', title: 'App Home Page', component: HomepageComponent },
  { path: 'login', title: 'App Login Page', component: LoginComponent },
  { path: 'admin', title: 'Admin Page', component: AdminpageComponent, canActivate: [authGuard] },
  {
    path: 'register',
    title: 'App Register Page',
    component: RegisterComponent,
  },
  {
    path: 'dashboard',
    title: 'App Dashboard Page',
    component: DashboardComponent,
    canActivate: [authGuard]
  },
  {path:'change',
    title: 'Change Password',
    component: PasswordChangeComponent,
    canActivate: [authGuard]
  }
];
