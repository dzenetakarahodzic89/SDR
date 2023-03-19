package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.GenreEntity_;
import ba.com.zira.sdr.dao.model.SongScoresEntity;
import ba.com.zira.sdr.dao.model.SongScoresEntity_;

@Repository
public class SongScoreDAO extends AbstractDAO<SongScoresEntity, Long> {
    public List<SongScoresEntity> findSongScoresByGenreIdArray(final List<Long> genreIds) {
        final CriteriaQuery<SongScoresEntity> cQuery = builder.createQuery(SongScoresEntity.class);
        final Root<SongScoresEntity> root = cQuery.from(SongScoresEntity.class);
        return entityManager.createQuery(cQuery.where(root.get(SongScoresEntity_.genre).get(GenreEntity_.id).in(genreIds))).getResultList();
    }
}
