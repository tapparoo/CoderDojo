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

  constructor(private http: HttpClient) {}

  private baseUrl = environment.baseUrl;

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
    return this.http.post(this.baseUrl + 'register', user)
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

  getCredentials() {
    return localStorage.getItem('credentials');
  }
}
