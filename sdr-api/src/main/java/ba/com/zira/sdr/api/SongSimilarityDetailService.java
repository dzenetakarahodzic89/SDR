package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;

/**
 * The Interface SongSimilarityDetailService.
 */
public interface SongSimilarityDetailService {

    /**
     * Find.
     *
     * @param request
     *         the request
     * @return the paged payload response
     * @throws ApiException
     *         the api exception
     */
    PagedPayloadResponse<SongSimilarityDetail> find(final FilterRequest request) throws ApiException;

    /**
     * Creates the.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongSimilarityDetail> create(EntityRequest<SongSimilarityDetailCreateRequest> request) throws ApiException;

    /**
     * Delete.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongSimilarityDetail> delete(EntityRequest<Long> request) throws ApiException;

    /**
     * Update.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongSimilarityDetail> update(EntityRequest<SongSimilarityDetailUpdateRequest> request) throws ApiException;

}
