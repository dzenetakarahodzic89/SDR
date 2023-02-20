package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;

public interface PersonService {

	public PagedPayloadResponse<PersonResponse> find(final FilterRequest request) throws ApiException;

	PayloadResponse<PersonResponse> create(final EntityRequest<PersonCreateRequest> entityRequest) throws ApiException;

	PayloadResponse<PersonResponse> update(final EntityRequest<PersonUpdateRequest> entityRequest) throws ApiException;

	PayloadResponse<PersonResponse> delete(final EntityRequest<Long> entityRequest);

}
