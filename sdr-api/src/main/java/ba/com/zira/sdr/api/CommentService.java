package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentNotificationRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.api.model.comment.CommentsFetchRequest;

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
public interface CommentService {

    /**
     * Find.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<Comment> find(final FilterRequest request) throws ApiException;

    /**
     * Creates the.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Comment> create(EntityRequest<CommentCreateRequest> request) throws ApiException;

    /**
     * Delete.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

    /**
     * Update.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Comment> update(EntityRequest<CommentUpdateRequest> entityRequest) throws ApiException;

    ListPayloadResponse<Comment> fetchComments(final EntityRequest<CommentsFetchRequest> request) throws ApiException;

    public CommentNotificationRequest createCommentNotificationRequest(EntityRequest<CommentCreateRequest> req);
}
