import { environment } from 'src/environments/environment';
import { UserAchievement } from './../models/user-achievement';
import { UserGoal } from './../models/user-goal';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserGoalService {
  private url = environment.baseUrl + 'api/usergoals';


  public index() {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<UserGoal[]>(this.url, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.index(): Error');
        console.error(err);
        return throwError('Error in GoalService.index()');
      })
    );
  }

  public create(userGoal: UserGoal) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.post<UserGoal>(this.url, userGoal, httpOptions).pipe(
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

  public update(userGoal: UserGoal) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.put<UserGoal>(`${this.url}/${userGoal.id}`, userGoal, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.update(): Error');
        console.error(err);
        return throwError('Error in GoalService.update()');
      })
    );
  }

  public getUserGoalsByUserachievement(userAchievement: UserAchievement){
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<UserGoal[]>(this.url+"/"+userAchievement.id, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.index(): Error');
        console.error(err);
        return throwError('Error in GoalService.index()');
      })
    );
  }



  constructor(private http: HttpClient,
    private auth: AuthService) { }
}
