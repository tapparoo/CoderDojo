import { Location } from './../models/location';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private baseUrl = environment.baseUrl;

  private url = this.baseUrl + 'api/locations';

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private router: Router
  ) {}

  // METHODS

  showAllLocations() {
    const credentials = this.auth.getCredentials();

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };

    return this.http.get<Location[]>(this.url, httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('getAll error');
      })
    );
  }
}
