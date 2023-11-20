package ba.com.zira.sdr.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lyric.LyricsSongResponse;
import ba.com.zira.sdr.dao.model.LyricEntity;

@Repository
public class LyricDAO extends AbstractDAO<LyricEntity, Long> {

    public List<LyricEntity> getLyricsByIds(final List<Long> lyricIds) {
        if (lyricIds == null || lyricIds.isEmpty()) {
            return Collections.emptyList();
        }

        String hql = "SELECT l FROM LyricEntity l WHERE l.id IN (:lyricIds)";
        TypedQuery<LyricEntity> query = entityManager.createQuery(hql, LyricEntity.class);
        query.setParameter("lyricIds", lyricIds);

        return query.getResultList();
    }

    public List<LyricsSongResponse> getAllLyricsForAlbum(Long albumId) {
        var hql = "select new ba.com.zira.sdr.api.model.song.LyricsSongResponse(s.id, s.name, l.language, l.text) from LyricEntity l join SongEntity s on s.id = l.song.id "
                + "join SongArtistEntity sa on s.id = sa.song.id join AlbumEntity a on a.id=sa.album.id where a.id = :albumId";

        TypedQuery<LyricsSongResponse> query = entityManager.createQuery(hql, LyricsSongResponse.class).setParameter("albumId", albumId);
        return query.getResultList();
    }

}
