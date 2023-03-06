package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.album.AlbumSearchRequest;

import ba.com.zira.sdr.api.model.album.AlbumArtistResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.AlbumEntity_;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity_;
import ba.com.zira.sdr.dao.model.EraEntity;
import ba.com.zira.sdr.dao.model.EraEntity_;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.GenreEntity_;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity_;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongEntity_;

@Repository
public class AlbumDAO extends AbstractDAO<AlbumEntity, Long> {

    public List<SongResponse> findSongsWithPlaytimeForAlbum(final Long albumId) {
        var hql = "select new ba.com.zira.sdr.api.model.song.SongResponse(s.id, s.name, s.playtime, g.name) from AlbumEntity a join SongArtistEntity sa on sa.album.id = a.id join SongEntity s on s.id = sa.song.id join GenreEntity g on g.id = s.genre.id where a.id = :albumId";
        TypedQuery<SongResponse> query = entityManager.createQuery(hql, SongResponse.class).setParameter("albumId", albumId);
        return query.getResultList();
    }

    public List<AlbumEntity> findAllAlbumsByNameGenreEraArtist(AlbumSearchRequest albumSearchRequest) {
        final CriteriaQuery<AlbumEntity> criteriaQuery = builder.createQuery(AlbumEntity.class);
        final Root<AlbumEntity> root = criteriaQuery.from(AlbumEntity.class);

        Join<AlbumEntity, SongArtistEntity> songArtists = root.join(AlbumEntity_.songArtists);
        Join<SongArtistEntity, ArtistEntity> artists = songArtists.join(SongArtistEntity_.artist);
        Join<SongEntity, GenreEntity> genres = songArtists.join(SongArtistEntity_.song).join(SongEntity_.genre);
        Join<AlbumEntity, EraEntity> eras = root.join(AlbumEntity_.era);
        List<Predicate> predicates = new ArrayList<>();

        if (albumSearchRequest.getEras() != null && !albumSearchRequest.getEras().isEmpty()) {
            predicates.add(eras.get(EraEntity_.id).in(albumSearchRequest.getEras()));
        }
        if (albumSearchRequest.getArtists() != null && !albumSearchRequest.getArtists().isEmpty()) {
            predicates.add(artists.get(ArtistEntity_.id).in(albumSearchRequest.getArtists()));
        }

        if (albumSearchRequest.getGenres() != null && !albumSearchRequest.getGenres().isEmpty()) {
            predicates.add(genres.get(GenreEntity_.id).in(albumSearchRequest.getGenres()));
        }
        if (albumSearchRequest.getName() != null && !albumSearchRequest.getName().isEmpty()) {
            predicates.add(builder.like(root.get(AlbumEntity_.name), "%" + albumSearchRequest.getName() + "%"));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        criteriaQuery.select(root).distinct(true);

        if (albumSearchRequest.getSort() != null && albumSearchRequest.getSort().equals("last_edit")) {
            criteriaQuery.orderBy(builder.asc(root.get(AlbumEntity_.modified)));

        }

        if (albumSearchRequest.getSort() != null && albumSearchRequest.getSort().equals("alphabetical")) {
            criteriaQuery.orderBy(builder.asc(root.get(AlbumEntity_.name)));

        }

        return entityManager.createQuery(criteriaQuery).getResultList();
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
