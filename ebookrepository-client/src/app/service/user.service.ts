import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { LoginResponse } from '../model/loginResponse.model';

import { Http, Response } from '@angular/http';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient,
    private http2 : Http) { }

  private parseData(res: Response) {
    return res.json() || [];
  }

  private handleError(error: Response | any) {
    let errorMessage: string;
    errorMessage = error.message ? error.message : error.toString();
    return Observable.throw(errorMessage);
  }

  /*getAll(): Observable<any> {
    return this.http2.get(this.baseUrl)
    .pipe(map(this.parseData));
  }*/
  register(user): Observable<User> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<User>(`${this.baseUrl}`, user, options);
  }
  login(user): Observable<LoginResponse> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, user, options);
  }
  logout(): Observable<any> {
    return this.http.get(`${this.baseUrl}/logout`);
  }
  changePassword(user): Observable<User> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<User>(`${this.baseUrl}/change-password`, user, options);
  }
  getMe(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/me`);
  }
  update(id: number, user): Observable<User> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.put<User>(`${this.baseUrl}/${id}`, user, options);
  }
  getAll(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}`);
  }
  
  getToken(): string {
    return localStorage.getItem('token');
  }

  removeToken() {
    localStorage.removeItem('token');
  }
  
}