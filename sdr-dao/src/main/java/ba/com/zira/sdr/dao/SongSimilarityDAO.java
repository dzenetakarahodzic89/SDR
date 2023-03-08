package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Random;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.songsimilarity.SongSimilarityResponse;
import ba.com.zira.sdr.dao.model.SongEntity_;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity_;

@Repository
public class SongSimilarityDAO extends AbstractDAO<SongSimilarityEntity, Long> {

    public Boolean existsSimilarity(Long songA, Long songB) {
        final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        final Root<SongSimilarityEntity> songSimilarityRoot = criteriaQuery.from(SongSimilarityEntity.class);

        criteriaQuery.select(builder.count(songSimilarityRoot));

        Predicate firstPermutationPredicate = builder.and(
                builder.equal(songSimilarityRoot.get(SongSimilarityEntity_.songA).get(SongEntity_.id), songA),
                builder.equal(songSimilarityRoot.get(SongSimilarityEntity_.songB).get(SongEntity_.id), songB));

        Predicate secondPermutationPredicate = builder.and(
                builder.equal(songSimilarityRoot.get(SongSimilarityEntity_.songA).get(SongEntity_.id), songB),
                builder.equal(songSimilarityRoot.get(SongSimilarityEntity_.songB).get(SongEntity_.id), songA));

        criteriaQuery.where(builder.or(firstPermutationPredicate, secondPermutationPredicate));
        return entityManager.createQuery(criteriaQuery).getSingleResult() != 0;
    }

    public List<SongSimilarityResponse> getAllSongSimilarity() {
        var hql = "select new ba.com.zira.sdr.api.model.songsimilarity.SongSimilarityResponse "
                + "(ss.id, MAX(ss.songA.id), MAX(sA.name), MAX(aA.name), MAX(aA.dateOfRelease), MAX(ss.songB.id), MAX(sB.name), MAX(aB.name), MAX(aB.dateOfRelease)) "
                + "from SongSimilarityEntity ss join SongEntity sA on ss.songA.id = sA.id "
                + "join SongEntity sB on ss.songB.id = sB.id join SongArtistEntity saA on sA.id=saA.song.id "
                + "join SongArtistEntity saB on sB.id=saB.song.id join AlbumEntity aA on saA.album.id=aA.id "
                + "join AlbumEntity aB on saB.album.id=aB.id group by ss.id";
        TypedQuery<SongSimilarityResponse> query = entityManager.createQuery(hql, SongSimilarityResponse.class);
        return query.getResultList();

    }

    public SongSimilarityResponse getRandomSongSimilarity() {
        var hql = "select new ba.com.zira.sdr.api.model.songsimilarity.SongSimilarityResponse "
                + "(ss.id, MAX(ss.songA.id), MAX(sA.name), MAX(aA.name), MAX(aA.dateOfRelease), MAX(ss.songB.id), MAX(sB.name), MAX(aB.name), MAX(aB.dateOfRelease)) "
                + "from SongSimilarityEntity ss join SongEntity sA on ss.songA.id = sA.id "
                + "join SongEntity sB on ss.songB.id = sB.id join SongArtistEntity saA on sA.id=saA.song.id "
                + "join SongArtistEntity saB on sB.id=saB.song.id join AlbumEntity aA on saA.album.id=aA.id "
                + "join AlbumEntity aB on saB.album.id=aB.id group by ss.id";
        var countHql = "select count (s) from SongSimilarityEntity s";
        TypedQuery<Long> countQuery = entityManager.createQuery(countHql, Long.class);
        int randomIndex = new Random().nextInt(countQuery.getSingleResult().intValue());
        TypedQuery<SongSimilarityResponse> query = entityManager.createQuery(hql, SongSimilarityResponse.class);
        return query.setFirstResult(randomIndex).setMaxResults(1).getSingleResult();

    }

}
