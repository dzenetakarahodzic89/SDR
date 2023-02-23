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
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class PersonRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private PersonDAO personDAO;
    private PersonRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.personDAO = Mockito.mock(PersonDAO.class);
        this.validation = new PersonRequestValidation(personDAO);
    }

    @Test
    public void validateExistsPersonRequestNotFound() {
        Mockito.when(personDAO.existsByPK(1L)).thenReturn(false);

        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);


        ValidationResponse validationResponse = validation.validateExistsPersonRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Person with id: 1 does not exist!");
        Mockito.verify(personDAO).existsByPK(1L);

    }

    @Test
    public void validateUpdateRequestPersonNotFound() {
        Mockito.when(personDAO.existsByPK(1L)).thenReturn(false);

        EntityRequest<PersonUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));

        var respose = new PersonUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);

        ValidationResponse validationResponse = validation.validateUpdatePersonRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Person with id: 1 does not exist!");
        Mockito.verify(personDAO).existsByPK(1L);
    }
}
