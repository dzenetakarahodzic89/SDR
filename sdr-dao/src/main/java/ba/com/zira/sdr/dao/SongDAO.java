package ba.com.zira.sdr.dao;


import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.SongEntity;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.TypedQuery;
import ba.com.zira.sdr.api.model.lov.LoV;


@Repository
public class SongDAO extends AbstractDAO<SongEntity, Long> {

    public Map<Long, String> songsByGenre(Long genreId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from SongEntity s where s.genre.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", genreId);
        try {
            return q.getResultStream().collect(Collectors.toMap(sl -> sl.getId(), sl -> sl.getName()));
        } catch (Exception e) {
            return null;
        }

    }

}
