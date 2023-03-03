package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.album.AlbumArtistResponse;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.dao.model.AlbumEntity;

@Repository
public class AlbumDAO extends AbstractDAO<AlbumEntity, Long> {

    public List<SongResponse> findSongsWithPlaytimeForAlbum(Long albumId) {
        var hql = "select new ba.com.zira.sdr.api.model.song.SongResponse(s.id, s.name, s.playtime) from AlbumEntity a join SongArtistEntity sa on sa.album.id = a.id join SongEntity s on s.id = sa.song.id where a.id = :albumId";
        TypedQuery<SongResponse> query = entityManager.createQuery(hql, SongResponse.class).setParameter("albumId", albumId);
        return query.getResultList();
    }
    public List<AlbumArtistResponse> findAllAlbumsForArtist(Long artistId) {
        var hql = "select distinct new ba.com.zira.sdr.api.model.album.AlbumArtistResponse(al.id, al.name,al.dateOfRelease) "
                + " from ArtistEntity as a"
                + " join SongArtistEntity as s "
                + " on a.id = s.artist.id "
                + " join AlbumEntity as al "
                + " on al.id = s.album.id "
                + " where a.id = :artistId";

        TypedQuery<AlbumArtistResponse> query = entityManager.createQuery(hql, AlbumArtistResponse.class).setParameter("artistId", artistId);
        return query.getResultList();
    }
}
