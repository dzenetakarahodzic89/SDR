package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistDeleteRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;

public interface ArtistService {

    ListPayloadResponse<ArtistResponse> getAll(EmptyRequest req) throws ApiException;

    PayloadResponse<ArtistResponse> create(EntityRequest<ArtistCreateRequest> request) throws ApiException;

    PayloadResponse<ArtistResponse> delete(EntityRequest<ArtistDeleteRequest> request) throws ApiException;
}
