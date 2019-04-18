import { Achievement } from './../models/achievement';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AchievementService {
  private url = environment.baseUrl + 'api/achievements';



  public index() {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<Achievement[]>(this.url, httpOptions).pipe(
      catchError((err: any) => {
        console.error('Achievement.index(): Error');
        console.error(err);
        return throwError('Error in AchievementService.index()');
      })
    );
  }

  public create(achievement: Achievement) {
    const credentials = this.auth.getCredentials();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return this.http.post<Achievement>(this.url, achievement, httpOptions).pipe(
      catchError((err: any) => {
        console.error('TodoService.create(): Error');
        console.error(err);
        return throwError('Error in AchievementService.create()');
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
        console.error('AchievementService.destroy(): Error');
        console.error(err);
        return throwError('Error in AchievementService.destroy()');
      })
    );
  }

  public update(achievement: Achievement) {
    const httpOptions = { headers: { 'Content-type': 'application/json' } };
    return this.http.put<Achievement>(`${this.url}/${achievement.id}`, achievement, httpOptions).pipe(
      catchError((err: any) => {
        console.error('TodoService.update(): Error');
        console.error(err);
        return throwError('Error in TodoService.update()');
      })
    );
  }


  constructor(private http: HttpClient,
    private auth: AuthService) { }
}

