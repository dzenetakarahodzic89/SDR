package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.GenreEntity_;
import ba.com.zira.sdr.dao.model.PlaylistEntity;
import ba.com.zira.sdr.dao.model.PlaylistEntity_;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongEntity_;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity_;

@Repository
public class PlaylistDAO extends AbstractDAO<PlaylistEntity, Long> {

    public List<PlaylistEntity> findPlaylistsByNameAndGenre(String name, Long genreId, String sortBy) {

        final CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
        final Root<PlaylistEntity> root = criteriaQuery.from(PlaylistEntity.class);
        Join<PlaylistEntity, SongPlaylistEntity> songPlaylists = root.join(PlaylistEntity_.songPlaylists);
        Join<SongPlaylistEntity, SongEntity> songs = songPlaylists.join(SongPlaylistEntity_.song);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(builder.like(root.get("name"), name));
        }

        if (genreId != null) {
            Predicate[] genreSubqueryPredicates = new Predicate[2];

            Subquery<SongEntity> genreSubquery = criteriaQuery.subquery(SongEntity.class);
            Root<SongEntity> song2 = genreSubquery.from(SongEntity.class);
            genreSubqueryPredicates[0] = builder.equal(builder.literal(genreId), song2.get(SongEntity_.genre).get(GenreEntity_.id));
            genreSubqueryPredicates[1] = song2.in(songs);
            genreSubquery.select(song2).where(genreSubqueryPredicates);
            predicates.add(builder.exists(genreSubquery));
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
}
