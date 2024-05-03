import { Component } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-dialog-logout',
  standalone: true,
  imports: [MatDialogModule, RouterModule, MatButtonModule],
  templateUrl: './dialog-logout.component.html',
  styleUrl: './dialog-logout.component.css'
})
export class DialogLogoutComponent {

  constructor(
    private _authService: AuthService,
    private router: Router,
  ) {}
  
  logout(){
    this._authService.setLogin(false);
    this._authService.setAdmin(false);
    localStorage.clear();
    this.router.navigateByUrl('/');
  }
}
