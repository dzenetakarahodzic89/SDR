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
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.core.validation.ArtistValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class ArtistRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private ArtistDAO atistDAO;
    private ArtistValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.atistDAO = Mockito.mock(ArtistDAO.class);
        this.validation = new ArtistValidation(atistDAO);
    }

    @Test
    public void validateUpdateRequestArtistNotFound() {
        Mockito.when(atistDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<ArtistUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("Test"));
        var respose = new ArtistUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateArtistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Artist with id: 1 does not exist!");
        Mockito.verify(atistDAO).existsByPK(1L);
    }

    @Test
    public void validateExistsChordPRequestNotFound() {
        Mockito.when(atistDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("Test"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateExistsArtistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Artist with id: 1 does not exist!");
        Mockito.verify(atistDAO).existsByPK(1L);

    }

}
