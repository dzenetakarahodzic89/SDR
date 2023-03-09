package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationResponse;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;

@Repository
public class DeezerIntegrationDAO extends AbstractDAO<DeezerIntegrationEntity, String> {

    public List<DeezerIntegrationResponse> getForTableFill() {
        var hql = "select new ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationResponse(die.id,die.name,die.status,die.response,die.objectType,die.objectId) from DeezerIntegrationEntity die where die.status = :status";
        TypedQuery<DeezerIntegrationResponse> query = entityManager.createQuery(hql, DeezerIntegrationResponse.class)
                .setParameter("status", "Active").setMaxResults(100);
        return query.getResultList();
    }

    public void updateStatus(String status, String id) {
        var hql = "update DeezerIntegrationEntity set status = :status where id like :id";
        Query query = entityManager.createQuery(hql).setParameter("status", status).setParameter("id", id);
        query.executeUpdate();
    }

}