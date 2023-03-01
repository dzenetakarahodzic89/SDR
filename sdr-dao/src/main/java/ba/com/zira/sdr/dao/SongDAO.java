package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.generateplaylist.PlaylistGenerateRequest;
import ba.com.zira.sdr.api.model.genre.SongGenreEraLink;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.SongSingleResponse;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.AlbumEntity_;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity_;
import ba.com.zira.sdr.dao.model.CountryEntity;
import ba.com.zira.sdr.dao.model.CountryEntity_;
import ba.com.zira.sdr.dao.model.EraEntity;
import ba.com.zira.sdr.dao.model.EraEntity_;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.GenreEntity_;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import ba.com.zira.sdr.dao.model.PersonArtistEntity_;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.PersonEntity_;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity_;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongEntity_;

@Repository
public class SongDAO extends AbstractDAO<SongEntity, Long> {
    public List<SongEntity> findSongsByIdArray(final List<Long> songIds) {
        final CriteriaQuery<SongEntity> cQuery = builder.createQuery(SongEntity.class);
        final Root<SongEntity> root = cQuery.from(SongEntity.class);
        return entityManager.createQuery(cQuery.where(root.get(SongEntity_.id).in(songIds))).getResultList();
    }

    public List<Object[]> generatePlaylistSql(final PlaylistGenerateRequest request) {
        String nativeQueryString = "";
        nativeQueryString += "select \r\n"
                + "        (ss.id || ';;;;;' || coalesce(ss.name,'null') || ';;;;;' || coalesce(ss.playtime,'null') || ';;;;;' || coalesce(ss.cover_id, -1) || ';;;;;' || coalesce(ss.remix_id, -1)) as SONG,\r\n"
                + "        string_agg(distinct (sa2.id || ';;;;;' || coalesce(sa2.name,'null') || ';;;;;' || coalesce(sa2.surname,'null')), ',,,,,') as ARTISTS,\r\n"
                + "        string_agg(distinct (sa.id || ';;;;;' || coalesce(sa.name,'null')), ',,,,,') as ALBUMS,\r\n"
                + "        (sg.id || ';;;;;' || coalesce(sg.name,'null') || ';;;;;' || coalesce(smg.id, -1) || ';;;;;' || coalesce(smg.name, 'null')) as GENRE,\r\n"
                + "        string_agg(distinct (sc.id || ';;;;;' || coalesce(sc.name,'null') || ';;;;;' || coalesce(sc.flag_abbriviation,'null') || ';;;;;' || coalesce(sc.region,'null')), ',,,,,') as COUNTRIES\r\n"
                + "from \r\n" + "        sat_song ss \r\n" + "join \r\n" + "        sat_genre sg ON ss.genre_id=sg.id\r\n" + "join \r\n"
                + "        sat_song_artist ssa on ss.id=ssa.song_id \r\n" + "join \r\n" + "        sat_album sa on sa.id=ssa.album_id \r\n"
                + "join \r\n" + "        sat_artist sa2 on sa2.id=ssa.artist_id \r\n" + "join\r\n"
                + "        sat_person_artist spa on sa2.id=spa.artist_id \r\n" + "join \r\n"
                + "        sat_person sp on sp.id=spa.person_id \r\n" + "join \r\n" + "        sat_country sc on sc.id=sp.country_id\r\n"
                + "left join \r\n" + "        sat_genre smg on smg.id=sg.genre_id\r\n" + "where 1=1\r\n";

        if (request.getGenreId() != null) {
            nativeQueryString += "and sg.id = " + request.getGenreId().toString() + "\r\n";
        }

        if (request.getIncludeCovers() == false) {
            nativeQueryString += "and ss.cover_id is null \r\n";
        }

        if (request.getIncludeRemixes() == false) {
            nativeQueryString += "and ss.remix_id is null \r\n";
        }

        nativeQueryString += "group by \r\n" + "        ss.id,\r\n" + "        sg.id,\r\n" + "        smg.id\r\n" + "order by random()\r\n";

        if (request.getAmountOfSongs() != null) {
            nativeQueryString += "limit " + request.getAmountOfSongs().toString();
        }

        Query q = entityManager.createNativeQuery(nativeQueryString);

        return q.getResultList();
    }

    public List<Tuple> generatePlaylistHql(final PlaylistGenerateRequest request) {
        String hql = "select  " + "        ss.name, " + "        count(distinct sa2.name), " + "        count(distinct sa.name), "
                + "        sg.name, " + "        count(distinct sc.name) " + "from  " + "        SongEntity ss  " + "join  "
                + "        GenreEntity sg ON ss.genre.id=sg.id " + "join  " + "        SongArtistEntity ssa on ss.id=ssa.song.id  "
                + "join  " + "        AlbumEntity sa on sa.id=ssa.album.id  " + "join  "
                + "        ArtistEntity sa2 on sa2.id=ssa.artist.id  " + "join "
                + "        PersonArtistEntity spa on sa2.id=spa.artist.id  " + "join  " + "        PersonEntity sp on sp.id=spa.person.id  "
                + "join  " + "        CountryEntity sc on sc.id=sp.country.id " + "group by  " + "        ss.name, "
                + "        sg.name        ";
        TypedQuery<Tuple> query = entityManager.createQuery(hql, Tuple.class);
        return query.getResultList();
    }

