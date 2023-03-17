package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.notesheet.NoteSheet;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetCreateRequest;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetSongResponse;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.api.model.notesheet.SongInstrumentSheetResponse;

/**
 * * Methods used to manipulate {@link NoteSheet} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #find}</li>
 * <li>{@link #create}</li>
 *
 * </ul>
 *
 * @author zira
 */
public interface NoteSheetService {

    /**
     * Find.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<NoteSheet> find(final FilterRequest request) throws ApiException;

    /**
     * Creates the.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<NoteSheet> create(EntityRequest<NoteSheetCreateRequest> request) throws ApiException;

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
     * Update.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<NoteSheet> update(EntityRequest<NoteSheetUpdateRequest> entityRequest) throws ApiException;

    PayloadResponse<NoteSheetSongResponse> findNoteSheetForSong(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<NoteSheetSongResponse> findNoteSheetByInstrumentAndSong(EntityRequest<SongInstrumentSheetResponse> request)
            throws ApiException;
}
