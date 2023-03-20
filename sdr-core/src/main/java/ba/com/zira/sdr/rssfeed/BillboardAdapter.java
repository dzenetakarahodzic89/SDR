package ba.com.zira.sdr.rssfeed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import ba.com.zira.sdr.dao.model.NewsArticlesEntity;

public class BillboardAdapter implements RssReader {

    @Override
    public List<NewsArticlesEntity> read(SyndFeed feed, String url, String userId) {
        var newsArticleList = new ArrayList<NewsArticlesEntity>();
        for (SyndEntry entry : feed.getEntries()) {
            var newArticleEntry = new NewsArticlesEntity();
            newArticleEntry.setCreated(LocalDateTime.now());
            newArticleEntry.setCreatedBy(userId);
            newArticleEntry.setSource(url);
            newArticleEntry.setLink(entry.getLink());
            newArticleEntry.setContent(entry.getTitle());
            newsArticleList.add(newArticleEntry);
        }
        return newsArticleList;
    }

}
