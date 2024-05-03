import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { BrowserModule } from '@angular/platform-browser';
import { MaterialModule } from './material/material.module';
import { ToolboxComponent } from './toolbox/toolbox.component';
import { DialogLogoutComponent } from './dialog-logout/dialog-logout.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    //BrowserModule,
    MaterialModule,
    RouterOutlet,
    RouterLink,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    ToolboxComponent,
    DialogLogoutComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
}
