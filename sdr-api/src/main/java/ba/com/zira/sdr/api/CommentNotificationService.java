package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentNotificationRequest;

/**
 * * Methods used to manipulate {@link Comment} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #find}</li>
 * <li>{@link #create}</li>
 *
 * </ul>
 *
 * @author zira
 */
public interface CommentNotificationService {

    /**
     * Find.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */

    void sendNotification(EntityRequest<CommentNotificationRequest> req) throws ApiException;

}
