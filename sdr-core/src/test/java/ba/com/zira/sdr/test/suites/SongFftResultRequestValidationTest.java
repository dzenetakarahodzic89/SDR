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
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;
import ba.com.zira.sdr.core.validation.SongFftResultValidation;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongFftResultDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;


public class SongFftResultRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private SongFftResultDAO songFftResultDAO;
    private SongFftResultValidation validation;
    private SongDAO songDAO;
    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.songFftResultDAO = Mockito.mock(SongFftResultDAO.class);
        this.songDAO = Mockito.mock(SongDAO.class);
        this.validation = new SongFftResultValidation(songFftResultDAO,songDAO);
    }

    @Test
    public void validateCreateRequestNotValid() {

        Mockito.when(songDAO.existsByPK(1L)).thenReturn(false);

        var response = new SongFftResultCreateRequest();
        response.setSongID(1L);

        EntityRequest<SongFftResultCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(response);

        ValidationResponse validationResponse = validation.validateCreateSongFftResultRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Song with id: 1 does not exist!");

        Mockito.verify(songDAO).existsByPK(1L);
    }
    // UPDATE
    @Test
    public void validateUpdateSongFftResultRequestNotFound() {
        Mockito.when(songFftResultDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(songDAO.existsByPK(1L)).thenReturn(false);

        var respose = new SongFftResultUpdateRequest();
        respose.setId(1L);
        respose.setSongID(1L);

        EntityRequest<SongFftResultUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(respose);

        ValidationResponse validationResponse = validation.validateUpdateSongFftResultRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Song with id: 1 does not exist!");

        Mockito.verify(songFftResultDAO).existsByPK(1L);
    }
    // EXISTS
    @Test
    public void validateExistsSongFftResultRequestNotFound() {
        Mockito.when(songFftResultDAO.existsByPK(1L)).thenReturn(false);

        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);

        ValidationResponse validationResponse = validation.validateExistsSongFftResultRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "SongFftResult with id: 1 does not exist!");

        Mockito.verify(songFftResultDAO).existsByPK(1L);

    }

    @Test
    public void validateSongFftResultRequestNotFound() {

        Mockito.when(songFftResultDAO.existsByPK(1L)).thenReturn(false);
        Mockito.when(songDAO.existsByPK(1L)).thenReturn(true);

        var respose = new SongFftResultUpdateRequest();
        respose.setId(1L);
        respose.setSongID(1L);

        EntityRequest<SongFftResultUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(respose);

        ValidationResponse validationResponse = validation.validateUpdateSongFftResultRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "SongFftResult with id: 1 does not exist!");

        Mockito.verify(songFftResultDAO).existsByPK(1L);

    }

}
