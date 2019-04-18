import { Location } from './../models/location';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { UserDetail } from '../models/user-detail';


@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private baseUrl = environment.baseUrl;

  private url = this.baseUrl + 'api/locations';

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private router: Router
  ) {}

  // METHODS

  showAllLocations() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    

    return this.http.get<Location[]>(this.url, httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('getAll error');
      })
    );
  }

  // admin top-right panel
  getUsersByLocation(loc: number) {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<UserDetail[]>((this.url + '/' + loc + '/users'),  httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getUserDetailByRole error');
               })
          );
  }

  // admin bottom-right panel
  getStudentsByLocation(loc: number) {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<UserDetail[]>((this.url + '/' + loc + '/users/students'),  httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getUserDetailByRole error');
               })
          );
  }
}
