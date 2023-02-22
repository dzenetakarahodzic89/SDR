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
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.core.validation.GenreRequestValidation;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class GenreRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private GenreDAO genreDAO;
    private GenreRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.genreDAO = Mockito.mock(GenreDAO.class);
        this.validation = new GenreRequestValidation(genreDAO);
    }

    /* Create */
    @Test
    public void validateCreateRequestNameExists() {
        Mockito.when(genreDAO.existsByName("name")).thenReturn(true);
        EntityRequest<GenreCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new GenreCreateRequest();
        respose.setName("name");
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateGenreCreateRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genre with name: name already exists!");
        Mockito.verify(genreDAO).existsByName("name");

    }

    @Test
    public void validateCreateRequestMainGenreNotFound() {
        Mockito.when(genreDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<GenreCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new GenreCreateRequest();
        respose.setMainGenreId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateGenreCreateRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genre with id: 1 does not exist!");
        Mockito.verify(genreDAO).existsByPK(1L);

    }

    /**
     * Update
     */

    @Test
    public void validateUpdateRequestGenreNotFound() {
        Mockito.when(genreDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<GenreUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new GenreUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateGenreUpdateRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genre with id: 1 does not exist!");
        Mockito.verify(genreDAO).existsByPK(1L);

    }

    @Test
    public void validateUpdateRequestMainGenreNotFound() {
        Mockito.when(genreDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(genreDAO.existsByPK(2L)).thenReturn(false);
        EntityRequest<GenreUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new GenreUpdateRequest();
        respose.setId(1L);
        respose.setMainGenreId(2L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateGenreUpdateRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genre with id: 2 does not exist!");
        Mockito.verify(genreDAO).existsByPK(2L);

    }

    @Test
    public void validateUpdateRequestNameExists() {
        Mockito.when(genreDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(genreDAO.existsByName("name", 1L)).thenReturn(true);
        EntityRequest<GenreUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new GenreUpdateRequest();
        respose.setId(1L);
        respose.setName("name");
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateGenreUpdateRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genre with name: name already exists!");
        Mockito.verify(genreDAO).existsByName("name", 1L);

    }

    /* Delete */
    @Test
    public void validateDeleteRequestGenreNotFound() {
        Mockito.when(genreDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateGenreDeleteRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genre with id: 1 does not exist!");
        Mockito.verify(genreDAO).existsByPK(1L);

    }

    @Test
    public void validateDeleteRequestSubGenresExist() {
        Mockito.when(genreDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(genreDAO.subGenresExist(1L)).thenReturn(true);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateGenreDeleteRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genres with subgenres attached to them are not allowed to be deleted.");
        Mockito.verify(genreDAO).subGenresExist(1L);

    }

    @Test
    public void validateDeleteRequestSongsExist() {
        Mockito.when(genreDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(genreDAO.songsExist(1L)).thenReturn(true);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateGenreDeleteRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Genres with songs attached to them are not allowed to be deleted.");
        Mockito.verify(genreDAO).songsExist(1L);

    }

}
