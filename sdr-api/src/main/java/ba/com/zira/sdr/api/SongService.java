package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.api.model.song.SongCreateRequest;
import ba.com.zira.sdr.api.model.song.SongSearchRequest;
import ba.com.zira.sdr.api.model.song.SongSearchResponse;
import ba.com.zira.sdr.api.model.song.SongSingleResponse;
import ba.com.zira.sdr.api.model.song.SongUpdateRequest;

/**
 * * Methods used to manipulate {@link Song} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #create}</li>
 * <li>{@link #retrieveAll}</li>
 * <li>{@link #retrieveById}</li>
 * <li>{@link #update}</li>
 * <li>{@link #delete}</li>
 * </ul>
 *
 * @author Faris
 */
public interface SongService {

    /**
     * Validate received song data and create new song.
     *
     * @param request
     *            EntityRequest containing the {@link SongCreateRequest} to
     *            create an song.
     * @return created {@link Song}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<Song> create(EntityRequest<SongCreateRequest> request) throws ApiException;

    /**
     * Retrieve All {@link Song}s from database.
     *
     * @param request
     *            {@link FilterRequest} containing pagination and sorting
     *            information.
     * @return {@link PagedPayloadResponse} for {@link Song}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PagedPayloadResponse<Song> retrieveAll(final FilterRequest request) throws ApiException;

    /**
     * Retrieve song by given id
     *
     * @param request
     *            {@link EntityRequest} containing the {@link Long} id of song.
     * @return found {@link Song}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<SongSingleResponse> retrieveById(final EntityRequest<Long> request) throws ApiException;

    /**
     * Validate received song data and update song.
     *
     * @param request
     *            EntityRequest containing the {@link SongUpdateRequest} to
     *            update song.
     * @return created {@link Song}.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<Song> update(EntityRequest<SongUpdateRequest> request) throws ApiException;

    /**
     * Delete song by given id
     *
     * @param request
     *            {@link EntityRequest} containing the {@link Long} id of song.
     * @return {@link String} saying that the deletion was successful.
     * @throws ApiException
     *             If there was a problem during API invocation then.
     *             {@link ApiException} will be generated/returned with
     *             corresponding error message and {@link ResponseCode}.
     */
    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

    /**
     * Retrieve not in album.
     *
     * @param request
     *            the request
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<LoV> retrieveNotInAlbum(final EntityRequest<Long> request) throws ApiException;

    ListPayloadResponse<SongSearchResponse> find(final EntityRequest<SongSearchRequest> request) throws ApiException;

}
