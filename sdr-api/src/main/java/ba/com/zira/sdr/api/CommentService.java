package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;

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
 *
 */
public interface CommentService {

    
    public PagedPayloadResponse<Comment> find(final FilterRequest request) throws ApiException;

    
    PayloadResponse<Comment> create(EntityRequest<CommentCreateRequest> request) throws ApiException;
    PayloadResponse<Comment> delete(EntityRequest<Long> request) throws ApiException;

    public PayloadResponse<Comment> update(EntityRequest<CommentUpdateRequest> entityRequest) throws ApiException;
}

