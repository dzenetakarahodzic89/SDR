package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;

public interface DeezerIntegrationService {

    public PagedPayloadResponse<DeezerIntegration> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<DeezerIntegration> create(final EntityRequest<DeezerIntegrationCreateRequest> request) throws ApiException;

    public PayloadResponse<DeezerIntegration> update(final EntityRequest<DeezerIntegrationUpdateRequest> request) throws ApiException;    
    
    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException;
}
