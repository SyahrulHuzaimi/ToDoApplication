import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MaterialModule } from '../material/material.module';

@Component({
  selector: 'app-toolbox',
  standalone: true,
  imports: [RouterLink, MaterialModule],
  templateUrl: './toolbox.component.html',
  styleUrl: './toolbox.component.css'
})
export class ToolboxComponent {

  constructor(
    private router: Router,
  ) {}

  logout() {
    localStorage.removeItem('JWTToken');
    this.router.navigateByUrl('/login');
    }
}
