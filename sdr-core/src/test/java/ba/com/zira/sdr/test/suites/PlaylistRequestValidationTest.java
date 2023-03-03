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
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.core.validation.PlaylistRequestValidation;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class PlaylistRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private PlaylistDAO playlistDAO;
    private PlaylistRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.playlistDAO = Mockito.mock(PlaylistDAO.class);
        this.validation = new PlaylistRequestValidation(playlistDAO);
    }

    @Test
    public void validateUpdateRequestPlaylistNotFound() {
        Mockito.when(playlistDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<PlaylistUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("TEST"));
        var respose = new PlaylistUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdatePlaylistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Playlist with id: 1 does not exist!");
        Mockito.verify(playlistDAO).existsByPK(1L);
    }

    @Test
    public void validateExistsPlaylistNotFound() {
        Mockito.when(playlistDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("TEST"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateExistsPlaylistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Playlist with id: 1 does not exist!");
        Mockito.verify(playlistDAO).existsByPK(1L);

    }
}