package ba.com.zira.sdr.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.UserRecommendationIntegrationDetailEntity;

@Repository
public class UserRecommendationIntegrationDetailDAO extends AbstractDAO<UserRecommendationIntegrationDetailEntity, Long> {

    public void cleanTableForGA() {
        var hql = "delete from UserRecommendationIntegrationDetailEntity d where d.createdBy = :ga";
        Query q = entityManager.createQuery(hql).setParameter("ga", "GA");
        q.executeUpdate();
    }

}
