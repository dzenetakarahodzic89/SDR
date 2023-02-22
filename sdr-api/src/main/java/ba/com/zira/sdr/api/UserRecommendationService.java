package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;

/**
 * * Methods used to manipulate {@link UserRecommendationResponse} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #find}</li>
 * <li>{@link #findById}</li>
 * <li>{@link #create}</li>
 * <li>{@link #delete}</li>
 * </ul>
 *
 * @author zira
 *
 */

public interface UserRecommendationService {

    public PagedPayloadResponse<UserRecommendationResponse> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<UserRecommendationResponse> findById(final EntityRequest<Long> request) throws ApiException;

    public PayloadResponse<UserRecommendationResponse> create(EntityRequest<UserRecommendationCreateRequest> request) throws ApiException;

    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException;
}
