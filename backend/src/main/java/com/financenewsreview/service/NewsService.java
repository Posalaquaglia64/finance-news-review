package com.financenewsreview.service;

import com.financenewsreview.model.NewsArticle;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {

    public List<NewsArticle> getLatestNews() {
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
                ),
                new NewsArticle(4L,
                        "Bitcoin steadies after a volatile week as institutional flows signal renewed interest",
                        "CoinDesk",
                        "Digital asset markets showed signs of recovery after volatility eased, with investors watching liquidity and exchange data closely.",
                        "Crypto",
                        "Positive",
                        LocalDateTime.now().minusHours(8),
                        "https://example.com/bitcoin-steadies"
                ),
                new NewsArticle(5L,
                        "Trade corridors remain under pressure as supply chain costs stay elevated",
                        "WSJ",
                        "Shipping and manufacturing groups continue to monitor cross-border trade friction, with cost pressures still influencing pricing decisions.",
                        "Global Finance",
                        "Neutral",
                        LocalDateTime.now().minusHours(10),
                        "https://example.com/trade-corridors"
                )
        );
    }
}
