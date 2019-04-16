import { UserDetail } from 'src/app/models/user-detail';
import { UserAchievement } from './../models/user-achievement';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserAchievementService {


  private baseUrl = 'http://localhost:8090/';
  private url = this.baseUrl + 'api/userachievements';
 

  public index() {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<UserAchievement[]>(this.url, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.index(): Error');
        console.error(err);
        return throwError('Error in GoalService.index()');
      })
    );
  }

  public create(userAchievement: UserAchievement) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };    
    
    return this.http.post<UserAchievement>(this.url, userAchievement, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.create(): Error');
        console.error(err);
        return throwError('Error in GoalService.create()');
      })
    );
  }

  public destroy(id: number) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.delete(`${this.url}/${id}`, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.destroy(): Error');
        console.error(err);
        return throwError('Error in GoalService.destroy()');
      })
    );
  }

  public update(userAchievement: UserAchievement) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.put<UserAchievement>(`${this.url}/${userAchievement.id}`, userAchievement, httpOptions).pipe(
      catchError((err: any) => {
        console.error('UserAchievement.update(): Error');
        // console.error(err);
        return throwError('Error in UserAchievement.update()');
      })
    );
  }

  public getUserAchievementsByUserDetail(userDetail: UserDetail){
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<UserAchievement[]>(this.url+"/user/"+userDetail.user.username, httpOptions).pipe(
      catchError((err: any) => {
        console.error('UserAchievementService: Error');
        console.error(err);
        return throwError('UserAchievementService: Error');
      })
    );
  }
  
 

  constructor(private http: HttpClient,
    private auth: AuthService) { }
}
