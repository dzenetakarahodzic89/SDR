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
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;
import ba.com.zira.sdr.core.validation.MoritsIntegrationRequestValidation;
import ba.com.zira.sdr.dao.MoritsIntegrationDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class MoritsIntegrationRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private MoritsIntegrationDAO moritsIntegrationDAO;
    private MoritsIntegrationRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.moritsIntegrationDAO = Mockito.mock(MoritsIntegrationDAO.class);
        this.validation = new MoritsIntegrationRequestValidation(moritsIntegrationDAO);
    }

    @Test
    public void validateUpdateRequestCountryNotFound() {
        Mockito.when(moritsIntegrationDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<MoritsIntegrationUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var response = new MoritsIntegrationUpdateRequest();
        response.setId(1L);
        request.setEntity(response);
        ValidationResponse validationResponse = validation.validateUpdateMoritsIntegrationRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Morits lyric integration with id: 1 does not exist!");
        Mockito.verify(moritsIntegrationDAO).existsByPK(1L);
    }
}
