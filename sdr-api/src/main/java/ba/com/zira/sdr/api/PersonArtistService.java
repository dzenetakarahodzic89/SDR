package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.api.model.personartist.PersonArtistResponse;

public interface PersonArtistService {

    public PagedPayloadResponse<PersonArtistResponse> get(final FilterRequest filterRequest) throws ApiException;

    public PayloadResponse<PersonArtistResponse> create(final EntityRequest<PersonArtistCreateRequest> entityRequest) throws ApiException;

    public PayloadResponse<String> delete(final EntityRequest<Long> entityRequest);

}
