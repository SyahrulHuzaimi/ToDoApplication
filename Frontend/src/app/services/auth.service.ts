import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { BehaviorSubject, Observable } from 'rxjs';

const BASE_URL = ['http://localhost:8080/'];

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  loggedInValue: boolean = false;
  loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  adminValue: boolean = false;
  admin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  buffer: number = 10000; //10 seconds

  setLogin(bool: boolean) {
    this.loggedInValue = bool;
    this.loggedIn.next(this.loggedInValue);
  }

  setAdmin(bool: boolean) {
    this.adminValue = bool;
    this.admin.next(this.adminValue);
  }

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

  private createRefreshHeader() {
    let headers: any = {};
    if (this.getRefreshToken() !== null) {
      headers = new HttpHeaders().set(
        'Authorization',
        'Bearer ' + this.getRefreshToken()
      );
    }
    return headers;
  }

  getAuthToken(): string | null {
    return localStorage.getItem('JWTToken');
  }

  getRefreshToken(): string | null {
    return localStorage.getItem('RefreshToken');
  }

  isTokenExpired(): boolean {
    let expiration = Number(localStorage.getItem('Expiration'));
    let today: number = Date.now();
    return expiration < today + this.buffer;
  }

  refreshToken(): void {
    if (!this.isTokenExpired()) {
      return;
    }
    let httpRequest: Observable<any> = this.http.post(
      BASE_URL + 'auth/refresh', {} ,
      { headers: this.createRefreshHeader() }
    );
    httpRequest.subscribe({
      next: (response) => {
        if (response.accessToken) {
          console.log('OldToken: ' + this.getAuthToken());
          console.log('OldRefresh: ' + this.getRefreshToken());
          localStorage.setItem('JWTToken', response.accessToken);
          localStorage.setItem('RefreshToken', response.refreshToken);
          localStorage.setItem('Expiration', response.expireDate);
          console.log('NewToken: ' + this.getAuthToken());
          console.log('NewRefresh: ' + this.getRefreshToken());

          this.setAdmin(response.admin);
          this.setLogin(true);
        }
      },
      error: (error) => {
        console.log('Bad Request: ' + error.error.responseMessage);
      },
    });
  }
}
