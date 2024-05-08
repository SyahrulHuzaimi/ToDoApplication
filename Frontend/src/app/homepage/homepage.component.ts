import { Component } from '@angular/core';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {
  username: string; 
  constructor(){
    this.username = localStorage.getItem('Username') !== null ? localStorage.getItem('Username') : null;
  }
}
