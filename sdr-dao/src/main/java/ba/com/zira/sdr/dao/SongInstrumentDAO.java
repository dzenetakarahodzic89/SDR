package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentPersonResponse;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentSingleSongResponse;
import ba.com.zira.sdr.dao.model.InstrumentEntity;
import ba.com.zira.sdr.dao.model.InstrumentEntity_;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongEntity_;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity_;

@Repository
public class SongInstrumentDAO extends AbstractDAO<SongInstrumentEntity, Long> {

    public List<SongInstrumentSingleSongResponse> getAllBySongId(final Long songId) {
        final CriteriaQuery<SongInstrumentSingleSongResponse> criteriaQuery = builder.createQuery(SongInstrumentSingleSongResponse.class);
        final Root<SongInstrumentEntity> root = criteriaQuery.from(SongInstrumentEntity.class);
        Join<SongInstrumentEntity, SongEntity> songs = root.join(SongInstrumentEntity_.song);
        Join<SongInstrumentEntity, InstrumentEntity> instruments = root.join(SongInstrumentEntity_.instrument);
        criteriaQuery.where(builder.equal(songs.get(SongEntity_.id), songId));
        criteriaQuery.multiselect(root.get(SongInstrumentEntity_.id), songs.get(SongEntity_.id), instruments.get(InstrumentEntity_.id))
                .distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public List<SongInstrumentPersonResponse> getSongInstrumentByPersonId(final Long personId) {
        var hql = "select new ba.com.zira.sdr.api.model.songinstrument.SongInstrumentPersonResponse(si.id,s.id,s.name,i.id,i.name) from SongInstrumentEntity si "
                + "left join SongEntity s on si.song.id = s.id " + "left join InstrumentEntity i on si.instrument.id = i.id "
                + "where si.person.id=:personId";
        TypedQuery<SongInstrumentPersonResponse> query = entityManager.createQuery(hql, SongInstrumentPersonResponse.class)
                .setParameter("personId", personId);
        return query.getResultList();
    }

}
