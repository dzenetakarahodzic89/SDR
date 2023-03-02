package ba.com.zira.sdr.dao;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity;

@Repository
public class SongSimilarityDAO extends AbstractDAO<SongSimilarityEntity, Long> {

    public List<SongSimilarityResponse> getAllSongSimilarity() {
        var hql = "select new ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse "
                + "(ss.id, ss.songA.id, sA.name, aA.name, aA.dateOfRelease, ss.songB.id, sB.name, aB.name, aB.dateOfRelease) "
                + "from SongSimilarityEntity ss join SongEntity sA on ss.songA.id = sA.id "
                + "join SongEntity sB on ss.songB.id = sB.id join SongArtistEntity saA on sA.id=saA.song.id "
                + "join SongArtistEntity saB on sB.id=saB.song.id join AlbumEntity aA on saA.album.id=aA.id "
                + "join AlbumEntity aB on saB.album.id=aB.id order by random()";
        List<SongSimilarityResponse> resultList = entityManager.createQuery(hql, SongSimilarityResponse.class).getResultList();
        SongSimilarityResponse randomRecord = resultList.get(new Random().nextInt(resultList.size()));
        return Collections.singletonList(randomRecord);

    }

}
