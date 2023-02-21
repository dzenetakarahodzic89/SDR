package ba.com.zira.sdr.test.suites;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.core.validation.SongArtistRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class SongArtistRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private SongArtistDAO songArtistDAO;
    private AlbumDAO albumDAO;
    private SongDAO songDAO;
    private LabelDAO labelDAO;
    private ArtistDAO artistDAO;
    private SongArtistRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.songArtistDAO = Mockito.mock(SongArtistDAO.class);
        this.albumDAO = Mockito.mock(AlbumDAO.class);
        this.songDAO = Mockito.mock(SongDAO.class);
        this.labelDAO = Mockito.mock(LabelDAO.class);
        this.artistDAO = Mockito.mock(ArtistDAO.class);

        this.validation = new SongArtistRequestValidation(songArtistDAO, songDAO, albumDAO, labelDAO, artistDAO);
    }

    /**
     * Update
     */

    @Test
    public void validateCreateRequestIdsNotFound() {
        ArrayList<String> errorList = new ArrayList<>();
        errorList.add("Album with id: 1 does not exist!");
        errorList.add("Song with id: 1 does not exist!");
        errorList.add("Artist with id: 1 does not exist!");
        errorList.add("Label with id: 1 does not exist!");

        Mockito.when(albumDAO.existsByPK(1L)).thenReturn(false);
        Mockito.when(labelDAO.existsByPK(1L)).thenReturn(false);
        Mockito.when(artistDAO.existsByPK(1L)).thenReturn(false);
        Mockito.when(songDAO.existsByPK(1L)).thenReturn(false);

        var createRequestEntity = new SongArtistCreateRequest();
        createRequestEntity.setAlbumId(1L);
        createRequestEntity.setArtistId(1L);
        createRequestEntity.setSongId(1L);
        createRequestEntity.setLabelId(1L);

        EntityRequest<SongArtistCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(createRequestEntity);

        ValidationResponse validationResponse = validation.validateCreateSongArtistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        errorList.forEach(error -> assertTrue(validationResponse.getDescription().contains(error)));

        Mockito.verify(songDAO).existsByPK(1L);
        Mockito.verify(artistDAO).existsByPK(1L);
        Mockito.verify(labelDAO).existsByPK(1L);
        Mockito.verify(albumDAO).existsByPK(1L);
    }

    @Test
    public void validateCreateRequestAllIdsFound() {
        Mockito.when(songDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(artistDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(labelDAO.existsByPK(1L)).thenReturn(true);
        Mockito.when(albumDAO.existsByPK(1L)).thenReturn(true);

        var createRequestEntity = new SongArtistCreateRequest();
        createRequestEntity.setAlbumId(1L);
        createRequestEntity.setArtistId(1L);
        createRequestEntity.setSongId(1L);
        createRequestEntity.setLabelId(1L);

        EntityRequest<SongArtistCreateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(createRequestEntity);

        ValidationResponse validationResponse = validation.validateCreateSongArtistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.OK);
        assertEquals(validationResponse.getDescription(), null);

        Mockito.verify(songDAO).existsByPK(1L);
        Mockito.verify(artistDAO).existsByPK(1L);
        Mockito.verify(labelDAO).existsByPK(1L);
        Mockito.verify(albumDAO).existsByPK(1L);
    }

    @Test
    public void validateDeleteRequestIdNotFound() {
        Mockito.when(songArtistDAO.existsByPK(1L)).thenReturn(false);

        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);

        ValidationResponse validationResponse = validation.validateDeleteSongArtistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Song-Artist with id: 1 does not exist!");

        Mockito.verify(songArtistDAO).existsByPK(1L);
    }

    @Test
    public void validateDeleteRequestIdFound() {
        Mockito.when(songArtistDAO.existsByPK(1L)).thenReturn(true);

        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);

        ValidationResponse validationResponse = validation.validateDeleteSongArtistRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.OK);
        assertEquals(validationResponse.getDescription(), null);

        Mockito.verify(songArtistDAO).existsByPK(1L);
    }
}
