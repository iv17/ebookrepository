import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ebook } from '../model/ebook';

@Injectable({
  providedIn: 'root'
})
export class EbookrepositoryService {

  private baseUrl = 'http://localhost:8080/api/books';
  private indexerUrl = 'http://localhost:8080/api/indexer';
  private searchUrl = 'http://localhost:8080/api/search';

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
    this.http.post(`${this.indexerUrl}`, book, options);
    return this.http.post<Ebook>(`${this.baseUrl}`, book, options);
  }
  createIndex(book): Observable<Ebook> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<Ebook>(`${this.indexerUrl}`, book, options);
  }

  update(id: number, book): Observable<Ebook> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.put<Ebook>(`${this.baseUrl}/${id}`, book, options);
  }

  upload(book: any): Observable<Ebook> {
    const options = {
      headers: new HttpHeaders()
    };
    let formData: FormData = new FormData();
    formData.append('file', book);

    return this.http.post<Ebook>(`${this.indexerUrl}/upload`, formData, options);
  }
  download(id: number) {
    return this.http.get(`${this.baseUrl}/download/${id}`, { responseType: 'blob' });
  }
  index(): Observable<any> {
    return this.http.get<any>(`${this.indexerUrl}/reindex`);
  }

  searchByContent(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/queryParser`, simpleQuery, options);
  }
  searchTerm(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/term`, simpleQuery, options);
  }
  searchFuzzy(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/fuzzy`, simpleQuery, options);
  }
  searchPhrase(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/phrase`, simpleQuery, options);
  }

  searchBoolean(advancedQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/boolean`, advancedQuery, options);
  }

}
