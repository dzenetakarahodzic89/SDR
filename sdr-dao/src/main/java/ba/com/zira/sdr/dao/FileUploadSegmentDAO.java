package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentResponse;
import ba.com.zira.sdr.dao.model.FileUploadSegmentEntity;

@Repository
public class FileUploadSegmentDAO extends AbstractDAO<FileUploadSegmentEntity, String> {
    private static final String MEDIAID = "mediaId";

    public void updateStatus(String status, Long mediaId) {
        var hql = "update FileUploadSegmentEntity set status = :status where media = :mediaId";
        Query query = entityManager.createQuery(hql).setParameter("status", status).setParameter(MEDIAID, mediaId);
        query.executeUpdate();
    }

    public Long countAllFieldsByMedia(Long mediaId) {
        var hql = "select count(f.id) from FileUploadSegmentEntity f where f.media = :mediaId";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter(MEDIAID, mediaId);
        return query.getSingleResult();
    }

    public String checkStatusOfMediaId(Long mediaId) {
        var hql = "select distinct sfus.status from FileUploadSegmentEntity sfus where sfus.media = :mediaId";
        TypedQuery<String> query = entityManager.createQuery(hql, String.class).setParameter(MEDIAID, mediaId);
        return query.getSingleResult();
    }

    public Long getReadyToMergeMediaId() {
        var hql = "select sfus.media from FileUploadSegmentEntity sfus where sfus.status = :status";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("status", "READY_TO_MERGE").setMaxResults(1);
        return query.getSingleResult();
    }

    public List<FileUploadSegmentResponse> getSegmentsByMediaId(Long mediaId) {
        var hql = "select new ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentResponse(sfus.id,sfus.fileName,sfus.fileSegment,sfus.fileSegmentTotal,sfus.fileSegmentContent,sfus.media) from FileUploadSegmentEntity sfus where sfus.media = :mediaId order by sfus.fileSegment";
        TypedQuery<FileUploadSegmentResponse> query = entityManager.createQuery(hql, FileUploadSegmentResponse.class).setParameter(MEDIAID,
                mediaId);
        return query.getResultList();
    }
}
