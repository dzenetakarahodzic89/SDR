package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;

public interface SpotifyIntegrationService {

    public PagedPayloadResponse<SpotifyIntegrationResponse> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<SpotifyIntegrationResponse> create(final EntityRequest<SpotifyIntegrationCreateRequest> request)
            throws ApiException;

    public PayloadResponse<SpotifyIntegrationResponse> update(final EntityRequest<SpotifyIntegrationUpdateRequest> request)
            throws ApiException;

    public PayloadResponse<SpotifyIntegrationResponse> delete(final EntityRequest<Long> request) throws ApiException;
}
