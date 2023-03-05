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
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.zira.sdr.core.validation.SpotifyIntegrationRequestValidation;
import ba.com.zira.sdr.dao.SpotifyIntegrationDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class SpotifyIntegrationRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";
    private SpotifyIntegrationDAO spotifyIntegrationDAO;
    private SpotifyIntegrationRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.spotifyIntegrationDAO = Mockito.mock(SpotifyIntegrationDAO.class);
        this.validation = new SpotifyIntegrationRequestValidation(spotifyIntegrationDAO);
    }

    /**
     * Update
     */
    @Test
    public void validateUpdateRequestSpotifyIntegrationNotFound() {
        Mockito.when(spotifyIntegrationDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<SpotifyIntegrationUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var response = new SpotifyIntegrationUpdateRequest();
        response.setId(1L);
        request.setEntity(response);
        ValidationResponse validationResponse = validation.validateUpdateSpotifyIntegrationRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(),
                "Spotify integration with id: 1 does not exist! | Object type: null is not valid!");
        Mockito.verify(spotifyIntegrationDAO).existsByPK(1L);
    }
}
