package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.label.LabelCreateRequest;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;

/**
 * * Methods used to manipulate {@link LabelResponse} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #find}</li>
 * <li>{@link #findById}</li>
 * <li>{@link #create}</li>
 * <li>{@link #update}</li>
 * <li>{@link #delete}</li>
 * </ul>
 *
 * @author zira
 *
 */

public interface LabelService {

    public PagedPayloadResponse<LabelResponse> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<LabelResponse> findById(final EntityRequest<Long> request) throws ApiException;

    /*
     * Validate received label data and create new label.
     *
     * @param request EntityRequest containing the {@link LabelCreateRequest} to
     * create an engine.
     *
     * @return created {@link Label}.
     *
     * @throws ApiException If there was a problem during API invocation then.
     * {@link ApiException} will be generated/returned with corresponding error
     * message and {@link ResponseCode}.
     */
    public PayloadResponse<LabelResponse> create(EntityRequest<LabelCreateRequest> request) throws ApiException;

    /**
     * Validate received label data and update label.
     *
     * @param request
     *            EntityRequest containing the {@link LabelUpdateRequest} to
     *            update.
     * @return created {@link LabelResponse}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    public PayloadResponse<LabelResponse> update(EntityRequest<LabelUpdateRequest> request) throws ApiException;

    /**
     * Change label status
     *
     * @param request
     *            {@link EntityRequest} containing the {@link Long} id of
     *            engine.
     * @return activated {@link LabelResponse}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     *
     */

    public PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;
}
