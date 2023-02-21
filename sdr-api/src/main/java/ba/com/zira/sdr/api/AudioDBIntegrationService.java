package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegration;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationUpdateRequest;

public interface AudioDBIntegrationService {
    public PagedPayloadResponse<AudioDBIntegration> find(final FilterRequest request) throws ApiException;
    PayloadResponse<AudioDBIntegration> create(EntityRequest<AudioDBIntegrationCreateRequest> request) throws ApiException;
    PayloadResponse<AudioDBIntegration> update(EntityRequest<AudioDBIntegrationUpdateRequest> request) throws ApiException;
    PayloadResponse<AudioDBIntegration> activate(EntityRequest<Long> request) throws ApiException;
    PayloadResponse<AudioDBIntegration> delete(EntityRequest<Long> request) throws ApiException;



}
