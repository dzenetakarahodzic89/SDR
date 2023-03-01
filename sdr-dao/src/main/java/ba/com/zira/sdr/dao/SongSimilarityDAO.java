package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity;

@Repository
public class SongSimilarityDAO extends AbstractDAO<SongSimilarityEntity, Long> {

    public List<SongSimilarityResponse> getAllSongASimilarity() {
        var hql = "select new ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse "
                + "(ss.songA.id, s.name, a.name, a.dateOfRelease) from SongSimilarityEntity ss join SongEntity s "
                + "on ss.songA.id=s.id join SongArtistEntity sa on s.id=sa.song.id join AlbumEntity a on sa.album.id=a.id";
        TypedQuery<SongSimilarityResponse> query = entityManager.createQuery(hql, SongSimilarityResponse.class);
        query.setMaxResults(1);
        return query.getResultList();
    }

    public List<SongSimilarityResponse> getAllSongBSimilarity() {
        var hql = "select new ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse "
                + "(ss.songB.id, s.name, a.name, a.dateOfRelease) from SongSimilarityEntity ss join SongEntity s "
                + "on ss.songB.id=s.id join SongArtistEntity sa on s.id=sa.song.id join AlbumEntity a on sa.album.id=a.id";
        TypedQuery<SongSimilarityResponse> query = entityManager.createQuery(hql, SongSimilarityResponse.class);
        query.setMaxResults(1);
        return query.getResultList();
    }
}
