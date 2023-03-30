package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.ListRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;

/**
 * The interface Country Relation service.
 */
public interface CountryRelationService {

    /**
     * Creates the countries relation.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> createCountriesRelation(ListRequest<CountryRelationCreateRequest> request) throws ApiException;

}
