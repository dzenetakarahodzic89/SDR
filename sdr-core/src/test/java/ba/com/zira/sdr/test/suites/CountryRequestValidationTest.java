package ba.com.zira.sdr.test.suites;

import static org.testng.Assert.assertEquals;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.core.validation.CountryRequestValidation;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class CountryRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private CountryDAO countryDAO;
    private CountryRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.countryDAO = Mockito.mock(CountryDAO.class);
        this.validation = new CountryRequestValidation(countryDAO);
    }

    /**
     * Update
     */

    @Test
    public void validateUpdateRequestCountryNotFound() {
        Mockito.when(countryDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<CountryUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new CountryUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateCountryRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Country with id: 1 does not exist!");
        Mockito.verify(countryDAO).existsByPK(1L);
    }

}
