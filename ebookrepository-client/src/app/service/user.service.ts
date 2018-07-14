import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { LoginResponse } from '../model/loginResponse.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

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