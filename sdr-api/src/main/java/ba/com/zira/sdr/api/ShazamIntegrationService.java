package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationResponse;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationUpdateRequest;

public interface ShazamIntegrationService {

	public PagedPayloadResponse<ShazamIntegrationResponse> find(final FilterRequest request) throws ApiException;

	public PayloadResponse<ShazamIntegrationResponse> create(
			final EntityRequest<ShazamIntegrationCreateRequest> request) throws ApiException;

	public PayloadResponse<ShazamIntegrationResponse> update(
			final EntityRequest<ShazamIntegrationUpdateRequest> request) throws ApiException;

	public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException;
}