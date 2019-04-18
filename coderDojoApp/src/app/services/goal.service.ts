import { GoalDTO } from './../models/goal-dto';

import { Goal } from '../models/goal';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GoalService {
  // private baseUrl = 'http://localhost:8090/';
  private url = environment.baseUrl  + 'api/goals';


  public index() {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<Goal[]>(this.url, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.index(): Error');
        console.error(err);
        return throwError('Error in GoalService.index()');
      })
    );
  }

  public create(goal: Goal) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
      let goalDTO: GoalDTO = new GoalDTO(goal.id, goal.name, goal.description, goal.achievement.id);
      console.log(goalDTO.achievementId)

    return this.http.post<GoalDTO>(this.url, goalDTO, httpOptions).pipe(
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

  public update(goal: Goal) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.put<Goal>(`${this.url}/${goal.id}`, goal, httpOptions).pipe(
      catchError((err: any) => {
        console.error('GoalService.update(): Error');
        console.error(err);
        return throwError('Error in GoalService.update()');
      })
    );
  }






  constructor(private http: HttpClient,
    private auth: AuthService) { }
}
