package com.financenewsreview.model;

import java.time.LocalDateTime;

public class NewsArticle {
    private Long id;
    private String title;
    private String source;
    private String summary;
    private String category;
    private String sentiment;
    private LocalDateTime publishedAt;
    private String link;

    public NewsArticle() {
    }

    public NewsArticle(Long id, String title, String source, String summary, String category,
                      String sentiment, LocalDateTime publishedAt, String link) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.summary = summary;
        this.category = category;
        this.sentiment = sentiment;
        this.publishedAt = publishedAt;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
