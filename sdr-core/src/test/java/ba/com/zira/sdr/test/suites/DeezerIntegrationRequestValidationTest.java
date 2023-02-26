package ba.com.zira.sdr.test.suites;

import static org.testng.AssertJUnit.assertEquals;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.core.validation.DeezerIntegrationRequestValidation;
import ba.com.zira.sdr.dao.DeezerIntegrationDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class DeezerIntegrationRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";
    private DeezerIntegrationDAO deezerIntegrationDAO;
    private DeezerIntegrationRequestValidation validation;
    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.deezerIntegrationDAO = Mockito.mock(DeezerIntegrationDAO.class);
        this.validation = new DeezerIntegrationRequestValidation(deezerIntegrationDAO);
    }
    @Test
    public void validateUpdateRequestDeezerIntegrationNotFound() {
        Mockito.when(deezerIntegrationDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<DeezerIntegrationUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var response = new DeezerIntegrationUpdateRequest();
        response.setId(1L);
        request.setEntity(response);
        ValidationResponse validationResponse = validation.validateUpdateDeezerIntegrationRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Deezer integration with id: 1 does not exist!");
        Mockito.verify(deezerIntegrationDAO).existsByPK(1L);
    }
}
