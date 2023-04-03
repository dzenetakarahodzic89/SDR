package ba.com.zira.sdr.dao;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;

@Repository
public class SongPlaylistDAO extends AbstractDAO<SongPlaylistEntity, Long> {

    @Transactional
    public void deleteByPlaylistId(Long playlistId) {
        Query query = entityManager.createQuery("delete from SongPlaylistEntity sp where sp.playlist.id = :playlistId");
        query.setParameter("playlistId", playlistId);
        query.executeUpdate();
    }

    @Transactional
    public void deleteByPlaylistIdAndSongId(Long playlistId, Long songId) {
        Query query = entityManager
                .createQuery("delete from SongPlaylistEntity sp where sp.playlist.id = :playlistId and sp.song.id = :songId");
        query.setParameter("playlistId", playlistId).setParameter("songId", songId);
        query.executeUpdate();
    }

}