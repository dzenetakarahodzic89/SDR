package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;

/**
 * The interface Country Relation service.
 */
public interface CountryRelationService {

    PayloadResponse<CountryResponse> createCountriesRelation(final EntityRequest<CountryRelationCreateRequest> request) throws ApiException;

}
