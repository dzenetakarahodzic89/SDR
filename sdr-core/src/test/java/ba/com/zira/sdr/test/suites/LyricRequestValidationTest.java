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
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;
import ba.com.zira.sdr.core.validation.LyricRequestValidation;
import ba.com.zira.sdr.dao.LyricDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class LyricRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private LyricDAO lyricDAO;
    private LyricRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.lyricDAO = Mockito.mock(LyricDAO.class);
        this.validation = new LyricRequestValidation(lyricDAO);
    }

    // UPDATE
    @Test
    public void validateUpdateLyricRequestNotFound() {
        Mockito.when(lyricDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<LyricUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new LyricUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateLyricRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Lyric with id: 1 does not exist!");
        Mockito.verify(lyricDAO).existsByPK(1L);
    }

    // EXISTS
    @Test
    public void validateExistsLyricRequestNotFound() {
        Mockito.when(lyricDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateExistsLyricRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Lyric with id: 1 does not exist!");
        Mockito.verify(lyricDAO).existsByPK(1L);

    }

}
