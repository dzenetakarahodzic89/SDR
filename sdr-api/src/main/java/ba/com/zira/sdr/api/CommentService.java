package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.comment.CommentModel;
import ba.com.zira.sdr.api.model.comment.CommentModelCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentModelUpdateRequest;

/**
 * * Methods used to manipulate {@link CommentModel} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #find}</li>
 * <li>{@link #create}</li>
 * <li>{@link #activate}</li>
 * </ul>
 * 
 * @author zira
 *
 */
public interface CommentService {

    /**
     * Retrieve All {@link CommentModel}s from database.
     * 
     * @param request
     *            {@link FilterRequest} containing pagination and sorting information.
     * @return {@link PagedPayloadResponse} for {@link CommentModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    public PagedPayloadResponse<CommentModel> find(final FilterRequest request) throws ApiException;

    /**
     * Validate received comment data and create new comment.
     * 
     * @param request
     *            EntityRequest containing the {@link CommentModelCreateRequest} to
     *            create an engine.
     * @return created {@link CommentModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<CommentModel> create(EntityRequest<CommentModelCreateRequest> request) throws ApiException;


    /**
     * Activate engine
     * 
     * @param request“
     *            {@link EntityRequest} containing the {@link Long} id of
     *            engine.
     * @return activated {@link CommentModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<CommentModel> activate(EntityRequest<Long> request) throws ApiException;
    
    PayloadResponse<CommentModel> delete(EntityRequest<Long> request) throws ApiException;

    public PayloadResponse<CommentModel> update(EntityRequest<CommentModelUpdateRequest> entityRequest) throws ApiException;
}

