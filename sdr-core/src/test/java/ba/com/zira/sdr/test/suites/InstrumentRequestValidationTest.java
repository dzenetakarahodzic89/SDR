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
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.core.validation.InstrumentRequestValidation;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class InstrumentRequestValidationTest extends BasicTestConfiguration {

    private InstrumentDAO instrumentDAO;
    private InstrumentRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.instrumentDAO = Mockito.mock(InstrumentDAO.class);
        this.validation = new InstrumentRequestValidation(instrumentDAO);
    }

    @Test
    public void validateUpdateRequestInstrumentNotFound() {
        Mockito.when(instrumentDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<InstrumentUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new InstrumentUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateInstrumentRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Instrument with id: 1 does not exist!");
        Mockito.verify(instrumentDAO).existsByPK(1L);
    }

}