    public List<Tuple> generatePlaylist(final PlaylistGenerateRequest request) {
        final CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();

        final Root<SongEntity> songRoot = criteriaQuery.from(SongEntity.class);
        final Root<GenreEntity> genreRoot = criteriaQuery.from(GenreEntity.class);
        final Root<SongArtistEntity> songArtistRoot = criteriaQuery.from(SongArtistEntity.class);
        final Root<ArtistEntity> artistRoot = criteriaQuery.from(ArtistEntity.class);
        final Root<AlbumEntity> albumRoot = criteriaQuery.from(AlbumEntity.class);
        final Root<PersonArtistEntity> personArtistRoot = criteriaQuery.from(PersonArtistEntity.class);
        final Root<PersonEntity> personRoot = criteriaQuery.from(PersonEntity.class);
        final Root<CountryEntity> countryRoot = criteriaQuery.from(CountryEntity.class);

        ArrayList<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(songRoot.get(SongEntity_.genre).get(GenreEntity_.id), genreRoot.get(GenreEntity_.id)));
        predicates.add(builder.equal(songRoot.get(SongEntity_.id), songArtistRoot.get(SongArtistEntity_.song).get(SongEntity_.id)));
        predicates.add(builder.equal(albumRoot.get(AlbumEntity_.id), songArtistRoot.get(SongArtistEntity_.album).get(AlbumEntity_.id)));
        predicates.add(builder.equal(artistRoot.get(ArtistEntity_.id), songArtistRoot.get(SongArtistEntity_.artist).get(ArtistEntity_.id)));
        predicates.add(
                builder.equal(artistRoot.get(ArtistEntity_.id), personArtistRoot.get(PersonArtistEntity_.artist).get(ArtistEntity_.id)));
        predicates.add(
                builder.equal(personRoot.get(PersonEntity_.id), personArtistRoot.get(PersonArtistEntity_.person).get(PersonEntity_.id)));
        predicates.add(builder.equal(personRoot.get(PersonEntity_.country).get(CountryEntity_.id), countryRoot.get(CountryEntity_.id)));

        if (request.getIncludeCovers() == false) {
            predicates.add(builder.isNull(songRoot.get(SongEntity_.cover).get(SongEntity_.id)));
        }

        if (request.getIncludeRemixes() == false) {
            predicates.add(builder.isNull(songRoot.get(SongEntity_.remix).get(SongEntity_.id)));
        }

        if (request.getGenreId() != null) {
            predicates.add(builder.equal(songRoot.get(SongEntity_.genre).get(GenreEntity_.id), request.getGenreId()));
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);

        criteriaQuery.orderBy(builder.asc(builder.function("random", null)));

        ;

        return entityManager.createQuery(criteriaQuery
                .multiselect(songRoot.as(SongEntity.class), genreRoot.as(GenreEntity.class),
                        builder.function("string_agg", String.class, artistRoot.get(ArtistEntity_.id)))
                .where(predicateArray).groupBy(songRoot, genreRoot)).setMaxResults(request.getAmountOfSongs().intValue()).getResultList();
    }

    public Map<Long, String> songsByGenre(Long genreId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from SongEntity s where s.genre.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", genreId);
        try {
            return q.getResultStream().collect(Collectors.toMap(LoV::getId, LoV::getName));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public SongSingleResponse getById(Long songId) {
        var hql = "select new ba.com.zira.sdr.api.model.song.SongSingleResponse(ss.id, ss.name, ss.outlineText,ss.information,ss.dateOfRelease,scp.name,sg.name,sg.id) from SongEntity ss left join ChordProgressionEntity scp on ss.chordProgression.id =scp.id left join GenreEntity sg on ss.genre.id = sg.id where ss.id =:id";
        TypedQuery<SongSingleResponse> q = entityManager.createQuery(hql, SongSingleResponse.class).setParameter("id", songId);
        return q.getSingleResult();
    }

    public List<SongGenreEraLink> findSongGenreEraLinks() {
        final CriteriaQuery<SongGenreEraLink> criteriaQuery = builder.createQuery(SongGenreEraLink.class);
        final Root<SongEntity> root = criteriaQuery.from(SongEntity.class);
        Join<SongEntity, SongArtistEntity> songArtists = root.join(SongEntity_.songArtists);
        Join<SongEntity, GenreEntity> sgenres = root.join(SongEntity_.genre);
        Join<SongArtistEntity, AlbumEntity> albumArtist = songArtists.join(SongArtistEntity_.album);
        Join<AlbumEntity, EraEntity> eraAlbum = albumArtist.join(AlbumEntity_.era);
        Join<GenreEntity, GenreEntity> genres = sgenres.join(GenreEntity_.mainGenre, JoinType.LEFT);

        Expression<Long> idSelectCase = builder.<Long> selectCase()
                .when(genres.get(GenreEntity_.id).isNotNull(), genres.get(GenreEntity_.id)).otherwise(sgenres.get(GenreEntity_.id));
        Expression<String> nameSelectCase = builder.<String> selectCase()
                .when(genres.get(GenreEntity_.name).isNotNull(), genres.get(GenreEntity_.name)).otherwise(sgenres.get(GenreEntity_.name));

        criteriaQuery.multiselect(root.get(SongEntity_.id), root.get(SongEntity_.name), idSelectCase, nameSelectCase,
                eraAlbum.get(EraEntity_.id), eraAlbum.get(EraEntity_.name));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Long countAllPlaylistsWhereSongExists(Long songId) {
        var hql = "select count(*) from SongPlaylistEntity ssp where ssp.song.id =:id";
        TypedQuery<Long> q = entityManager.createQuery(hql, Long.class).setParameter("id", songId);
        return q.getSingleResult();
    }

}
