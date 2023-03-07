package ba.com.zira.sdr.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.sdr.api.model.album.AlbumArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumSearchRequest;
import ba.com.zira.sdr.api.model.album.AlbumSearchResponse;
import ba.com.zira.sdr.api.model.album.AlbumPersonResponse;
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

        Join<AlbumEntity, SongArtistEntity> songArtists = root.join(AlbumEntity_.songArtists);
        Join<SongArtistEntity, SongEntity> songs = songArtists.join(SongArtistEntity_.song);
        Join<SongEntity, GenreEntity> genres = songs.join(SongEntity_.genre);
        Join<AlbumEntity, EraEntity> eras = root.join(AlbumEntity_.era);
        Join<SongArtistEntity, ArtistEntity> artists = songArtists.join(SongArtistEntity_.artist);

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
            predicates.add(builder.like(root.get(AlbumEntity_.name), "%" + albumSearchRequest.getName() + "%"));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        var sumPlaytime = builder.sum(songs.get(SongEntity_.playtimeInSeconds));
        var sort = "play_time";
        if (request.getFilter().getSortingFilters() != null && !request.getFilter().getSortingFilters().isEmpty()) {
            sort = request.getFilter().getSortingFilters().get(0).getAttribute();
        }

        if (sort.equals("last_edit")) {
            criteriaQuery.orderBy(builder.asc(root.get(AlbumEntity_.modified)));

        }

        if (sort.equals("alphabetical")) {
            criteriaQuery.orderBy(builder.asc(root.get(AlbumEntity_.name)));

        }
        if (sort.equals("play_time")) {

            criteriaQuery.orderBy(builder.desc(sumPlaytime));
            criteriaQuery.groupBy(root.get(AlbumEntity_.id), root.get(AlbumEntity_.dateOfRelease), root.get(AlbumEntity_.name),
                    root.get(AlbumEntity_.information), root.get(AlbumEntity_.era), root.get(AlbumEntity_.modified));

        }
        criteriaQuery.multiselect(root.get(AlbumEntity_.id), root.get(AlbumEntity_.dateOfRelease), root.get(AlbumEntity_.name),
                root.get(AlbumEntity_.information), root.get(AlbumEntity_.era).get(EraEntity_.id), root.get(AlbumEntity_.modified),
                sumPlaytime).distinct(true);
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
                + "a.id = si.objectId and si.objectType like :album join SongArtistEntity sa on a.id=sa.album.id where si.id = null";
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
        countQuery.select(builder.count(root));
        if (criteriaQuery.getRestriction() != null) {
            countQuery.where(criteriaQuery.getRestriction());
        }
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        return count.intValue();
    }

}
