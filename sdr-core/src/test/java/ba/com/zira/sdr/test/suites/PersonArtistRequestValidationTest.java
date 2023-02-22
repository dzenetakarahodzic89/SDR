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
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.core.validation.PersonArtistRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class PersonArtistRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private PersonDAO personDAO;
    private ArtistDAO artistDAO;
    private PersonArtistDAO personArtistDAO;
    private PersonArtistRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.personDAO = Mockito.mock(PersonDAO.class);
        this.artistDAO = Mockito.mock(ArtistDAO.class);
        this.personArtistDAO = Mockito.mock(PersonArtistDAO.class);
        this.validation = new PersonArtistRequestValidation(personArtistDAO, personDAO, artistDAO);
    }

    @Test
    public void validateCreatePersonArtistRequestPersonNotValid() {
        Mockito.when(personDAO.existsByPK(1L)).thenReturn(false);
        Mockito.when(artistDAO.existsByPK(1L)).thenReturn(true);
        EntityRequest<PersonArtistCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var response = new PersonArtistCreateRequest();
        response.setPersonId(1L);
        response.setArtistId(1L);
        request.setEntity(response);
        ValidationResponse validationResponse = validation.validateCreatePersonArtistRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Person with id: 1 does not exist!");
        Mockito.verify(personDAO).existsByPK(1L);
    }

    @Test
    public void validateCreatePersonArtistRequestArtistNotValid() {
        Mockito.when(personDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(artistDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<PersonArtistCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var response = new PersonArtistCreateRequest();
        response.setPersonId(1L);
        response.setArtistId(1L);
        request.setEntity(response);
        ValidationResponse validationResponse = validation.validateCreatePersonArtistRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Artist with id: 1 does not exist!");
        Mockito.verify(personDAO).existsByPK(1L);
    }

    @Test
    public void validateDeletePersonArtistRequestNotValid() {
        Mockito.when(personArtistDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<Long>(1L);
        request.setUser(new User("test"));
        ValidationResponse validationResponse = validation.validateDeletePersonArtistRequest(request);
        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Person-Artist with id: 1 does not exist!");
        Mockito.verify(personArtistDAO).existsByPK(1L);
    }
}
