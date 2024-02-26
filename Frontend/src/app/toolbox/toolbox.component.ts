import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MaterialModule } from '../material/material.module';
import { AuthService } from '../services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-toolbox',
  standalone: true,
  imports: [RouterLink, MaterialModule],
  templateUrl: './toolbox.component.html',
  styleUrl: './toolbox.component.css'
})
export class ToolboxComponent implements OnInit, OnDestroy{

  constructor(
    private _authService: AuthService,
    private router: Router,
  ) {}

  isLoggedIn: boolean;
  subscription: Subscription;

  ngOnDestroy(): void {
    this.subscription && this.subscription.unsubscribe();
  }

  ngOnInit(): void {  
    this.subscription = this._authService.loggedIn.subscribe(loggedIn => this.isLoggedIn=loggedIn)
  }

  logout() {
    this._authService.setLogin(false);
    localStorage.removeItem('JWTToken');
    this.router.navigateByUrl('/login');
    }

  
}
