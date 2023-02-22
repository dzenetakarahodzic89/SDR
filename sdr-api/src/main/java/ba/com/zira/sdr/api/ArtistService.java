package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;

public interface ArtistService {

    public PagedPayloadResponse<ArtistResponse> find(final FilterRequest request) throws ApiException;

    // ListPayloadResponse<ArtistResponse> getAll(EmptyRequest req) throws
    // ApiException;

    PayloadResponse<ArtistResponse> create(EntityRequest<ArtistCreateRequest> request) throws ApiException;

    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<ArtistResponse> update(EntityRequest<ArtistUpdateRequest> request) throws ApiException;

    // ListPayloadResponse<ArtistResponse> getArtistsByEra(EntityRequest<Long>
    // request) throws ApiException;

}
