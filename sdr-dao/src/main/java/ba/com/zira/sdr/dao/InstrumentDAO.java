package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.instrument.InstrumentSongResponse;
import ba.com.zira.sdr.api.instrument.ResponseSongInstrumentEra;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.InstrumentEntity;

@Repository
public class InstrumentDAO extends AbstractDAO<InstrumentEntity, Long> {

    public List<ResponseSongInstrumentEra> findAllSongsInErasForInstruments(Long instrumentId) {
        var hql = "select new ba.com.zira.sdr.api.instrument.ResponseSongInstrumentEra(e.name, COUNT(DISTINCT s.id))"
                + " from InstrumentEntity as i" + " join SongInstrumentEntity as si" + " on i.id =si.id" + " join SongEntity as s"
                + " on si.song.id = s.id" + " join SongArtistEntity as sa" + " on s.id = sa.song.id" + " join AlbumEntity as a"
                + " on sa.album.id = a.id" + " join EraEntity as e" + " on a.era.id = e.id" + " where i.id = :instrumentId"
                + " group by e.name";

        TypedQuery<ResponseSongInstrumentEra> query = entityManager.createQuery(hql, ResponseSongInstrumentEra.class)
                .setParameter("instrumentId", instrumentId);
        return query.getResultList();
    }

    public Map<Long, String> getInstrumentNames(List<Long> ids) {
        var hql = new StringBuilder(
                "select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.name) from InstrumentEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }

    public List<InstrumentSongResponse> getInstrumentsForSong(Long songId) {
        var hql = "select new ba.com.zira.sdr.api.instrument.InstrumentSongResponse(si.id,si.name) from SongInstrumentEntity ssi join SongEntity ss on ss.id = ssi.song.id join InstrumentEntity si on si.id = ssi.instrument.id where ss.id = :songId";
        TypedQuery<InstrumentSongResponse> q = entityManager.createQuery(hql, InstrumentSongResponse.class).setParameter("songId", songId);
        return q.getResultList();
    }

}
