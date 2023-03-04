package ba.com.zira.sdr.dao;

import javax.persistence.Query;

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
}
