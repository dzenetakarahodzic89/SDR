package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;

@Repository
public class UserRecommendationDAO extends AbstractDAO<UserRecommendationEntity, Long> {

    public List<UserRecommendationResponse> findAllUsers() {
        var hql = "select new ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse(r.id, r.userCode) from UserRecommendationEntity r ";
        TypedQuery<UserRecommendationResponse> query = entityManager.createQuery(hql, UserRecommendationResponse.class);
        return query.getResultList();
    }

    public List<UserRecommendationResponse> averageScoreByGenre(final List<Long> userIds) {

        var hql = "select new ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse(r.id, sg.name, r.userCode,  round(avg(surd.userScore), 2), "
                + "MAX(ss.name)) " + "from UserRecommendationEntity r "
                + "inner join UserRecommendationDetailEntity surd on r.id = surd.userRecommendation.id "
                + "inner join SongEntity ss on surd.song.id=ss.id " + "inner join GenreEntity sg on ss.genre.id = sg.id "
                + "where r.id in :userIds " + "group by r.id, sg.name order by sg.name, r.userCode asc";

        var q = entityManager.createQuery(hql, UserRecommendationResponse.class);
        q.setParameter("userIds", userIds);
        q.setMaxResults(100);

        var results = q.getResultList();

        return results;

    }

}
