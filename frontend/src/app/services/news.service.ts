import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NewsArticle } from '../models/news-article.model';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private readonly apiUrl = 'http://localhost:8080/api/news';

  constructor(private http: HttpClient) {}

  getNews(): Observable<NewsArticle[]> {
    return this.http.get<NewsArticle[]>(this.apiUrl);
  }
}
