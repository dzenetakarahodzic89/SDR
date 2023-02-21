package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;

@Repository
public class ChordProgressionDAO extends AbstractDAO<ChordProgressionEntity, Long> {
    public List<ChordProgressionResponse> getAllChordProgressions() {
        var hql = "select new ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse(r.id, r.name, r.status,r.information) from ChordProgressionEntity r";
        TypedQuery<ChordProgressionResponse> query = entityManager.createQuery(hql, ChordProgressionResponse.class);
        return query.getResultList();
    }

    public Map<Long, String> songsByChordProgression(Long chordProgressionId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from SongEntity s where s.chordProgression.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", chordProgressionId);
        try {
            return q.getResultStream().collect(Collectors.toMap(sl -> sl.getId(), sl -> sl.getName()));
        } catch (Exception e) {
            return null;
        }

    }
}