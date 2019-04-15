import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { User } from '../models/user';
import { UserDetail } from '../models/user-detail';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url = environment.baseUrl + 'api/users';

  constructor(private http: HttpClient, private auth: AuthService) { }

  index() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.get<User[]>(this.url, httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getAll Users error');
               })
          );
  }

  getUser(username) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.get<any>(this.url + `/${username}`, httpOptions)
         .pipe(
               catchError((err: any) => {
                 if (err.status === 401) {
                  console.log('Not authorized to see this user\'s profile');

                 }
                 return 'getUser error';
               })
          );
  }

  getChildren(username){
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.get<any>(this.url + `/${username}/children`, httpOptions)
         .pipe(
               catchError((err: any) => {
                 if (err.status === 401) {
                  console.log('Not authorized to see this user\'s children');

                 }
                 return 'getChildren error';
               })
          );
  }

  getParents(username) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.get<any>(this.url + `/${username}/parents`, httpOptions)
         .pipe(
               catchError((err: any) => {
                 if (err.status === 401) {
                  console.log('Not authorized to see this user\'s children');

                 }
                 return 'getChildren error';
               })
          );
  }

  addChild(username, child) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.post<any>(this.url + `/${username}/children`, child, httpOptions)
         .pipe(
               catchError((err: any) => {
                 if (err.status === 401) {
                  console.log('Problem adding a child to this parent');

                 }
                 return 'getChildren error';
               })
          );
  }

  getUserAchievements(username){
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.get<any>(this.url + `/${username}/achievements`, httpOptions)
         .pipe(
               catchError((err: any) => {
                 if (err.status === 401) {
                  console.log('Not authorized to see this user\'s profile');

                 }
                 return 'getUserAchievements error';
               })
          );
  }

  getUserMeetings(username) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.get<any>(this.url + `/${username}/meetings`, httpOptions)
         .pipe(
               catchError((err: any) => {
                 if (err.status === 401) {
                  console.log('Not authorized to see this user\'s profile');

                 }
                 return 'getUserAchievements error';
               })
          );
  }

  updateUser(user: User) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.put<User>(this.url + `/${user.id}`, user,  httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('updateUser error');
               })
          );
  }

  updateUserDetail(user: UserDetail) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.put<UserDetail>(this.url, user,  httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('updateUserDetail error');
               })
          );
  }



}
