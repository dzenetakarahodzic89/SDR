package ba.com.zira.sdr.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.List;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.dao.model.AlbumEntity;

@Repository
public class AlbumDAO extends AbstractDAO<AlbumEntity, Long> {

    public List<SongResponse> findSongsWithPlaytimeForAlbum(Long albumId) {
        var hql = "select new ba.com.zira.sdr.api.model.song.SongResponse(s.id, s.name, s.playtime) from AlbumEntity a join SongArtistEntity sa on sa.album.id = a.id join SongEntity s on s.id = sa.song.id where a.id = :albumId";
        TypedQuery<SongResponse> query = entityManager.createQuery(hql, SongResponse.class).setParameter("albumId", albumId);
        return query.getResultList();
    }
}
