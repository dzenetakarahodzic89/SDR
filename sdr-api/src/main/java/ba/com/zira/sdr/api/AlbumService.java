package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumSearchRequest;
import ba.com.zira.sdr.api.model.album.AlbumSearchResponse;
import ba.com.zira.sdr.api.model.album.AlbumSongResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.api.model.album.AlbumsByDecadeResponse;
import ba.com.zira.sdr.api.model.album.SongOfAlbumUpdateRequest;
import ba.com.zira.sdr.api.model.song.Song;


/**
 * The Interface AlbumService.
 */
/**
 * @author User
 *
 */
/**
 * @author User
 *
 */
/**
 * @author User
 *
 */
public interface AlbumService {

    /**
     * Find.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<AlbumResponse> find(final FilterRequest request) throws ApiException;

    /**
     * Search.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */

    PagedPayloadResponse<AlbumSearchResponse> search(EntityRequest<AlbumSearchRequest> request) throws ApiException;

    /**
     * Creates the.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<AlbumResponse> create(EntityRequest<AlbumCreateRequest> request) throws ApiException;

    /**
     * Update.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<AlbumResponse> update(EntityRequest<AlbumUpdateRequest> request) throws ApiException;

    /**
     * Delete.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

    /**
     * Find all songs for album.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<AlbumSongResponse> findAllSongsForAlbum(EntityRequest<Long> request) throws ApiException;

    ListPayloadResponse<AlbumsByDecadeResponse> findAllAlbumsForArtist(EntityRequest<Long> request) throws ApiException;


    /**
     * @param request
     * @return
     * @throws ApiException
     */
    PayloadResponse<AlbumResponse> getById(EntityRequest<Long> request) throws ApiException;

    /**
     * Adds the song and label to album.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Song> addSongToAlbum(EntityRequest<SongOfAlbumUpdateRequest> request) throws ApiException;


}
