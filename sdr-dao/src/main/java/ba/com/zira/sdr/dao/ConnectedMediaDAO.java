package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaPersonResponse;
import ba.com.zira.sdr.dao.model.ConnectedMediaEntity;

@Repository
public class ConnectedMediaDAO extends AbstractDAO<ConnectedMediaEntity, Long> {

    public List<ConnectedMediaPersonResponse> getConnectedMediaByPersonId(Long personId) {

        var hql = "select new ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaPersonResponse(scm.id, scm.created, scm.createdBy , scm.modified, scm.modifiedBy, scm.objectId, scm.objectType, scm.status) from ConnectedMediaEntity scm inner join ConnectedMediaDetailEntity scmd on scm.id = scmd.connectedMedia.id   "
                + " where scm.objectType = 'PERSON' and scm.objectId= :id";
        TypedQuery<ConnectedMediaPersonResponse> q = entityManager.createQuery(hql, ConnectedMediaPersonResponse.class).setParameter("id",
                personId);
        return q.getResultList();
    }
}
