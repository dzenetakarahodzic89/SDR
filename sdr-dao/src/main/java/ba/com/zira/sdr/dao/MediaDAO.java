package ba.com.zira.sdr.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.MediaEntity;

@Repository
public class MediaDAO extends AbstractDAO<MediaEntity, Long> {

    public MediaEntity findByTypeAndId(final String objectType, final Long objectId) {
        var hql = "select m from MediaEntity m where m.objectType = :objectType and m.objectId = :objectId";
        TypedQuery<MediaEntity> q = entityManager.createQuery(hql, MediaEntity.class).setParameter("objectId", objectId)
                .setParameter("objectType", objectType);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}