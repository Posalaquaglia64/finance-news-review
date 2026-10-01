package com.financenewsreview.service;

import com.financenewsreview.model.NewsArticle;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    private static final String GOOGLE_NEWS_FINANCE_RSS =
            "https://news.google.com/rss/search?q=finance+market+economy+stocks&hl=en-US&gl=US&ceid=US:en";

    public List<NewsArticle> getLatestNews() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String xml = restTemplate.getForObject(GOOGLE_NEWS_FINANCE_RSS, String.class);
            if (xml == null || xml.isBlank()) {
                return fallbackNews();
            }
            return parseGoogleNews(xml);
        } catch (Exception ex) {
            return fallbackNews();
        }
    }

    private List<NewsArticle> parseGoogleNews(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));

        NodeList itemNodes = document.getElementsByTagName("item");
        List<NewsArticle> articles = new ArrayList<>();

        for (int i = 0; i < itemNodes.getLength() && i < 8; i++) {
            Element item = (Element) itemNodes.item(i);

            String title = getTextContent(item, "title");
            String link = getTextContent(item, "link");
            String summary = getTextContent(item, "description");
            String source = getSourceName(item);
            String pubDate = getTextContent(item, "pubDate");
            String category = detectCategory(title);
            String sentiment = detectSentiment(title);

            articles.add(new NewsArticle(
                    (long) (i + 1),
                    stripHtml(title),
                    source,
                    stripHtml(summary == null || summary.isBlank() ? title : summary),
                    category,
                    sentiment,
                    parseDate(pubDate),
                    link
            ));
        }

        return articles;
    }

    private String getTextContent(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList == null || nodeList.getLength() == 0) {
            return null;
        }
        Node node = nodeList.item(0);
        return node != null ? node.getTextContent() : null;
    }

    private String getSourceName(Element item) {
        NodeList sourceNodes = item.getElementsByTagName("source");
        if (sourceNodes != null && sourceNodes.getLength() > 0) {
            Node sourceNode = sourceNodes.item(0);
            if (sourceNode != null && sourceNode.getTextContent() != null) {
                return sourceNode.getTextContent().trim();
            }
        }
        return "Google News";
    }

    private LocalDateTime parseDate(String rawDate) {
        if (rawDate == null || rawDate.isBlank()) {
            return LocalDateTime.now();
        }

        try {
            return ZonedDateTime.parse(rawDate, DateTimeFormatter.RFC_1123_DATE_TIME)
                    .toLocalDateTime();
        } catch (Exception ignored) {
            try {
                return ZonedDateTime.parse(rawDate, DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH))
                        .toLocalDateTime();
            } catch (Exception ignored2) {
                return LocalDateTime.now();
            }
        }
    }

    private String stripHtml(String text) {
        if (text == null) {
            return "";
        }

        return text.replaceAll("<.*?>", "").replace("&amp;", "&").trim();
    }

    private String detectCategory(String title) {
        String normalized = title.toLowerCase(Locale.ROOT);

        if (normalized.contains("bitcoin") || normalized.contains("crypto") || normalized.contains("ethereum") || normalized.contains("digital asset")) {
            return "Crypto";
        }
        if (normalized.contains("inflation") || normalized.contains("treasury") || normalized.contains("fed") || normalized.contains("rate")
                || normalized.contains("central bank") || normalized.contains("economic") || normalized.contains("gdp")) {
            return "Economy";
        }
        if (normalized.contains("bank") || normalized.contains("regulator") || normalized.contains("policy") || normalized.contains("finance ministry")
                || normalized.contains("federal reserve")) {
            return "Policy";
        }
        if (normalized.contains("currency") || normalized.contains("trade") || normalized.contains("global") || normalized.contains("international")) {
            return "Global Finance";
        }
        return "Markets";
    }

    private String detectSentiment(String title) {
        String normalized = title.toLowerCase(Locale.ROOT);

        if (normalized.contains("surge") || normalized.contains("rally") || normalized.contains("gain") || normalized.contains("climb")
                || normalized.contains("upbeat") || normalized.contains("strong") || normalized.contains("boost")) {
            return "Positive";
        }
        if (normalized.contains("drop") || normalized.contains("fall") || normalized.contains("plunge") || normalized.contains("slump")
                || normalized.contains("decline") || normalized.contains("selloff") || normalized.contains("crash")) {
            return "Negative";
        }
        return "Neutral";
    }

    private List<NewsArticle> fallbackNews() {
        return List.of(
                new NewsArticle(1L,
                        "Global markets rally as central banks signal rate stability",
                        "Bloomberg",
                        "Investors welcomed a more measured tone from major central banks, lifting equities and easing pressure on bond markets.",
                        "Markets",
                        "Positive",
                        LocalDateTime.now().minusHours(2),
                        "https://example.com/markets-rally"
                ),
                new NewsArticle(2L,
                        "Treasury yields soften as inflation data cools expectations",
                        "Reuters",
                        "A slower-than-expected inflation reading reduced the likelihood of a near-term tightening surprise, which helped bond prices stabilize.",
                        "Economy",
                        "Neutral",
                        LocalDateTime.now().minusHours(4),
                        "https://example.com/treasury-yields"
                ),
                new NewsArticle(3L,
                        "Banking sector braces for tighter lending rules and scrutiny",
                        "Financial Times",
                        "Regulators are pushing for more robust stress-testing frameworks as financial institutions adapt to a changing credit environment.",
                        "Policy",
                        "Cautious",
                        LocalDateTime.now().minusHours(6),
                        "https://example.com/banking-sector"
                )
        );
    }
}
