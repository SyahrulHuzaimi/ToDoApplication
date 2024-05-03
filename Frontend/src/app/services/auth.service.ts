import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import {
  BehaviorSubject,
  Observable,
  catchError,
  filter,
  tap,
  throwError,
} from 'rxjs';

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
  //private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  private isRefreshing: boolean = false;


  setLogin(bool: boolean) {
    this.loggedInValue = bool;
    this.loggedIn.next(this.loggedInValue);
  }

  setAdmin(bool: boolean) {
    this.adminValue = bool;
    this.admin.next(this.adminValue);
  }

  getLoginUrl(): string {
    localStorage.clear();
    return BASE_URL + 'auth/login';
  }

  getRegisterUrl(): string {
    return BASE_URL + 'auth/register';
  }

  getTaskUrl(): string {
    return BASE_URL + 'tasks';
  }

  getChangeUrl(): string {
    return BASE_URL + 'auth/changepassword';
  }

  getAuthHeader(): HttpHeaders {
    return this.createAuthHeader();
  }

  getAuthHeaderJSON(): HttpHeaders {
    let header = this.createAuthHeader();
    header = header.set('Content-Type', 'application/json');
    return header;
  }

  hello(): Observable<any> {
    return this.http.get(BASE_URL + 'hello');
  }

  getLoggedInValue() {
    this.loggedInValue = localStorage.getItem('RefreshToken') !== null;
    this.loggedIn.next(this.loggedInValue);
    return this.loggedInValue;
  }

  getAdminValue() {
    this.admin.next(this.adminValue);
    return this.adminValue;
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

  refreshToken(): Observable<any> {
    let refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
    if (!this.isTokenExpired()) {
      refreshTokenSubject.next("Valid Refresh");
      return refreshTokenSubject.asObservable();
    }

    if (!this.isRefreshing) {
      this.isRefreshing = true;
      
      let httpRequest: Observable<any> = this.http.post(
        BASE_URL + 'auth/refresh', {} ,
        { headers: this.createRefreshHeader() }
      );
      httpRequest.subscribe({
        next: (response) => {
          if (response.accessToken) {
            localStorage.setItem('JWTToken', response.accessToken);
            localStorage.setItem('RefreshToken', response.refreshToken);
            localStorage.setItem('Expiration', response.expireDate);
  
            this.setAdmin(response.admin);
            this.setLogin(true);
            refreshTokenSubject.next(response.accessToken);
          }else{
            refreshTokenSubject.next(null);
          }
          this.isRefreshing = false;
        },
        error: (error) => {
          console.log('Bad Request: ' + error.error.responseMessage);
          refreshTokenSubject.error(error);
          this.isRefreshing = false;
        },
      });
    }
    return refreshTokenSubject.asObservable();
  }
}
