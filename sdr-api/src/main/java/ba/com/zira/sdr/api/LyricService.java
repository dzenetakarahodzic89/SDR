package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricAlbumResponse;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;

/**
 * The interface Lyric service.
 */
public interface LyricService {

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<Lyric> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Lyric> create(EntityRequest<LyricCreateRequest> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Lyric> update(EntityRequest<LyricUpdateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Lyric> delete(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<LyricAlbumResponse> findAllLyricsForAlbum(EntityRequest<Long> request) throws ApiException;

}
