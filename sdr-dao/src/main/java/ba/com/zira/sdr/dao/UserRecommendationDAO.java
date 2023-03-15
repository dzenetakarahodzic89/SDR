package ba.com.zira.sdr.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;

@Repository
public class UserRecommendationDAO extends AbstractDAO<UserRecommendationEntity, Long> {

    public void cleanTableForGA() {
        var hql = "delete from UserRecommendationEntity d where d.createdBy = :ga";
        Query q = entityManager.createQuery(hql).setParameter("ga", "GA");
        q.executeUpdate();
    }

}
