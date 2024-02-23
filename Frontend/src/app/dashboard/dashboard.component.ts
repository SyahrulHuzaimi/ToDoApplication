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
    task: ['', Validators.required]
  });
  selectedTasksForm = this.formBuilder.group({
    selectedTasks: ''
  })

  ngOnInit(): void {
    this.handleDashboardSubmit();
  }

  constructor(private _authService: AuthService, private formBuilder: FormBuilder) {}

  handleDashboardSubmit() {
    this.tasks.tasks = [''];
    this._authService.getTasks().subscribe({
      next: (response: TasksInt) => {
        this.tasks = response;
      },
      error: (error) => {
        console.log(error.value);
      },
    });
  }

  postNewTask() {
    console.log(this.taskForm.value);
    this._authService.postTask(this.taskForm).subscribe({
      next: (response: TasksInt) => {
        this.tasks = response;
      },
      error: (error) => {
        console.log(error.value);
      },
    });
    this.taskForm.patchValue({task:''})
  }

  removeSelectedTasks() {
    for (var val of this.selectedTasksForm.get('selectedTasks').value){
      this.taskForm.get("task").setValue(val);
      this.postNewTask()
    }
    console.log(this.selectedTasksForm.get('selectedTasks').value);
    }
}
