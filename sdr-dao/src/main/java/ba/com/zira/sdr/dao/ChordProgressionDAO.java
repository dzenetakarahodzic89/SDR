package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;

@Repository
public class ChordProgressionDAO extends AbstractDAO<ChordProgressionEntity, Long> {
    public List<ChordProgressionResponse> getAllChordProgressions() {
        var hql = "select new ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse(r.id, r.name, r.status,r.information) from ChordProgressionEntity r";
        TypedQuery<ChordProgressionResponse> query = entityManager.createQuery(hql, ChordProgressionResponse.class);
        return query.getResultList();
    }
}
