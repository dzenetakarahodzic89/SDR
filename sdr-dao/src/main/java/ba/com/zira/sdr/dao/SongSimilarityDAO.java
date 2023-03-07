package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Random;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity;

@Repository
public class SongSimilarityDAO extends AbstractDAO<SongSimilarityEntity, Long> {

    public List<SongSimilarityResponse> getAllSongSimilarity() {
        var hql = "select new ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse "
                + "(ss.id, MAX(ss.songA.id), MAX(sA.name), MAX(aA.name), MAX(aA.dateOfRelease), MAX(ss.songB.id), MAX(sB.name), MAX(aB.name), MAX(aB.dateOfRelease)) "
                + "from SongSimilarityEntity ss join SongEntity sA on ss.songA.id = sA.id "
                + "join SongEntity sB on ss.songB.id = sB.id join SongArtistEntity saA on sA.id=saA.song.id "
                + "join SongArtistEntity saB on sB.id=saB.song.id join AlbumEntity aA on saA.album.id=aA.id "
                + "join AlbumEntity aB on saB.album.id=aB.id group by ss.id";
        TypedQuery<SongSimilarityResponse> query = entityManager.createQuery(hql, SongSimilarityResponse.class);
        return query.getResultList();

    }

    public SongSimilarityResponse getRandomSongSimilarity() {
        var hql = "select new ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse "
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
