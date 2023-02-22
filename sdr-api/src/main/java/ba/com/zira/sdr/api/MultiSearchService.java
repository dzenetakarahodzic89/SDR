package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse;
import ba.com.zira.sdr.api.model.wiki.WikiResponse;

/**
 * The interface Multi search service.
 */
public interface MultiSearchService {
    /**
     * Find list payload response.
     *
     * @param request
     *         the request
     * @return the list payload response
     * @throws ApiException
     *         the api exception
     */
    ListPayloadResponse<MultiSearchResponse> find(EntityRequest<String> request) throws ApiException;

    /**
     * Find wiki list payload response.
     *
     * @param req
     *         the req
     * @return the list payload response
     * @throws ApiException
     *         the api exception
     */
    ListPayloadResponse<WikiResponse> findWiki(EmptyRequest req) throws ApiException;

    /**
     * Gets all.
     *
     * @param req
     *         the req
     * @return the all
     * @throws ApiException
     *         the api exception
     */
    ListPayloadResponse<MultiSearchResponse> getAll(EmptyRequest req) throws ApiException;

    /**
     * Gets random.
     *
     * @param req
     *         the req
     * @return the random
     * @throws ApiException
     *         the api exception
     */
    ListPayloadResponse<MultiSearchResponse> getRandom(EmptyRequest req) throws ApiException;

}
