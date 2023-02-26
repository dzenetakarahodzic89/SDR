package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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

    public List<PlaylistEntity> findPlaylistsByNameAndGenre(String name, Long genreId) {

        final CriteriaQuery<PlaylistEntity> criteriaQuery = builder.createQuery(PlaylistEntity.class);
        final Root<PlaylistEntity> root = criteriaQuery.from(PlaylistEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(builder.like(root.get("name"), name));
        }

        if (genreId != null) {
            Predicate[] subqueryPredicates = new Predicate[2];

            Join<PlaylistEntity, SongPlaylistEntity> songPlaylists = root.join(PlaylistEntity_.songPlaylists);
            Join<SongPlaylistEntity, SongEntity> songs = songPlaylists.join(SongPlaylistEntity_.song);

            Subquery<SongEntity> subquery = criteriaQuery.subquery(SongEntity.class);
            Root<SongEntity> song = subquery.from(SongEntity.class);
            subqueryPredicates[0] = builder.equal(builder.literal(genreId), song.get(SongEntity_.genre).get(GenreEntity_.id));
            subqueryPredicates[1] = song.in(songs);
            subquery.select(song).where(subqueryPredicates);
            predicates.add(builder.exists(subquery));
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);

        criteriaQuery.select(root).where(predicateArray);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }
}
