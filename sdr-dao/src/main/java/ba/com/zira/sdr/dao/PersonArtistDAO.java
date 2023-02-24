package ba.com.zira.sdr.dao;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PersonArtistDAO extends AbstractDAO<PersonArtistEntity, Long> {

    public Map<Long, String> personArtistEntityByArtist(Long artistId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.status) from PersonArtistEntity s where s.artist.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", artistId);
        try {
            return q.getResultStream().collect(Collectors.toMap(LoV::getId, LoV::getName));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
