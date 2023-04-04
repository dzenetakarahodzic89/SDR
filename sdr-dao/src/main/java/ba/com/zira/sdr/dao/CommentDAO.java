package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.dao.model.CommentEntity;

@Repository
public class CommentDAO extends AbstractDAO<CommentEntity, Long> {

    public List<Comment> fetchComments(final String objectType, final Long objectId) {
        var hql = "select new ba.com.zira.sdr.api.model.comment.Comment(c.id,c.content,c.created,c.createdBy,c.modified) from CommentEntity c where c.objectType=:objectType and c.objectId=:objectId";
        TypedQuery<Comment> query = entityManager.createQuery(hql, Comment.class).setParameter("objectType", objectType)
                .setParameter("objectId", objectId);
        return query.getResultList();

    }

}
