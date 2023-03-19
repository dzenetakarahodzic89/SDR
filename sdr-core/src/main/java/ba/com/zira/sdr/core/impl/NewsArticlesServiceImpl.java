package ba.com.zira.sdr.core.impl;

import java.io.IOException;
import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import ba.com.zira.commons.model.User;
import ba.com.zira.sdr.dao.NewsArticlesDAO;
import ba.com.zira.sdr.dao.RssFeedDAO;
import ba.com.zira.sdr.rssfeed.RssAdapterFactory;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NewsArticlesServiceImpl {
    NewsArticlesDAO newsArticlesDAO;
    RssFeedDAO rssFeedDAO;
    private static final User systemUser = new User("Rss feed Integration");

    @Scheduled(cron = "0 0 * * * *") // 1 hour on the dot
    public void getBlogContent() throws IOException, IllegalArgumentException, FeedException {
        var blogs = rssFeedDAO.findAll();
        for (var blog : blogs) {
            URL url = new URL(blog.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            @SuppressWarnings("deprecation")
            SyndFeed feed = input.build(new XmlReader(url));
            var adapter = RssAdapterFactory.build(blog.getAdapter());
            var data = adapter.read(feed, blog.getUrl(), systemUser.getUserId());
            if (data != null) {
                newsArticlesDAO.persistCollection(data);
            }
        }
    }
}
