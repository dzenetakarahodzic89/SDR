package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.sdr.api.model.album.AlbumArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumPersonResponse;
import ba.com.zira.sdr.api.model.album.AlbumSearchRequest;
import ba.com.zira.sdr.api.model.album.AlbumSearchResponse;
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

    static final Logger LOGGER = LoggerFactory.getLogger(AlbumDAO.class);

    public List<SongResponse> findSongsWithPlaytimeForAlbum(final Long albumId) {
        var hql = "select new ba.com.zira.sdr.api.model.song.SongResponse(s.id, s.name, s.playtime, g.name) from AlbumEntity a join SongArtistEntity sa on sa.album.id = a.id join SongEntity s on s.id = sa.song.id join GenreEntity g on g.id = s.genre.id where a.id = :albumId";
        TypedQuery<SongResponse> query = entityManager.createQuery(hql, SongResponse.class).setParameter("albumId", albumId);
        return query.getResultList();
    }

    public PagedData<AlbumSearchResponse> findAllAlbumsByNameGenreEraArtist(SearchRequest<AlbumSearchRequest> request) {
        final CriteriaQuery<AlbumSearchResponse> criteriaQuery = builder.createQuery(AlbumSearchResponse.class);
        final Root<AlbumEntity> root = criteriaQuery.from(AlbumEntity.class);

        Join<AlbumEntity, SongArtistEntity> songArtists = root.join(AlbumEntity_.songArtists, JoinType.LEFT);
        Join<SongArtistEntity, SongEntity> songs = songArtists.join(SongArtistEntity_.song, JoinType.LEFT);
        Join<SongEntity, GenreEntity> genres = songs.join(SongEntity_.genre, JoinType.LEFT);
        Join<AlbumEntity, EraEntity> eras = root.join(AlbumEntity_.era, JoinType.LEFT);
        Join<SongArtistEntity, ArtistEntity> artists = songArtists.join(SongArtistEntity_.artist, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        var albumSearchRequest = request.getEntity();
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
            predicates
                    .add(builder.like(builder.lower(root.get(AlbumEntity_.name)), "%" + albumSearchRequest.getName().toLowerCase() + "%"));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        var sumPlaytime = builder.sum(songs.get(SongEntity_.playtimeInSeconds));
        var sort = "";
        if (request.getFilter().getSortingFilters() != null && !request.getFilter().getSortingFilters().isEmpty()) {
            sort = request.getFilter().getSortingFilters().get(0).getAttribute();
        }

        Expression<?> modified = builder.selectCase().when(root.get(AlbumEntity_.modified).isNull(), root.get(AlbumEntity_.created))
                .otherwise(root.get(AlbumEntity_.modified));

        if (sort.equals("last_edit")) {
            criteriaQuery.orderBy(builder.desc(modified));

        }

        if (sort.equals("alphabetical")) {
            criteriaQuery.orderBy(builder.asc(root.get(AlbumEntity_.name)));

        }
        if (sort.equals("play_time")) {

            criteriaQuery.orderBy(builder.desc(builder.coalesce(sumPlaytime, 0)));

        }

        criteriaQuery.groupBy(root.get(AlbumEntity_.id), root.get(AlbumEntity_.dateOfRelease), root.get(AlbumEntity_.name),
                root.get(AlbumEntity_.information), root.get(AlbumEntity_.era), modified);
        criteriaQuery.multiselect(root.get(AlbumEntity_.id), root.get(AlbumEntity_.dateOfRelease), root.get(AlbumEntity_.name),
                root.get(AlbumEntity_.information), root.get(AlbumEntity_.era).get(EraEntity_.id), modified,
                builder.coalesce(sumPlaytime, 0)).distinct(true);
        return handlePaginationFilterForAlbum(request.getFilter(), criteriaQuery);

    }

    public List<AlbumArtistResponse> findAllAlbumsForArtist(Long artistId) {
        var hql = "select distinct new ba.com.zira.sdr.api.model.album.AlbumArtistResponse(al.id, al.name,al.dateOfRelease) "
                + " from ArtistEntity as a" + " join SongArtistEntity as s " + " on a.id = s.artist.id " + " join AlbumEntity as al "
                + " on al.id = s.album.id " + " where a.id = :artistId";

        TypedQuery<AlbumArtistResponse> query = entityManager.createQuery(hql, AlbumArtistResponse.class).setParameter("artistId",
                artistId);

        return query.getResultList();
    }

    public List<AlbumEntity> findByArtistId(Long artistId) {

        final CriteriaQuery<AlbumEntity> criteriaQuery = builder.createQuery(AlbumEntity.class);
        final Root<AlbumEntity> root = criteriaQuery.from(AlbumEntity.class);

        Join<AlbumEntity, SongArtistEntity> songArtists = root.join(AlbumEntity_.songArtists);
        Join<SongArtistEntity, ArtistEntity> artists = songArtists.join(SongArtistEntity_.artist);
        criteriaQuery.where(builder.equal(artists.get(ArtistEntity_.id), artistId));
        criteriaQuery.select(root).distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();
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
        var cases = "case when sa.artist.id is not null then concat('album:',a.name,' ','artist:',a2.fullName) else concat('album:', a.name) end";
        var subquery = "select si from SpotifyIntegrationEntity si where si.objectId=a.id and si.objectType like :album";
        var hql = "select distinct new ba.com.zira.sdr.api.model.lov.LoV(a.id,  concat('album:'," + cases
                + ")) from AlbumEntity a left join SongArtistEntity sa on a.id=sa.album.id left join ArtistEntity a2 on sa.artist.id=a2.id where not exists("
                + subquery + ") " + "and (a.spotifyId is null or length(a.spotifyId)<1)";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class).setParameter("album", "ALBUM").setMaxResults(responseLimit);

        return query.getResultList();
    }

    public PagedData<AlbumSearchResponse> handlePaginationFilterForAlbum(final Filter filter,
            final CriteriaQuery<AlbumSearchResponse> criteriaQuery) {
        TypedQuery<AlbumSearchResponse> query = entityManager.createQuery(criteriaQuery);
        var paginationFilter = filter.getPaginationFilter();
        PagedData<AlbumSearchResponse> pagedData = new PagedData<>();
        if (paginationFilter != null && paginationFilter.getPage() >= 0 && paginationFilter.getEntitiesPerPage() > 0) {
            int numberOfRecords = countAllForAlbum(criteriaQuery);
            pagedData.setPage(paginationFilter.getPage());
            pagedData.setRecordsPerPage(paginationFilter.getEntitiesPerPage());
            pagedData.setNumberOfPages((int) Math.ceil((float) numberOfRecords / paginationFilter.getEntitiesPerPage()));
            pagedData.setNumberOfRecords(numberOfRecords);
            query.setFirstResult((pagedData.getPage() - 1) * pagedData.getRecordsPerPage());
            query.setMaxResults(pagedData.getRecordsPerPage());
        } else if (paginationFilter != null && paginationFilter.isOffsetLimit()) {
            int numberOfRecords = countAllForAlbum(criteriaQuery);
            pagedData.setPage(paginationFilter.getPage());
            pagedData.setRecordsPerPage(paginationFilter.getEntitiesPerPage());
            pagedData.setNumberOfPages((int) Math.ceil((float) numberOfRecords / paginationFilter.getEntitiesPerPage()));

            pagedData.setNumberOfRecords(numberOfRecords);

            query.setFirstResult(paginationFilter.getPage());
            query.setMaxResults(paginationFilter.getEntitiesPerPage());
        }

        pagedData.setRecords(query.getResultList());
        return pagedData;
    }

    private void addJoinsForAlbum(final Root<AlbumEntity> root, final CriteriaQuery<?> criteriaQuery) {
        for (Root<?> tmpRoot : criteriaQuery.getRoots()) {
            for (Join<?, ?> join : tmpRoot.getJoins()) {
                Join<?, ?> copyOfJoin = root.join(join.getAttribute().getName(), join.getJoinType());
                addJoinsRecursively(join, copyOfJoin);
            }
        }
    }

    private void addJoinsRecursively(final Join<?, ?> parentJoin, final Join<?, ?> copyOfJoin) {
        for (Join<?, ?> join : parentJoin.getJoins()) {
            if (join.getJoins().isEmpty()) {
                copyOfJoin.join(join.getAttribute().getName(), join.getJoinType());
            } else {
                Join<?, ?> copyOfChild = copyOfJoin.join(join.getAttribute().getName(), join.getJoinType());
                addJoinsRecursively(join, copyOfChild);
            }
        }

    }

    public int countAllForAlbum(final CriteriaQuery<AlbumSearchResponse> criteriaQuery) {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<AlbumEntity> root = countQuery.from(AlbumEntity.class);
        addJoinsForAlbum(root, criteriaQuery);
        countQuery.select(builder.countDistinct(root));
        if (criteriaQuery.getRestriction() != null) {
            countQuery.where(criteriaQuery.getRestriction());
        }
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        return count.intValue();
    }

    public List<AlbumEntity> findAlbumsToFetchSongsFromSpotify(int responseLimit) {
        var hql = "select a from AlbumEntity a where a.spotifyId is not null and length(a.spotifyId)>0 and "
                + "(a.spotifyStatus!=:status or a.spotifyStatus is null) "
                + "and exists(select si from SpotifyIntegrationEntity si where si.objectId=a.id and si.objectType like :album)";
        return entityManager.createQuery(hql, AlbumEntity.class).setParameter("status", "Done").setParameter("album", "ALBUM")
                .setMaxResults(responseLimit).getResultList();
    }

    public AlbumEntity getAlbumBySpotifyId(String spotifyId) {
        var hql = "select a from AlbumEntity a where a.spotifyId=:spotifyId";
        return entityManager.createQuery(hql, AlbumEntity.class).setParameter("spotifyId", spotifyId).getSingleResult();
    }

    public List<AlbumEntity> getDuplicateAlbums() {
        var hql = "select a from AlbumEntity a where a.created > "
                + "(select min(a2.created) from AlbumEntity a2 where a2.spotifyId = a.spotifyId group by a2.spotifyId )"
                + " and a.spotifyId is not null and length(a.spotifyId)>0";
        return entityManager.createQuery(hql, AlbumEntity.class).getResultList();
    }

    public void deleteAlbums(List<Long> albumIds) {
        var hql = "delete from AlbumEntity a where a.id in (:albumIds)";
        Query q = entityManager.createQuery(hql).setParameter("albumIds", albumIds);
        q.executeUpdate();
    }

    public List<LoV> getAllAlbumsWithNameLike(String albumName) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from AlbumEntity s where s.name like :albumName";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class).setParameter("albumName", albumName);
        return query.getResultList();
    }

    public List<LoV> getAlbumLoVs() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from AlbumEntity s ";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public Map<Long, String> getAlbumNames(List<Long> ids) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from AlbumEntity s where s.id in (:ids)";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class).setParameter("ids", ids);
        return query.getResultStream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }
}
