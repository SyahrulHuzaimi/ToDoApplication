import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MaterialModule } from '../material/material.module';
import { AuthService } from '../services/auth.service';
import { Subscription } from 'rxjs';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-toolbox',
  standalone: true,
  imports: [RouterLink, MatMenuModule, MatButtonModule, MatToolbarModule],
  templateUrl: './toolbox.component.html',
  styleUrl: './toolbox.component.css'
})
export class ToolboxComponent implements OnInit, OnDestroy{

  constructor(
    private _authService: AuthService,
    private router: Router,
  ) {}

  isLoggedIn: boolean;
  subcription1: Subscription;
  subcription2: Subscription;
  isAdmin: boolean;

  ngOnDestroy(): void {
    this.subcription1 && this.subcription1.unsubscribe();
    this.subcription2 && this.subcription2.unsubscribe();
  }

  ngOnInit(): void {  
    this.isLoggedIn = this._authService.getLoggedInValue();
    this.isAdmin = this._authService.getAdminValue();
    this.subcription1 = this._authService.loggedIn.subscribe(loggedIn => this.isLoggedIn=loggedIn)
    this.subcription2 = this._authService.admin.subscribe(admin => this.isAdmin=admin)
    
  }

  logout() {
    this._authService.setLogin(false);
    this._authService.setAdmin(false);
    localStorage.clear();
    this.router.navigateByUrl('/');
    }

  
}
