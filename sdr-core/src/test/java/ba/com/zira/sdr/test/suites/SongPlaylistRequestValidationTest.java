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
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;
import ba.com.zira.sdr.core.validation.SongPlaylistRequestValidation;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongPlaylistDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class SongPlaylistRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private SongPlaylistDAO songPlaylistDAO;
    private SongDAO songDAO;
    private PlaylistDAO playlistDAO;
    private SongPlaylistRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.songDAO = Mockito.mock(SongDAO.class);
        this.playlistDAO = Mockito.mock(PlaylistDAO.class);
        this.songPlaylistDAO = Mockito.mock(SongPlaylistDAO.class);
        this.validation = new SongPlaylistRequestValidation(songPlaylistDAO, songDAO, playlistDAO);
    }

    @Test
    public void validateUpdateRequestSongPlaylistNotFound() {
        Mockito.when(songPlaylistDAO.existsByPK(1L)).thenReturn(false);
        Mockito.when(songDAO.existsByPK(null)).thenReturn(true);
        Mockito.when(playlistDAO.existsByPK(null)).thenReturn(true);

        EntityRequest<SongPlaylistUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("TEST"));
        var respose = new SongPlaylistUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateSongPlaylistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "SongPlaylist with id: 1 does not exist!");
        Mockito.verify(songPlaylistDAO).existsByPK(1L);
    }

    @Test
    public void validateExistsSongPlaylistNotFound() {
        Mockito.when(songPlaylistDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("TEST"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateExistsSongPlaylistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "SongPlaylist with id: 1 does not exist!");
        Mockito.verify(songPlaylistDAO).existsByPK(1L);

    }
}
