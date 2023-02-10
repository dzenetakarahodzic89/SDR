package ba.com.zira.sdr.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.List;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse;
import ba.com.zira.sdr.api.model.wiki.WikiResponse;
import ba.com.zira.sdr.dao.model.MultiSearchEntity;

@Repository
public class MultiSearchDAO extends AbstractDAO<MultiSearchEntity, Long> {
    public List<MultiSearchResponse> getAllMultiSearches(String name) {
        var hql = "select new ba.com.zira.rpgcreation.api.model.multisearch.MultiSearchResponse(r.id, r.name, r.type) from MultiSearchEntity r where r.name like :name";
        TypedQuery<MultiSearchResponse> query = entityManager.createQuery(hql, MultiSearchResponse.class).setParameter("name",
                '%' + name + '%');
        return query.getResultList();
    }

    public List<WikiResponse> getWiki() {
        var hql = "select new ba.com.zira.rpgcreation.api.model.wiki.WikiResponse(count(r.id), r.type) from MultiSearchEntity r group by r.type";
        TypedQuery<WikiResponse> query = entityManager.createQuery(hql, WikiResponse.class);
        return query.getResultList();

    }

    public List<MultiSearchResponse> getAllMultiSearches() {
        var hql = "select new ba.com.zira.rpgcreation.api.model.multisearch.MultiSearchResponse(r.id, r.name, r.type) from MultiSearchEntity r";
        TypedQuery<MultiSearchResponse> query = entityManager.createQuery(hql, MultiSearchResponse.class);
        return query.getResultList();
    }

    public List<MultiSearchResponse> getRandomMultiSearches() {
        var hql = "select new ba.com.zira.rpgcreation.api.model.multisearch.MultiSearchResponse(r.id, r.name, r.type) from MultiSearchEntity r order by random()";
        TypedQuery<MultiSearchResponse> query = entityManager.createQuery(hql, MultiSearchResponse.class).setMaxResults(10);
        return query.getResultList();
    }

}
