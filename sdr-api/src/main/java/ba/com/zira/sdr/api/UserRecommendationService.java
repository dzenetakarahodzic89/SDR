package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.userrecommendation.AverageScorePerCountry;
import ba.com.zira.sdr.api.model.userrecommendation.ScoreCompareRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.api.model.userrecommendation.UserScoreResponse;

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
 */
public interface UserRecommendationService {

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<UserRecommendationResponse> find(final FilterRequest request) throws ApiException;

    /**
     * Find by id payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<UserRecommendationResponse> findById(final EntityRequest<Long> request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<UserRecommendationResponse> create(EntityRequest<UserRecommendationCreateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException;

    /**
     * Find all users.
     *
     * Empty request
     *
     * @param req
     *            the req
     * @return the list payload response
     */

    ListPayloadResponse<UserScoreResponse> findAllUsers(EmptyRequest req);

    /**
     * Score compare.
     *
     * @param request
     *            the request
     * @return the list payload response
     */
    ListPayloadResponse<UserRecommendationResponse> scoreCompare(EntityRequest<ScoreCompareRequest> request);

    /**
     * Gets the average score per country.
     *
     * @param request
     *            the request
     * @return the average score per country
     */
    ListPayloadResponse<AverageScorePerCountry> getAverageScorePerCountry(EntityRequest<String> request);

    PayloadResponse<String> generateUserRecommendationsForGA(final EmptyRequest request);
}
