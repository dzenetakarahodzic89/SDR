package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;

public interface CountryService {
    public PagedPayloadResponse<CountryResponse> get(final FilterRequest filterRequest) throws ApiException;

    public PayloadResponse<CountryResponse> create(final EntityRequest<CountryCreateRequest> request) throws ApiException;

    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException;

    public PayloadResponse<CountryResponse> update(final EntityRequest<CountryUpdateRequest> request) throws ApiException;
}
