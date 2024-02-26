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

  getLoginUrl(): string {
    localStorage.removeItem('JWTToken');
    return BASE_URL + 'auth/login';
  }

  getRegisterUrl(): string {
    return BASE_URL + 'auth/register';
  }

  getTaskUrl(): string {
    return BASE_URL + 'tasks';
  }

  getAuthHeader(): HttpHeaders {
    return this.createAuthHeader();
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
