package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.album.AlbumArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumPersonResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.dao.model.AlbumEntity;

@Repository
public class AlbumDAO extends AbstractDAO<AlbumEntity, Long> {

    public List<SongResponse> findSongsWithPlaytimeForAlbum(final Long albumId) {
        var hql = "select new ba.com.zira.sdr.api.model.song.SongResponse(s.id, s.name, s.playtime, g.name) from AlbumEntity a join SongArtistEntity sa on sa.album.id = a.id join SongEntity s on s.id = sa.song.id join GenreEntity g on g.id = s.genre.id where a.id = :albumId";
        TypedQuery<SongResponse> query = entityManager.createQuery(hql, SongResponse.class).setParameter("albumId", albumId);
        return query.getResultList();
    }

    public List<AlbumArtistResponse> findAllAlbumsForArtist(Long artistId) {
        var hql = "select distinct new ba.com.zira.sdr.api.model.album.AlbumArtistResponse(al.id, al.name,al.dateOfRelease) "
                + " from ArtistEntity as a" + " join SongArtistEntity as s " + " on a.id = s.artist.id " + " join AlbumEntity as al "
                + " on al.id = s.album.id " + " where a.id = :artistId";

        TypedQuery<AlbumArtistResponse> query = entityManager.createQuery(hql, AlbumArtistResponse.class).setParameter("artistId",
                artistId);
        return query.getResultList();
    }

    public List<AlbumPersonResponse> findAlbumByPersonId(Long personId) {
        var hql = "select distinct new ba.com.zira.sdr.api.model.album.AlbumPersonResponse(al.id, al.dateOfRelease, al.information, al.name, al.status) from PersonEntity sp "
                + " inner join PersonArtistEntity spa on sp.id = spa.person.id inner join ArtistEntity sa on spa.artist.id =sa.id inner join SongArtistEntity ssa on sa.id =ssa.artist.id inner join AlbumEntity al on ssa.album.id =al.id where sp.id = :personId";

        TypedQuery<AlbumPersonResponse> query = entityManager.createQuery(hql, AlbumPersonResponse.class).setParameter("personId",
                personId);
        return query.getResultList();
    }

    public List<String> findAllAlbumArtists(final Long albumId) {
        var hql = "select distinct artist.name || ' ' || artist.surname from AlbumEntity a join SongArtistEntity sa on sa.album.id = a.id join ArtistEntity artist on artist.id = sa.artist.id where a.id = :albumId";
        TypedQuery<String> query = entityManager.createQuery(hql, String.class).setParameter("albumId", albumId);

        return query.getResultList();
    }

    public List<LoV> findAlbumsToFetchFromSpotify(int responseLimit) {
        var cases = "case when sa.artist.surname is null then concat('album:',a.name,' ','artist:',sa.artist.name) else"
                + " concat('album:',a.name,' ','artist:',sa.artist.name,' ',sa.artist.surname) end";
        var hql = "select distinct new ba.com.zira.sdr.api.model.lov.LoV(a.id," + cases
                + ") from AlbumEntity a left join SpotifyIntegrationEntity si on "
                + "a.id = si.objectId and si.objectType like 'ALBUM' join SongArtistEntity sa on a.id=sa.album.id where si.id = null";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class).setMaxResults(responseLimit);

        return query.getResultList();
    }
}
