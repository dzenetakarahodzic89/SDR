package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.playlist.PlaylistOfUserResponse;
import ba.com.zira.sdr.api.model.playlist.PlaylistResponse;
import ba.com.zira.sdr.dao.model.GenreEntity_;
import ba.com.zira.sdr.dao.model.PlaylistEntity;
import ba.com.zira.sdr.dao.model.PlaylistEntity_;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongEntity_;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity_;

@Repository
public class PlaylistDAO extends AbstractDAO<PlaylistEntity, Long> {

    public List<PlaylistEntity> findByUserCode(String userCode) {

        final CriteriaQuery<PlaylistEntity> criteriaQuery = builder.createQuery(PlaylistEntity.class);
        final Root<PlaylistEntity> root = criteriaQuery.from(PlaylistEntity.class);

        criteriaQuery.where(builder.equal(root.get(PlaylistEntity_.userCode), userCode));

        criteriaQuery.select(root).distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public List<PlaylistEntity> findPlaylistsByNameAndGenre(String name, Long genreId, String sortBy) {

        final CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
        final Root<PlaylistEntity> root = criteriaQuery.from(PlaylistEntity.class);
        Join<PlaylistEntity, SongPlaylistEntity> songPlaylists = root.join(PlaylistEntity_.songPlaylists);
        Join<SongPlaylistEntity, SongEntity> songs = songPlaylists.join(SongPlaylistEntity_.song);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (genreId != null) {
            Predicate[] subqueryPredicates = new Predicate[2];

            Subquery<SongEntity> subquery = criteriaQuery.subquery(SongEntity.class);
            Root<SongEntity> song = subquery.from(SongEntity.class);
            subqueryPredicates[0] = builder.equal(builder.literal(genreId), song.get(SongEntity_.genre).get(GenreEntity_.id));
            subqueryPredicates[1] = song.in(songs);
            subquery.select(song).where(subqueryPredicates);
            predicates.add(builder.exists(subquery));
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);
        Order order = null;

        if (sortBy != null) {
            switch (sortBy) {
            case "LastEdit":
                order = builder.desc(root.get("modified"));
                break;
            case "Alphabetical":
                order = builder.asc(root.get("name"));
                break;
            case "NoOfSongs":
                order = builder.desc(builder.count(songs));
                break;
            default:
                break;
            }

        }

        if (order != null) {
            criteriaQuery.multiselect(root, builder.count(songs)).where(predicateArray).groupBy(root.get("id")).orderBy(order);
        } else {
            criteriaQuery.multiselect(root, builder.count(songs)).where(predicateArray).groupBy(root.get("id"));
        }

        return entityManager.createQuery(criteriaQuery).getResultStream().map(r -> (PlaylistEntity) r.get(0)).collect(Collectors.toList());

    }

    public Map<Long, List<PlaylistOfUserResponse>> findPlaylistOfUser(String userCode) {
        var hql = "select new ba.com.zira.sdr.api.model.playlist.PlaylistOfUserResponse(p.id, s.id, s.name, s.playtimeInSeconds,s.spotifyId) "
                + "from PlaylistEntity p " + "join SongPlaylistEntity sp " + "on sp.playlist.id = p.id " + "join SongEntity s "
                + "on sp.song.id = s.id " + "where p.userCode = :userCode " + "order by p.id ";
        TypedQuery<PlaylistOfUserResponse> query = entityManager.createQuery(hql, PlaylistOfUserResponse.class);
        query.setParameter("userCode", userCode);

        return query.getResultList().stream().collect(Collectors.groupingBy(PlaylistOfUserResponse::getPlaylistId));

    }

    public List<PlaylistResponse> getPlaylistInfo(Long id) {

        var hql = "select new ba.com.zira.sdr.api.model.playlist.PlaylistResponse (s.id, s.name, al.name, ar.name || ' ' || ar.surname, s.playtime, s.spotifyId, "
                + "p.name, p.numberOfPlays, p.numberOfShares, p.outlineText) "
                + "from PlaylistEntity p join SongPlaylistEntity ssp on p.id=ssp.playlist.id join "
                + "SongEntity s on ssp.song.id =s.id join GenreEntity sg on s.genre.id =sg.id join SongArtistEntity ssa on s.id =ssa.song.id "
                + "join ArtistEntity ar on ssa.artist.id =ar.id join PersonArtistEntity spa on spa.artist.id =ar.id join AlbumEntity al on ssa.album.id =al.id join "
                + "PersonEntity sp on spa.person.id =sp.id join CountryEntity sc on sp.country.id =sc.id where p.id=:id "
                + "group by s.id, p.id, sc.id, ar.id, s.playtime, sg.name, al.name " + "order by s.name asc";
        TypedQuery<PlaylistResponse> query = entityManager.createQuery(hql, PlaylistResponse.class).setParameter("id", id);
        return query.getResultList();

    }

    public List<PlaylistResponse> getPlaylistInfoOverview(Long id) {
        var hql = "select new ba.com.zira.sdr.api.model.playlist.PlaylistResponse(p.name, s.id, s.name, s.spotifyId, sg.name, sc.name, ar.name || ' ' || ar.surname, s.playtime) "
                + "from PlaylistEntity p join SongPlaylistEntity ssp on p.id=ssp.playlist.id join "
                + "SongEntity s on ssp.song.id =s.id join GenreEntity sg on s.genre.id =sg.id join SongArtistEntity ssa on s.id =ssa.song.id "
                + "join ArtistEntity ar on ssa.artist.id =ar.id join PersonArtistEntity spa on spa.artist.id =ar.id join "
                + "PersonEntity sp on spa.person.id =sp.id join CountryEntity sc on sp.country.id =sc.id where p.id=:id "
                + "group by s.id, p.id, sc.id, ar.id, s.playtime, sg.name " + "order by s.name asc";
        TypedQuery<PlaylistResponse> query = entityManager.createQuery(hql, PlaylistResponse.class).setParameter("id", id);
        return query.getResultList();

    }

}
