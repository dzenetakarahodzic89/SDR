package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.label.LabelArtistResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity_;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.LabelEntity_;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity_;

@Repository
public class LabelDAO extends AbstractDAO<LabelEntity, Long> {

    public LabelArtistResponse getById(Long labelId) {
        var hql = "select new ba.com.zira.sdr.api.model.label.LabelArtistResponse(la.id, la.name, la.outlineText,la.information,la.foundingDate,f.name || ' ' || f.surname,f.id) from LabelEntity la left join PersonEntity f on la.founder.id = f.id where la.id =:id";
        TypedQuery<LabelArtistResponse> q = entityManager.createQuery(hql, LabelArtistResponse.class).setParameter("id", labelId);
        return q.getSingleResult();
    }

    public Map<Long, String> getLabelNames(List<Long> ids) {
        var hql = new StringBuilder("select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.name) from LabelEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }

    public List<LoV> getLabelLoVs() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(l.id,l.name) from LabelEntity l";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public List<LabelEntity> findByArtistId(final Long artistId) {
        final CriteriaQuery<LabelEntity> criteriaQuery = builder.createQuery(LabelEntity.class);
        final Root<LabelEntity> root = criteriaQuery.from(LabelEntity.class);
        Join<LabelEntity, SongArtistEntity> songArtists = root.join(LabelEntity_.songArtists);
        Join<SongArtistEntity, ArtistEntity> artists = songArtists.join(SongArtistEntity_.artist);
        criteriaQuery.where(builder.equal(artists.get(ArtistEntity_.id), artistId));

        criteriaQuery.select(root).distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
