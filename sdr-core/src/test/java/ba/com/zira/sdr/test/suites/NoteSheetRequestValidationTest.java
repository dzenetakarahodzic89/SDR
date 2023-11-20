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
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.core.validation.NoteSheetRequestValidation;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class NoteSheetRequestValidationTest extends BasicTestConfiguration {

    private NoteSheetDAO noteSheetDAO;
    private NoteSheetRequestValidation validation;
    private SongDAO songDAO;
    private InstrumentDAO instrumentDAO;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.noteSheetDAO = Mockito.mock(NoteSheetDAO.class);
        this.validation = new NoteSheetRequestValidation(noteSheetDAO, songDAO, instrumentDAO);
    }

    @Test
    public void validateUpdateRequestNoteSheetNotFound() {
        Mockito.when(noteSheetDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<NoteSheetUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new NoteSheetUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateNoteSheetRequest(request);

        assertEquals(ResponseCode.REQUEST_INVALID, validationResponse.getCode());
        assertEquals("NoteSheet with id: 1 does not exist!", validationResponse.getDescription());
        Mockito.verify(noteSheetDAO).existsByPK(1L);
    }

}
