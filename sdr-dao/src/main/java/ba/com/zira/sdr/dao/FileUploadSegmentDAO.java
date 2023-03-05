package ba.com.zira.sdr.dao;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.FileUploadSegmentEntity;

@Repository
public class FileUploadSegmentDAO extends AbstractDAO<FileUploadSegmentEntity, String> {
    public void updateStatus(String status, Long mediaId) {
        var hql = "update FileUploadSegmentEntity set status = :status where media.id = :mediaId";
        Query query = entityManager.createQuery(hql).setParameter("status", status).setParameter("mediaId", mediaId);
        query.executeUpdate();
    }

    public Long countAllFieldsByMedia(Long mediaId) {
        var hql = "select count(f.id) from FileUploadSegmentEntity f where f.media.id = : mediaId";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("mediaId", mediaId);
        return query.getSingleResult();
    }

    public String checkStatusOfMediaId(Long mediaId) {
        var hql = "select distinct sfus.status from FileUploadSegmentEntity sfus where sfus.media.id = :mediaId";
        TypedQuery<String> query = entityManager.createQuery(hql, String.class).setParameter("mediaId", mediaId);
        return query.getSingleResult();
    }

}
