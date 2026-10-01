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
  selectedCategory = 'All';
  categories = ['All', 'Markets', 'Economy', 'Policy', 'Crypto', 'Global Finance'];
  isLoading = true;

  constructor(private newsService: NewsService) {}

  ngOnInit(): void {
    this.newsService.getNews().subscribe({
      next: (response) => {
        this.articles = response;
        this.featuredArticle = response[0] ?? null;
        this.applyCategoryFilter();
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.articles = [];
        this.featuredArticle = null;
      }
    });
  }

  applyCategoryFilter(): void {
    if (!this.articles.length) {
      this.featuredArticle = null;
      return;
    }

    const filtered = this.selectedCategory === 'All'
      ? this.articles
      : this.articles.filter((article) => article.category === this.selectedCategory);

    this.featuredArticle = filtered[0] ?? this.articles[0];
  }

  setCategory(category: string): void {
    this.selectedCategory = category;
    this.applyCategoryFilter();
  }

  getVisibleArticles(): NewsArticle[] {
    if (this.selectedCategory === 'All') {
      return this.articles.slice(1);
    }

    return this.articles.filter((article) => article.category === this.selectedCategory && article.id !== this.featuredArticle?.id);
  }
}
