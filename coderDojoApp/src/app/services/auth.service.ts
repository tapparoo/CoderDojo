import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User } from '../models/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private username;

  constructor(private http: HttpClient) {}

  private baseUrl = environment.baseUrl;
  // private authorities;
  login(username, password) {
      // Make credentials
      const credentials = this.generateBasicAuthCredentials(username, password);

      // Send credentials as Authorization header (this is spring security convention for basic auth)
      const httpOptions = {
        headers: new HttpHeaders({
          Authorization: `Basic ${credentials}`,
          'X-Requested-With': 'XMLHttpRequest'
        })
      };
     // create request to authenticate credentials
      return this.http
       .get<any>(this.baseUrl + 'authenticate', httpOptions)
       .pipe(
         tap((res) => {
           localStorage.setItem('credentials' , credentials);
           return res;
         }),
         catchError((err: any) => {
           console.log(err);
           return throwError('AuthService.login(): Error logging in.');
         })
       );
  }

  register(user) {
    const credentials = this.generateBasicAuthCredentials(user.username, user.password);

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    // create request to register a new account
    return this.http.post<any>(this.baseUrl + 'register', user)
      .pipe(
        tap((res) => {
          localStorage.setItem('credentials' , credentials);
          return res;
        }),
          catchError((err: any) => {
            console.log(err);
            return throwError('AuthService.register(): error registering user.');
          })
        );
  }
  // Same as register(), but don't re-set credentials as child
  registerChild(user) {
    const credentials = this.generateBasicAuthCredentials(user.username, user.password);

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    // create request to register a new account
    return this.http.post<User>(this.baseUrl + 'register', user)
      .pipe(
        tap((res) => {
          return res;
        }),
          catchError((err: any) => {
            confirm(`Username/nickname ${user.username} `)
            console.log(err);
            return throwError('AuthService.registerChild(): error registering user.');
          })
        );
  }

  logout() {
    localStorage.removeItem('credentials');
  }

  checkLogin() {
    if (localStorage.getItem('credentials')) {
      return true;
    }
    return false;
  }

  generateBasicAuthCredentials(username, password) {
    return btoa(`${username}:${password}`);
  }

  setCredentials(username, password) {
    localStorage.setItem('credentials' , this.generateBasicAuthCredentials(username, password));
  }

  getCredentials() {
    return localStorage.getItem('credentials');
  }

  setUsername() {
    this.username = atob(this.getCredentials()).split(':')[0];
  }

  getLoggedInUsername() {
    return this.username;
  }
}
