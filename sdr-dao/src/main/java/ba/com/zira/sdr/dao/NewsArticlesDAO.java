package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.newsarticles.NewsArticleResponse;
import ba.com.zira.sdr.dao.model.NewsArticlesEntity;

@Repository
public class NewsArticlesDAO extends AbstractDAO<NewsArticlesEntity, Long> {
    public List<NewsArticleResponse> getFreshContent() {
        String hql = "SELECT new ba.com.zira.sdr.api.model.newsarticles.NewsArticleResponse(n.id,n.imageUrl,n.link,n.content) FROM NewsArticlesEntity n order by n.created desc";
        TypedQuery<NewsArticleResponse> query = entityManager.createQuery(hql, NewsArticleResponse.class).setMaxResults(10);
        return query.getResultList();
    }
}
