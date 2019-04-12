import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url = environment.baseUrl + 'api/users';
  credentials = this.auth.getCredentials();
  httpOptions = {
    headers: new HttpHeaders({
      'Authorization': `Basic ${this.credentials}`,
      'X-Requested-With': 'XMLHttpRequest'
    })
  };

  constructor(private http: HttpClient, private auth: AuthService) { }

  index() {
    return this.http.get<User[]>(this.url, this.httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getAll Users error');
               })
          );
  }

  getUser(id: number) {
    return this.http.get<User>(this.url + `/${id}`, this.httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getAll Users error');
               })
          );
  }
}
