import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ebook } from '../model/ebook';
import { IndexUnit } from '../model/indexUnit';

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
  create(book): Observable<Ebook> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<Ebook>(`${this.baseUrl}`, book, options);
  }
  update(id: number, book): Observable<Ebook> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.put<Ebook>(`${this.baseUrl}/${id}`, book, options);
  }

  upload(book: any): Observable<IndexUnit> {
    const options = {
      headers: new HttpHeaders()
    };
    let formData: FormData = new FormData();
    formData.append('file', book);

    return this.http.post<IndexUnit>(`http://localhost:8080/index/add`, formData, options);
  }
  index(): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/reindex`);
  }

}
