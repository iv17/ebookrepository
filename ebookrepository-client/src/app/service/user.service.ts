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
    return this.http.post<User>(`${this.baseUrl}/register`, user, options);
  }

  login(user): Observable<LoginResponse> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, user, options);
  }

  logout(): Observable<any> {
    console.log('logout');
    return this.http.get(`${this.baseUrl}/logout`);
  }

  getToken(): string {
    return localStorage.getItem('token');
  }

  removeToken() {
    localStorage.removeItem('token');
  }
  
}
