package ba.com.zira.sdr.rssfeed;

import java.io.IOException;
import java.util.List;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;

import ba.com.zira.sdr.dao.model.NewsArticlesEntity;

public interface RssReader {
    List<NewsArticlesEntity> read(SyndFeed feed, String url, String userId) throws IOException, FeedException;
}