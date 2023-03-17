package ba.com.zira.sdr.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetSongResponse;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;

@Repository
public class NoteSheetDAO extends AbstractDAO<NoteSheetEntity, Long> {

    public List<NoteSheetEntity> getNoteSheetsByIds(final List<Long> noteSheetIds) {
        if (noteSheetIds == null || noteSheetIds.isEmpty()) {
            return Collections.emptyList();
        }

        String hql = "SELECT n FROM NoteSheetEntity n WHERE n.id IN (:noteSheetIds)";
        TypedQuery<NoteSheetEntity> query = entityManager.createQuery(hql, NoteSheetEntity.class);
        query.setParameter("noteSheetIds", noteSheetIds);

        return query.getResultList();
    }

    public NoteSheetSongResponse getNoteSheetById(Long noteSheetId) {
        var hql = "SELECT DISTINCT new ba.com.zira.sdr.api.model.notesheet.NoteSheetSongResponse (ns.id, si.name,si.id, ss.dateOfRelease, ss.name, ss.id, ns.sheetContent, sp.countryId, co.flagAbbriviation, sa.id,sa.name || ' ' || sa.surname, sp.name || ' ' || sp.surname ) FROM NoteSheetEntity ns LEFT JOIN SongEntity s ON ns.song = s.id LEFT JOIN SongArtistEntity sae ON s.id = sae.song.id LEFT JOIN ArtistEntity sa ON sae.artist.id = sa.id LEFT JOIN PersonArtistEntity spa ON sa.id = spa.artist.id LEFT JOIN PersonEntity sp ON spa.person.id = sp.id LEFT JOIN CountryEntity co ON sp.countryId = co.id LEFT JOIN ba.com.zira.sdr.dao.model.SongEntity ss ON ns.song = ss.id LEFT JOIN ba.com.zira.sdr.dao.model.InstrumentEntity si ON ns.instrument = si.id WHERE ns.id = :id";
        TypedQuery<NoteSheetSongResponse> q = entityManager.createQuery(hql, NoteSheetSongResponse.class).setParameter("id", noteSheetId);
        return q.getSingleResult();
    }

    public NoteSheetEntity getNoteSheetByInstrumentAndSong(Long songId, Long instrumentId) {
        String hql = "SELECT ns FROM NoteSheetEntity ns WHERE ns.song.id = :songId AND ns.instrument.id = :instrumentId";
        TypedQuery<NoteSheetEntity> query = entityManager.createQuery(hql, NoteSheetEntity.class).setParameter("songId", songId)
                .setParameter("instrumentId", instrumentId);

        return query.getSingleResult();
    }

}
