package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.SampleModel;
import ba.com.zira.sdr.api.model.SampleModelCreateRequest;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;

/**
 * * Methods used to manipulate {@link SampleModel} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #find}</li>
 * <li>{@link #create}</li>
 * <li>{@link #update}</li>
 * <li>{@link #activate}</li>
 * </ul>
 * 
 * @author zira
 *
 */
public interface SampleService {

    /**
     * Retrieve All {@link SampleModel}s from database.
     * 
     * @param request
     *            {@link FilterRequest} containing pagination and sorting information.
     * @return {@link PagedPayloadResponse} for {@link SampleModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    public PagedPayloadResponse<SampleModel> find(final FilterRequest request) throws ApiException;

    /**
     * Validate received sample data and create new sample.
     * 
     * @param request
     *            EntityRequest containing the {@link SampleModelCreateRequest} to
     *            create an engine.
     * @return created {@link SampleModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<SampleModel> create(EntityRequest<SampleModelCreateRequest> request) throws ApiException;

    /**
     * Validate received sample data and update sample.
     * 
     * @param request
     *            EntityRequest containing the {@link SampleModelUpdateRequest} to
     *            update.
     * @return created {@link SampleModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<SampleModel> update(EntityRequest<SampleModelUpdateRequest> request) throws ApiException;    
    
    /**
     * Activate engine
     * 
     * @param request
     *            {@link EntityRequest} containing the {@link Long} id of
     *            engine.
     * @return activated {@link SampleModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<SampleModel> activate(EntityRequest<Long> request) throws ApiException;
}
