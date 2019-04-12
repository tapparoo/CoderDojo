import { Meeting } from './../models/meeting';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MeetingService {

  private baseUrl = environment.baseUrl;

  private url = this.baseUrl + 'api/meetings';
  private urlSchedule = this.baseUrl + 'api/schedule';
  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private router: Router) { }

  // METHODS

  // for all users
  showSchedule() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.get<Meeting[]>(this.urlSchedule, httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getAll error');
               })
          );
  }

  index() {
      const credentials = this.auth.getCredentials();

      const httpOptions = {
        headers: new HttpHeaders({
          Authorization: `Basic ${credentials}`,
          'X-Requested-With': 'XMLHttpRequest'
        })
      };

      return this.http.get<Meeting[]>(this.url, httpOptions)
           .pipe(
                 catchError((err: any) => {
                   console.log(err);
                   return throwError('getAll error');
                 })
            );
  }

  create(meeting: Meeting) {
    console.log(meeting);
    const credentials = this.auth.getCredentials();

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.post<any>(this.url, meeting, httpOptions);
  }


  destroy(id: number) {
    const credentials = this.auth.getCredentials();

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    console.log(id);
    return this.http.delete<any>(this.url + '/' + id, httpOptions);
  }

  public update(meeting: Meeting) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.put<Meeting>(`${this.url}/${meeting.id}`, meeting, httpOptions).pipe(
      catchError((err: any) => {
        console.error('MeetingService.update(): Error');
        console.error(err);
        return throwError('Error in MeetingService.update()');
      })
    );
  }
}
