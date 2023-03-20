package ba.com.zira.sdr.rssfeed;

import java.util.List;

import com.rometools.rome.feed.synd.SyndFeed;

import ba.com.zira.sdr.dao.model.NewsArticlesEntity;

public interface RssReader {
    List<NewsArticlesEntity> read(SyndFeed feed, String url, String userId);
}