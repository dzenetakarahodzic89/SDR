package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;

/**
 * * Methods used to manipulate {@link Lyric} data. <br>
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
public interface LyricService {

    public PagedPayloadResponse<Lyric> find(final FilterRequest request) throws ApiException;

    PayloadResponse<Lyric> create(EntityRequest<LyricCreateRequest> request) throws ApiException;

    PayloadResponse<Lyric> update(EntityRequest<LyricUpdateRequest> request) throws ApiException;

    PayloadResponse<Lyric> activate(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<Lyric> delete(EntityRequest<Long> request) throws ApiException;

}
