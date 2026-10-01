import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewsService } from './services/news.service';
import { NewsArticle } from './models/news-article.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  articles: NewsArticle[] = [];
  featuredArticle: NewsArticle | null = null;
  isLoading = true;

  constructor(private newsService: NewsService) {}

  ngOnInit(): void {
    this.newsService.getNews().subscribe({
      next: (response) => {
        this.articles = response;
        this.featuredArticle = response[0] ?? null;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.articles = [];
      }
    });
  }
}
