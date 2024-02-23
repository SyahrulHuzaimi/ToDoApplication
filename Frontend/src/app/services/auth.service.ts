import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';

const BASE_URL = ['http://localhost:8080/'];

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(loginRequest: any): Observable<any> {
    localStorage.removeItem('JWTToken');
    return this.http.post(BASE_URL + 'auth/login', loginRequest.value);
  }

  register(registerRequest: any): Observable<any> {
    return this.http.post(BASE_URL + 'auth/register', registerRequest.value);
  }

  getTasks(): Observable<any> {
    return this.http.get(BASE_URL + 'tasks', {
      headers: this.createAuthHeader(),
    });
  }

  postTask(taskForm: FormGroup<{ task: FormControl<string> }>) {
    return this.http.post(BASE_URL + 'tasks', taskForm.value, {
      headers: this.createAuthHeader(),
    });
  }

  hello(): Observable<any> {
    return this.http.get(BASE_URL + 'hello');
  }

  private createAuthHeader() {
    let headers: any = {};
    if (this.getAuthToken() !== null) {
      headers = new HttpHeaders().set(
        'Authorization',
        'Bearer ' + this.getAuthToken()
      );
    }
    return headers;
  }
  getAuthToken(): string | null {
    return localStorage.getItem('JWTToken');
  }
}
