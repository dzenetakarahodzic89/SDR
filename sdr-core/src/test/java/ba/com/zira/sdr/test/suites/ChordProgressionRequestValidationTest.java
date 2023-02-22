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
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;
import ba.com.zira.sdr.core.validation.ChordProgressionValidation;
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class ChordProgressionRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private ChordProgressionDAO chordProgressionDAO;
    private ChordProgressionValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.chordProgressionDAO = Mockito.mock(ChordProgressionDAO.class);
        this.validation = new ChordProgressionValidation(chordProgressionDAO);
    }

    /**
     * Update
     */
    @Test
    public void validateUpdateRequestChoreProgressionNotFound() {
        Mockito.when(chordProgressionDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<ChordProgressionUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("user"));
        var respose = new ChordProgressionUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateChordProgressionRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Chord progression with id: 1 does not exist!");
        Mockito.verify(chordProgressionDAO).existsByPK(1L);
    }

    @Test
    public void validateExistsChordPRequestNotFound() {
        Mockito.when(chordProgressionDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("user"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateExistsChordProgressionRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Chord progression with id: 1 does not exist!");
        Mockito.verify(chordProgressionDAO).existsByPK(1L);

    }
}
