package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.generateplaylist.GeneratedPlaylistSongDbResponse;
import ba.com.zira.sdr.api.model.generateplaylist.PlaylistGenerateRequest;
import ba.com.zira.sdr.api.model.genre.SongGenreEraLink;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.SongSearchResponse;
import ba.com.zira.sdr.api.model.song.SongSingleResponse;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.AlbumEntity_;
import ba.com.zira.sdr.dao.model.EraEntity;
import ba.com.zira.sdr.dao.model.EraEntity_;
import ba.com.zira.sdr.dao.model.GeneratedSongViewEntity;
import ba.com.zira.sdr.dao.model.GeneratedSongViewEntity_;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.GenreEntity_;
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

    public List<GeneratedPlaylistSongDbResponse> generatePlaylist(final PlaylistGenerateRequest request) {
        final CriteriaQuery<GeneratedPlaylistSongDbResponse> criteriaQuery = builder.createQuery(GeneratedPlaylistSongDbResponse.class);
        final Root<GeneratedSongViewEntity> generatedSongsRoot = criteriaQuery.from(GeneratedSongViewEntity.class);

        ArrayList<Predicate> predicates = new ArrayList<>();

        if (Boolean.FALSE.equals(request.getIncludeCovers())) {
            predicates.add(builder.isNull(generatedSongsRoot.get(GeneratedSongViewEntity_.coverId)));
        }

        if (Boolean.FALSE.equals(request.getIncludeRemixes())) {
            predicates.add(builder.isNull(generatedSongsRoot.get(GeneratedSongViewEntity_.remixId)));
        }

        if (request.getGenreId() != null) {
            predicates.add(builder.equal(generatedSongsRoot.get(GeneratedSongViewEntity_.genreId), request.getGenreId()));
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);

        return entityManager.createQuery(criteriaQuery
                .multiselect(generatedSongsRoot.get(GeneratedSongViewEntity_.song),
                        generatedSongsRoot.get(GeneratedSongViewEntity_.artists), generatedSongsRoot.get(GeneratedSongViewEntity_.albums),
                        generatedSongsRoot.get(GeneratedSongViewEntity_.genre), generatedSongsRoot.get(GeneratedSongViewEntity_.countries))
                .where(predicateArray)).setMaxResults(request.getAmountOfSongs().intValue()).getResultList();
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

    public List<LoV> getSongsNotInAlbum(Long albumId) {
        var hql = "select distinct new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from SongEntity s left join SongArtistEntity sa on s.id=sa.song.id where sa.album.id!=:albumId or sa.id=null";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class).setParameter("albumId", albumId);
        return query.getResultList();
    }

    public List<SongSearchResponse> find(String songName, String sortBy, Long remixId, Long coverId, List<Long> artistIds,
            List<Long> albumIds, List<Long> genreIds, int page, int pageSize) {
        var query = "select new ba.com.zira.sdr.api.model.song.SongSearchResponse(ss.id, ss.name, ss.outlineText, ss.modified) from SongEntity ss left join SongArtistEntity ssa on ss.id = ssa.song.id "
                + "left join AlbumEntity sa on sa.id = ssa.album.id join GenreEntity sg on ss.genre.id = sg.id "
                + "where (ss.name like :songName or :songName is null or :songName = '') and (:remixId is null or ss.remix.id is not null) and (:coverId is null or ss.cover.id is not null) and (coalesce(:artistIds, null) is null or ssa.artist.id in :artistIds) and (coalesce(:albumIds, null) is null or ssa.album.id in :albumIds) and (coalesce(:genreIds, null) is null or ss.genre.id in :genreIds)";
        if ("last_date".equals(sortBy)) {
            query += " order by ss.modified desc";
        }

        else {
            query += " order by ss.name";
        }

        var q = entityManager.createQuery(query, SongSearchResponse.class);

        if (remixId != null) {
            q.setParameter("remixId", true);
            if (remixId == 0) {
                q.setParameter("remixId", null);
            }
        }

        else {
            q.setParameter("remixId", null);
        }

        if (coverId != null) {
            q.setParameter("coverId", true);
            if (coverId == 0) {
                q.setParameter("coverId", null);
            }
        } else {
            q.setParameter("coverId", null);
        }

        if (songName != null && !songName.isEmpty()) {
            q.setParameter("songName", "%" + songName + "%");
        } else {
            q.setParameter("songName", null);
        }

        if (artistIds != null) {
            q.setParameter("artistIds", artistIds);
        } else {
            q.setParameter("artistIds", null);
        }

        if (albumIds != null) {
            q.setParameter("albumIds", albumIds);
        } else {
            q.setParameter("albumIds", null);
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
        List<SongSearchResponse> songs = q.getResultList();

        return songs;
    }

    public List<LoV> findSongsToFetchFromSpotify(int responseLimit) {
        var cases = "case when sa.artist.surname is null then concat('track:',s.name,' ','artist:',sa.artist.name) else"
                + " concat('track:',s.name,' ','artist:',sa.artist.name,' ',sa.artist.surname) end";
        var hql = "select distinct new ba.com.zira.sdr.api.model.lov.LoV(s.id," + cases + ") from SongEntity s left join"
                + " SpotifyIntegrationEntity si on s.id = si.objectId and si.objectType like 'SONG' join SongArtistEntity sa on s.id=sa.song.id where si.id = null";
        return entityManager.createQuery(hql, LoV.class).setMaxResults(responseLimit).getResultList();

    }

}
