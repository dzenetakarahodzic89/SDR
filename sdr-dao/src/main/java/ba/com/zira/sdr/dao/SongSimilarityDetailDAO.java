package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailResponse;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;

@Repository
public class SongSimilarityDetailDAO extends AbstractDAO<SongSimilarityDetailEntity, Long> {

    public List<SongSimilarityDetailResponse> getAllSongASimilarityDetail() {
        var hql = "select new ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailResponse "
                + "(sss.id, sssd.userCode, sssd.grade, sss.totalSimilarityScore) from SongSimilarityDetailEntity "
                + "sssd join SongSimilarityEntity sss on sssd.songSimilarity.id =sss.id";
        TypedQuery<SongSimilarityDetailResponse> query = entityManager.createQuery(hql, SongSimilarityDetailResponse.class);
        return query.getResultList();

    }
}