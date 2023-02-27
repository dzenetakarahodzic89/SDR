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
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentUpdateRequest;
import ba.com.zira.sdr.core.validation.SongInstrumentValidation;
import ba.com.zira.sdr.dao.SongInstrumentDAO;


import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class SongInstrumentRequestValidationTest extends BasicTestConfiguration {
    
 
    private SongInstrumentDAO songInstrumentDAO;
    private SongInstrumentValidation validation;
    

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.songInstrumentDAO = Mockito.mock(SongInstrumentDAO.class);
        this.validation = new SongInstrumentValidation(songInstrumentDAO);
    }

    @Test
    public void validateUpdateRequestSongInstrumentNotFound() {
        Mockito.when(songInstrumentDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<SongInstrumentUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new SongInstrumentUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateSongInstrumentRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Song Instrument with id: 1 does not exist!");
        Mockito.verify(songInstrumentDAO).existsByPK(1L);
    }

}
