package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistUpdateRequest;

public interface SongArtistService {

    public PagedPayloadResponse<SongArtistResponse> get(final FilterRequest filterRequest) throws ApiException;

    public PayloadResponse<SongArtistResponse> create(final EntityRequest<SongArtistCreateRequest> entityRequest) throws ApiException;

    public PayloadResponse<SongArtistResponse> update(final EntityRequest<SongArtistUpdateRequest> entityRequest) throws ApiException;

    public PayloadResponse<SongArtistResponse> delete(final EntityRequest<Long> entityRequest);

}
