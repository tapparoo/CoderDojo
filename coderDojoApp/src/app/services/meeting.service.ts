import { Meeting } from '../models/meeting';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MeetingService {

  private url = environment.baseUrl + 'api/meetings';
  private urlSchedule = environment.baseUrl  + 'api/schedule';

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private router: Router) { }

  // METHODS

  // for all users
  showSchedule() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'X-Requested-With': 'XMLHttpRequest'
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

  // for admin
  getMeetingsByLocation(locationid: number) {
    const credentials = this.auth.getCredentials();

    const httpOptions = {
      headers: new HttpHeaders({
        // Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
console.log(httpOptions);
    return this.http.get<any>(this.url + '/locations/' + locationid, httpOptions)
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

  update(meeting: Meeting) {
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


  updateMeetingAttendeeStatus(meetId, meetingAttendent, status) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    meetingAttendent.attended = status;
    return this.http.put<Meeting>(`${this.url}/${meetId}/attendee/${meetingAttendent.id}`, meetingAttendent, httpOptions).pipe(
      catchError((err: any) => {
        console.error('MeetingService.update(): Error');
        console.error(err);
        return throwError('Error in MeetingService.update()');
      })
    );
  }

  registerUserForMeeting(meetingId, user) {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    console.log(httpOptions);

    return this.http.put<any>(this.url + `/${meetingId}/register/${user}`, user, httpOptions)
         .pipe(
               catchError((err: any) => {
                 if (err.status === 401) {
                  console.log('Not authorized');

                 }
                 return throwError('Error in MeetingService.register()');
               })
          );
  }

}
