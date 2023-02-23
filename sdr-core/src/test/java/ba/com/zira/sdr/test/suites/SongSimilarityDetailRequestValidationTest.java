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
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelUpdateRequest;
import ba.com.zira.sdr.core.validation.SongSimilarityDetailRequestValidation;
import ba.com.zira.sdr.dao.SongSimilarityDetailDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class SongSimilarityDetailRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private SongSimilarityDetailDAO songSimilarityDetailDAO;
    private SongSimilarityDetailRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.songSimilarityDetailDAO = Mockito.mock(SongSimilarityDetailDAO.class);
        this.validation = new SongSimilarityDetailRequestValidation(songSimilarityDetailDAO);
    }

    // UPDATE
    @Test
    public void validateUpdateSongSimilarityDetailRequestNotFound() {
        Mockito.when(songSimilarityDetailDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<SongSimilarityDetailModelUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new SongSimilarityDetailModelUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateSongSimilartyDetailRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Song sample detail with id: 1 does not exist!");
        Mockito.verify(songSimilarityDetailDAO).existsByPK(1L);
    }

    // EXISTS
    @Test
    public void validateExistsSongSimilarityDetailRequestNotFound() {
        Mockito.when(songSimilarityDetailDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateExistsSongSimilartyDetailRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Song sample detail with id: 1 does not exist!");
        Mockito.verify(songSimilarityDetailDAO).existsByPK(1L);

    }

}
