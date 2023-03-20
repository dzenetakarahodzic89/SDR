package ba.com.zira.sdr.core.impl;

import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.NewsArticlesService;
import ba.com.zira.sdr.api.model.newsarticles.NewsArticleResponse;
import ba.com.zira.sdr.dao.NewsArticlesDAO;
import ba.com.zira.sdr.dao.RssFeedDAO;
import ba.com.zira.sdr.rssfeed.RssAdapterFactory;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NewsArticlesServiceImpl implements NewsArticlesService {
    NewsArticlesDAO newsArticlesDAO;
    RssFeedDAO rssFeedDAO;

    private static final User systemUser = new User("Rss feed Integration");

    @Scheduled(fixedDelayString = "${rss.taskDelay}", initialDelay = 10000L)
    public void getBlogContent() throws ApiException {
        try {
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
        } catch (Exception ex) {
            throw ApiException.createFrom(ResponseCode.BATCH_INSERT_ERROR, ex.getMessage());
        }
    }

    @Override
    public ListPayloadResponse<NewsArticleResponse> getFreshContent(EmptyRequest req) throws ApiException {
        var newsArticles = newsArticlesDAO.getFreshContent();
        return new ListPayloadResponse<>(req, ResponseCode.OK, newsArticles);
    }
}
