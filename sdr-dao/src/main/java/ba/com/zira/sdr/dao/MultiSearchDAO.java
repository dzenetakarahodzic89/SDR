package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse;
import ba.com.zira.sdr.api.model.wiki.WikiResponse;
import ba.com.zira.sdr.dao.model.MultiSearchEntity;

@Repository
public class MultiSearchDAO extends AbstractDAO<MultiSearchEntity, Long> {
    public List<MultiSearchResponse> getAllMultiSearches(String name) {
        var hql = "select new ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse(r.id, r.name, r.type) from MultiSearchEntity r where r.name like :name";
        TypedQuery<MultiSearchResponse> query = entityManager.createQuery(hql, MultiSearchResponse.class).setParameter("name",
                '%' + name + '%');
        return query.getResultList();
    }

    public List<WikiResponse> getWiki() {
        var hql = "select new ba.com.zira.sdr.api.model.wiki.WikiResponse(count(r.id), r.type) from MultiSearchEntity r group by r.type";
        TypedQuery<WikiResponse> query = entityManager.createQuery(hql, WikiResponse.class);
        return query.getResultList();

    }

    public List<MultiSearchResponse> getAllMultiSearches() {
        var hql = "select new ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse(r.id, r.name, r.type) from MultiSearchEntity r";
        TypedQuery<MultiSearchResponse> query = entityManager.createQuery(hql, MultiSearchResponse.class);
        return query.getResultList();
    }

    public List<MultiSearchResponse> getRandomMultiSearches() {
        var hql = "select new ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse(r.id, r.name, r.type) from MultiSearchEntity r order by random()";
        TypedQuery<MultiSearchResponse> query = entityManager.createQuery(hql, MultiSearchResponse.class).setMaxResults(10);
        return query.getResultList();
    }

    public void deleteTable() {
        var hql = "drop table sat_multi_search";
        var query = entityManager.createNativeQuery(hql);
        query.executeUpdate();
    }

    public void createTableAndFillWithData() {
        var hql = "create table sat_multi_search as (select    cc2.id,    cc2.name || ' ' || cc2.surname as name,"
                + "    'PERSON' as type,'' as spotify_id from    sat_person cc2 union select    cf.id,"
                + "    cf.name,    'SONG',    cf.spotify_id from    sat_song cf union select    cl.id,"
                + "    cl.name,    'ALBUM',    cl.spotify_id from    sat_album cl union select    cl.id,"
                + "    cl.name,    'ARTIST',    cl.spotify_id from    sat_artist cl)";
        var query = entityManager.createNativeQuery(hql);
        query.executeUpdate();
    }

    public Map<String, MultiSearchResponse> getCurrentSpotifyIds() {
        var hql = "select new ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse(r.name, r.type, r.spotifyId) from MultiSearchEntity r";
        TypedQuery<MultiSearchResponse> query = entityManager.createQuery(hql, MultiSearchResponse.class);
        return query.getResultList().stream().filter(a -> a.getSpotifyId() != null)
                .collect(Collectors.toMap(MultiSearchResponse::getSpotifyId, a -> a, (a1, a2) -> a1));
    }

}
