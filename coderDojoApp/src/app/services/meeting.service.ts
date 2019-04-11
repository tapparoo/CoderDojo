import { Meeting } from './../models/meeting';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})
export class MeetingService {

  private baseUrl = environment.baseUrl;

  private url = this.baseUrl + 'api/meetings';

  constructor(
    private http: HttpClient,
    private auth: AuthService) { }

  // METHODS
  index() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
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
}
