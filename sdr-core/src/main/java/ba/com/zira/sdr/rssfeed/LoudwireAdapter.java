package ba.com.zira.sdr.rssfeed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rometools.rome.feed.synd.SyndFeed;

import ba.com.zira.sdr.dao.model.NewsArticlesEntity;

public class LoudwireAdapter implements RssReader {

    @Override
    public List<NewsArticlesEntity> read(SyndFeed feed, String url, String userId) {
        var newsArticleList = new ArrayList<NewsArticlesEntity>();
        for (var entry : feed.getEntries()) {
            var newArticleEntry = new NewsArticlesEntity();
            newArticleEntry.setCreated(LocalDateTime.now());
            newArticleEntry.setCreatedBy(userId);
            newArticleEntry.setSource(url);
            newArticleEntry.setLink(entry.getLink());
            newArticleEntry.setImageUrl(entry.getForeignMarkup().get(0).getAttributeValue("url"));
            newArticleEntry.setContent(entry.getTitle());
            newsArticleList.add(newArticleEntry);
        }
        return newsArticleList;
    }

}
