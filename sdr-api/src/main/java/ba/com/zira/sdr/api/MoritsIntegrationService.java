package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegration;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;

public interface MoritsIntegrationService {

    public PagedPayloadResponse<MoritsIntegration> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<MoritsIntegration> create(EntityRequest<MoritsIntegrationCreateRequest> request) throws ApiException;

    public PayloadResponse<MoritsIntegration> update(EntityRequest<MoritsIntegrationUpdateRequest> request) throws ApiException;

    public PayloadResponse<MoritsIntegration> delete(EntityRequest<Long> request) throws ApiException;
}
