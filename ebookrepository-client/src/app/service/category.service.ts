import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) { }
  
  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }
  getAll(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}`);
  }
  addNewCategory(category): Observable<Category> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<Category>(`${this.baseUrl}`, category, options);
  }
  updateCategory(id: number, category): Observable<Category> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.put<Category>(`${this.baseUrl}/${id}`, category, options);
  }
}
