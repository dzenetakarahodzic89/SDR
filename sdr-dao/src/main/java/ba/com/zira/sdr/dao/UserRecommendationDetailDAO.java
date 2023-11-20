package ba.com.zira.sdr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.userrecommendationdetail.ResponseUserRecommendedDetailSongs;
import ba.com.zira.sdr.dao.model.UserRecommendationDetailEntity;

@Repository
public class UserRecommendationDetailDAO extends AbstractDAO<UserRecommendationDetailEntity, Long> {

    public Map<Long, String> userRecommendationDetailEntityByUserRecommendation(Long userRecommendationId) {

        var hql = "select new ba.com.zira.sdr.api.model lov.LoV(u.id,u.status) from UserRecommendationDetailEntity u where u.recommendationdetail.id =:id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", userRecommendationId);

        try {
            return q.getResultStream().collect(Collectors.toMap(LoV::getId, LoV::getName));
        } catch (Exception e) {
            return new HashMap<>();
        }

    }

    public List<ResponseUserRecommendedDetailSongs> findTopTenRatedSongs() {
        var hql = "select new ba.com.zira.sdr.api.model.userrecommendationdetail.ResponseUserRecommendedDetailSongs(s.name, ROUND(AVG(urd.userScore),2)) "
                + "from UserRecommendationDetailEntity as urd " + "join SongEntity as s " + "on urd.song.id = s.id " + "group by s.name";
        TypedQuery<ResponseUserRecommendedDetailSongs> query = entityManager.createQuery(hql, ResponseUserRecommendedDetailSongs.class);
        return query.setMaxResults(10).getResultList();

    }

    public void cleanTableForGA() {
        var hql = "delete from UserRecommendationDetailEntity d where d.createdBy = :ga";
        Query q = entityManager.createQuery(hql).setParameter("ga", "GA");
        q.executeUpdate();
    }

}
