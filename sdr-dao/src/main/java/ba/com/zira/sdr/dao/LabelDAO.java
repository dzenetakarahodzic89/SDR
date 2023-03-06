package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.label.LabelArtistResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.LabelEntity;

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

}
