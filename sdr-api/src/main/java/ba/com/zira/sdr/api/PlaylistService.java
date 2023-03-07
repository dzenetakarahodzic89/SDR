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
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistSearchRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;

/**
 * The interface Playlist service.
 */
public interface PlaylistService {

    /**
     * Retrieve All {@link SampleModel}s from database.
     *
     * @param request
     *            {@link FilterRequest} containing pagination and sorting
     *            information.
     * @return {@link PagedPayloadResponse} for {@link SampleModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PagedPayloadResponse<Playlist> find(final FilterRequest request) throws ApiException;

    /**
     * Validate received sample data and create new sample.
     *
     * @param request
     *            EntityRequest containing the {@link SampleModelCreateRequest}
     *            to create an engine.
     * @return created {@link SampleModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<Playlist> create(EntityRequest<PlaylistCreateRequest> request) throws ApiException;

    /**
     * Validate received sample data and update sample.
     *
     * @param request
     *            EntityRequest containing the {@link SampleModelUpdateRequest}
     *            to update.
     * @return created {@link SampleModel}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<Playlist> update(EntityRequest<PlaylistUpdateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

    /**
     * Search by name song genre.
     *
     * @param request
     *            the request
     * @return the paged payload response
     */
    PagedPayloadResponse<Playlist> searchByNameSongGenre(EntityRequest<PlaylistSearchRequest> request);
}
