import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ebook } from '../model/ebook';

@Injectable({
  providedIn: 'root'
})
export class EbookrepositoryService {

  private baseUrl = 'http://localhost:8080/api/books';

  constructor(private http: HttpClient) { }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }
  getAllByCategory(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}/category`);
  }
  getAll(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}`);
  }
  addNewBook(book): Observable<Ebook> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<Ebook>(`${this.baseUrl}`, book, options);
  }
  updateBook(id: number, book): Observable<Ebook> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.put<Ebook>(`${this.baseUrl}/${id}`, book, options);
  }
}
