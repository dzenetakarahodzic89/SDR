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
        Query query = entityManager.createQuery("DELETE FROM SongPlaylistEntity sp WHERE sp.playlist.id = :playlistId");
        query.setParameter("playlistId", playlistId);
        query.executeUpdate();
    }
}