package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentSingleSongResponse;
import ba.com.zira.sdr.dao.model.InstrumentEntity;
import ba.com.zira.sdr.dao.model.InstrumentEntity_;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongEntity_;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity_;

@Repository
public class SongInstrumentDAO extends AbstractDAO<SongInstrumentEntity, Long> {

    public List<SongInstrumentSingleSongResponse> getAllBySongId(Long songId) {
        final CriteriaQuery<SongInstrumentSingleSongResponse> criteriaQuery = builder.createQuery(SongInstrumentSingleSongResponse.class);
        final Root<SongInstrumentEntity> root = criteriaQuery.from(SongInstrumentEntity.class);
        Join<SongInstrumentEntity, SongEntity> songs = root.join(SongInstrumentEntity_.song);
        Join<SongInstrumentEntity, InstrumentEntity> instruments = root.join(SongInstrumentEntity_.instrument);
        criteriaQuery.where(builder.equal(songs.get(SongEntity_.id), songId));
        criteriaQuery.multiselect(root.get(SongInstrumentEntity_.id), songs.get(SongEntity_.id), instruments.get(InstrumentEntity_.id))
                .distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
