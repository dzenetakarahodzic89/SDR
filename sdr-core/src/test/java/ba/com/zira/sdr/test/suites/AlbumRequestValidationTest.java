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
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.core.validation.AlbumRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class AlbumRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private AlbumDAO albumDAO;
    private EraDAO eraDAO;
    private AlbumRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.albumDAO = Mockito.mock(AlbumDAO.class);
        this.eraDAO = Mockito.mock(EraDAO.class);
        this.validation = new AlbumRequestValidation(eraDAO, albumDAO);
    }

    @Test
    public void validateCreateRequestNotValid() {
        Mockito.when(eraDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<AlbumCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var response = new AlbumCreateRequest();
        response.setEraId(1L);
        request.setEntity(response);
        ValidationResponse validationResponse = validation.validateCreateAlbumRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Era with id: 1 does not exist!");
        Mockito.verify(eraDAO).existsByPK(1L);
    }

    @Test
    public void validateUpdateRequestAlbumNotFound() {
        Mockito.when(albumDAO.existsByPK(1L)).thenReturn(false);
        Mockito.when(eraDAO.existsByPK(1L)).thenReturn(true);
        EntityRequest<AlbumUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var response = new AlbumUpdateRequest();
        response.setId(1L);
        response.setEraId(1L);
        request.setEntity(response);
        ValidationResponse validationResponse = validation.validateUpdateAlbumRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Album with id: 1 does not exist!");
        Mockito.verify(albumDAO).existsByPK(1L);
    }

    @Test
    public void validateDeleteAlbumRequestNotFound() {
        Mockito.when(albumDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<Long>(1L);
        request.setUser(new User("test"));
        ValidationResponse validationResponse = validation.validateDeleteAlbumRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Album with id: 1 does not exist!");
        Mockito.verify(albumDAO).existsByPK(1L);
    }
}
