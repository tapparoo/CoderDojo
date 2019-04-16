import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { UserDetail } from '../models/user-detail';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private url = environment.baseUrl + 'api/roles/';

  constructor(private http: HttpClient, private auth: AuthService) { }

  getRoles() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<any[]>((this.url),  httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getUserDetailByRole error');
               })
          );
  }

  getUsersByRole(role: string){
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${this.auth.getCredentials()}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<UserDetail[]>((this.url+role+'/users'),  httpOptions)
         .pipe(
               catchError((err: any) => {
                 console.log(err);
                 return throwError('getUserDetailByRole error');
               })
          );
  }
}
