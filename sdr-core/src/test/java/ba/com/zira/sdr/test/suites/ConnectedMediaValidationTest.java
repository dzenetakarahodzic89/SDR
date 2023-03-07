package ba.com.zira.sdr.test.suites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaUpdateRequest;
import ba.com.zira.sdr.core.validation.ConnectedMediaRequestValidation;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class ConnectedMediaValidationTest extends BasicTestConfiguration {

    private ConnectedMediaDAO connectedMediaDAO;
    private ConnectedMediaRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.connectedMediaDAO = Mockito.mock(ConnectedMediaDAO.class);
        this.validation = new ConnectedMediaRequestValidation(connectedMediaDAO);
    }

    @Test
    public void validateUpdateRequestConnectedMediaNotFound() {
        Mockito.when(connectedMediaDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<ConnectedMediaUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new ConnectedMediaUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateConnectedMediaUpdateRequest(request);

        assertEquals(ResponseCode.REQUEST_INVALID, validationResponse.getCode());
        assertEquals("Connected Media with id: 1 does not exist!", validationResponse.getDescription());
        Mockito.verify(connectedMediaDAO).existsByPK(1L);
    }

}
