package ba.com.zira.sdr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionSearchResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordSongAlbumEraResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;

@Repository
public class ChordProgressionDAO extends AbstractDAO<ChordProgressionEntity, Long> {
    public List<ChordProgressionResponse> getAllChordProgressions() {
        var hql = "select new ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse(r.id, r.name, r.status,r.information) from ChordProgressionEntity r";
        TypedQuery<ChordProgressionResponse> query = entityManager.createQuery(hql, ChordProgressionResponse.class);
        return query.getResultList();
    }

    public Map<Long, String> songsByChordProgression(final Long chordProgressionId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from SongEntity s where s.chordProgression.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", chordProgressionId);

        try {
            return q.getResultStream().collect(Collectors.toMap(LoV::getId, LoV::getName));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public List<ChordSongAlbumEraResponse> getAllChordProgressionSongNumberByEras() {
        var hql = "select distinct new ba.com.zira.sdr.api.model.chordprogression.ChordSongAlbumEraResponse(se.id,se.name,scp.id,ss.name) "
                + "from ChordProgressionEntity scp join SongEntity ss on scp.id = ss.chordProgression.id "
                + "join SongArtistEntity ssa on ssa.song.id =ss.id join AlbumEntity sa on ssa.album.id =sa.id "
                + "join EraEntity se on sa.era.id =se.id";
        TypedQuery<ChordSongAlbumEraResponse> query = entityManager.createQuery(hql, ChordSongAlbumEraResponse.class);
        return query.getResultList();
    }

    public List<LoV> getChordProgressionLoV() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(c.id,c.name) from ChordProgressionEntity c";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public List<ChordProgressionSearchResponse> searchChordProgression(final String chordProgressionName, final String sortBy,
            final List<Long> eraIds, final List<Long> genreIds, final int page, final int pageSize) {
        var query = "select new ba.com.zira.sdr.api.model.chordprogression.ChordProgressionSearchResponse(scp.id, scp.name, scp.outlineText) from"
                + " ChordProgressionEntity scp inner join SongEntity ss on scp.id  = ss.chordProgression.id inner join GenreEntity sg on ss.genre.id = sg.id inner join SongArtistEntity ssa on ss.id = ssa.song.id "
                + " inner join AlbumEntity sa on ssa.album.id=sa.id inner join EraEntity se on sa.era.id=se.id where"
                + " (scp.name like :chordProgressionName or :chordProgressionName is null or :chordProgressionName = '') and (coalesce(:eraIds, null) is null or sa.era.id in :eraIds) and (coalesce(:genreIds, null) is null or ss.genre.id in :eraIds)";
        if ("last_date".equals(sortBy)) {
            query += " order by ss.modified desc";
        }

        else {
            query += " order by ss.name";
        }

        var q = entityManager.createQuery(query, ChordProgressionSearchResponse.class);

        if (chordProgressionName != null && !chordProgressionName.isEmpty()) {
            q.setParameter("chordProgressionName", "%" + chordProgressionName + "%");
        } else {
            q.setParameter("chordProgressionName", null);
        }

        if (eraIds != null) {
            q.setParameter("eraIds", eraIds);
        } else {
            q.setParameter("eraIds", null);
        }
        if (genreIds != null) {
            q.setParameter("genreIds", genreIds);
        } else {
            q.setParameter("genreIds", null);
        }
        // Apply pagination
        int firstResult = (page - 1) * pageSize;
        int maxResults = pageSize;
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);

        return q.getResultList();
    }

}
