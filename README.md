# Finance News Review

A full-stack web application for reviewing major finance news.

## Stack
- Backend: Java 17 + Spring Boot 3
- Frontend: Angular 17 + TypeScript
- Data source: seed financial news data for demo purposes

## Features
- Latest major finance headlines
- Category tags (Markets, Policy, Economy, Crypto, Global Finance)
- Sentiment labels and publication metadata
- Newspaper-style layout for a financial review dashboard

## Run backend
```bash
cd backend
./mvnw spring-boot:run
```

## Run frontend
```bash
cd frontend
npm install
npm start
```

Then open http://localhost:4200

## Backend API
```http
GET http://localhost:8080/api/news
```
