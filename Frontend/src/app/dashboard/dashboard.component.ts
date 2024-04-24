import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TasksInt } from '../tasksInt';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { Observable, Subscription } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  token = '';
  showLogin = false;
  tasks: TasksInt = {
    tasks: [],
  };
  taskForm = this.formBuilder.group({
    task: ['', Validators.required],
  });
  selectedTasksForm = this.formBuilder.group({
    selectedTasks: '',
  });
  sub: Subscription;

  ngOnInit(): void {
    console.log('INIT CALLED');
    this.handleDashboardSubmit();
  }

  constructor(
    private _authService: AuthService,
    private formBuilder: FormBuilder,
    private http: HttpClient,
  ) {}

  handleDashboardSubmit() {
    this._authService.refreshToken().subscribe({
      next: (token) => {
        if(token){
          // Token is refreshed, or not needed to be refreshed
        this.tasks.tasks = [''];
        let httpRequest: Observable<any> = this.http.get(
          this._authService.getTaskUrl(),
          { headers: this._authService.getAuthHeader() }
        );
  
        httpRequest.subscribe({
          next: (response: TasksInt) => {
            this.tasks = response;
            //this.sub.unsubscribe;
          },
          error: (error) => {
            console.log(error.value);
          },
        });
        }
      },
      error: (error) => {
        console.error('Failed to refresh token', error);
      }
    });
  }

  postNewTask() {
    console.log('POST CALLED');
    this.sub = this._authService.refreshToken().subscribe({
      next: (token) => {
        if(token){
          // Token is refreshed, or not needed to be refreshed
          console.log(this.taskForm.value);
          console.log("We in next");
          let httpRequest: Observable<any> = this.http.post(
            this._authService.getTaskUrl(),
            this.taskForm.value,
            { headers: this._authService.getAuthHeader() }
          );
          
          httpRequest.subscribe({
            next: (response: TasksInt) => {
              this.tasks = response;
              console.log('We got a response');
              this.taskForm.patchValue({ task: '' }); // Reset form value here on success
            },
            error: (error) => {
              console.log(error.value);
            },
          });
        }
        
      },
      error: (error) => {
        console.error('Failed to refresh token', error);
      }
    });
  }

  removeSelectedTasks() {
    for (var val of this.selectedTasksForm.get('selectedTasks').value) {
      this.taskForm.get('task').setValue(val);
      this.postNewTask();
    }
    console.log(this.selectedTasksForm.get('selectedTasks').value);
  }
}

