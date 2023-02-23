package ba.com.zira.sdr.test.suites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.core.impl.SongArtistServiceImpl;
import ba.com.zira.sdr.core.mapper.SongArtistMapper;
import ba.com.zira.sdr.core.validation.SongArtistRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class SongArtistServiceTest extends BasicTestConfiguration {
    @Autowired
    private SongArtistMapper songArtistMapper;

    private SongArtistDAO songArtistDAO;
    private SongDAO songDAO;
    private ArtistDAO artistDAO;
    private LabelDAO labelDAO;
    private AlbumDAO albumDAO;
    private SongArtistRequestValidation songArtistRequestValidation;
    private SongArtistService songArtistService;

    private List<AlbumEntity> album = new ArrayList<>();
    private List<SongEntity> song = new ArrayList<>();
    private List<ArtistEntity> artist = new ArrayList<>();
    private List<LabelEntity> label = new ArrayList<>();

    private void setUpFkEntities() {

        album.add(new AlbumEntity(1L, null, null, null, null, null, null, "album test 1", null, null, null));
        album.add(new AlbumEntity(2L, null, null, null, null, null, null, "album test 2", null, null, null));
        album.add(new AlbumEntity(3L, null, null, null, null, null, null, "album test 3", null, null, null));

        label.add(new LabelEntity(1L, null, null, null, null, null, null, "label test 1", null, null, null, null));
        label.add(new LabelEntity(2L, null, null, null, null, null, null, "label test 2", null, null, null, null));
        label.add(new LabelEntity(3L, null, null, null, null, null, null, "label test 3", null, null, null, null));

        artist.add(new ArtistEntity(1L, null, null, null, null, null, null, null, "artist test 1", null, null, null, null, null));
        artist.add(new ArtistEntity(2L, null, null, null, null, null, null, null, "artist test 2", null, null, null, null, null));
        artist.add(new ArtistEntity(3L, null, null, null, null, null, null, null, "artist test 3", null, null, null, null, null));

        song.add(new SongEntity(1L, null, null, null, null, null, null, "song test 1", null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null));
        song.add(new SongEntity(2L, null, null, null, null, null, null, "song test 2", null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null));
        song.add(new SongEntity(3L, null, null, null, null, null, null, "song test 3", null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null));
    }

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.songArtistDAO = Mockito.mock(SongArtistDAO.class);
        this.songDAO = Mockito.mock(SongDAO.class);
        this.artistDAO = Mockito.mock(ArtistDAO.class);
        this.albumDAO = Mockito.mock(AlbumDAO.class);
        this.labelDAO = Mockito.mock(LabelDAO.class);

        this.songArtistRequestValidation = Mockito.mock(SongArtistRequestValidation.class);
        this.songArtistService = new SongArtistServiceImpl(songArtistDAO, albumDAO, labelDAO, artistDAO, songDAO, songArtistMapper,
                songArtistRequestValidation);

        this.setUpFkEntities();
    }

    @Test(enabled = true)
    public void testFindSongArtist() {
        try {

            List<SongArtistEntity> entities = new ArrayList<>();

            SongArtistEntity firstSongArtistEntity = new SongArtistEntity();
            firstSongArtistEntity.setAlbum(album.get(0));
            firstSongArtistEntity.setArtist(artist.get(0));
            firstSongArtistEntity.setLabel(label.get(0));
            firstSongArtistEntity.setSong(song.get(0));

            SongArtistEntity secondSongArtistEntity = new SongArtistEntity();
            secondSongArtistEntity.setAlbum(album.get(1));
            secondSongArtistEntity.setArtist(artist.get(1));
            secondSongArtistEntity.setLabel(label.get(1));
            secondSongArtistEntity.setSong(song.get(1));

            SongArtistEntity thirdSongArtistEntity = new SongArtistEntity();
            thirdSongArtistEntity.setAlbum(album.get(2));
            thirdSongArtistEntity.setArtist(artist.get(2));
            thirdSongArtistEntity.setLabel(label.get(2));
            thirdSongArtistEntity.setSong(song.get(2));

            entities.add(firstSongArtistEntity);
            entities.add(secondSongArtistEntity);
            entities.add(thirdSongArtistEntity);

            PagedData<SongArtistEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<SongArtistResponse> response = new ArrayList<>();

            SongArtistResponse firstResponse = new SongArtistResponse();
            firstResponse.setAlbumId(1L);
            firstResponse.setAlbumName("album test 1");
            firstResponse.setArtistId(1L);
            firstResponse.setArtistName("artist test 1");
            firstResponse.setSongId(1L);
            firstResponse.setSongName("song test 1");
            firstResponse.setLabelId(1L);
            firstResponse.setLabelName("label test 1");

            SongArtistResponse secondResponse = new SongArtistResponse();
            secondResponse.setAlbumId(2L);
            secondResponse.setAlbumName("album test 2");
            secondResponse.setArtistId(2L);
            secondResponse.setArtistName("artist test 2");
            secondResponse.setSongId(2L);
            secondResponse.setSongName("song test 2");
            secondResponse.setLabelId(2L);
            secondResponse.setLabelName("label test 2");

            SongArtistResponse thirdResponse = new SongArtistResponse();
            thirdResponse.setAlbumId(3L);
            thirdResponse.setAlbumName("album test 3");
            thirdResponse.setArtistId(3L);
            thirdResponse.setArtistName("artist test 3");
            thirdResponse.setSongId(3L);
            thirdResponse.setSongName("song test 3");
            thirdResponse.setLabelId(3L);
            thirdResponse.setLabelName("label test 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<SongArtistResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();

            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);
            Mockito.when(songArtistDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<SongArtistResponse> songArtistFindResponse = songArtistService.get(filterRequest).getPayload();

            Assertions.assertThat(songArtistFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateSongArtist() {
        try {

            EntityRequest<SongArtistCreateRequest> req = new EntityRequest<>();

            var newSongArtistRequest = new SongArtistCreateRequest();
            newSongArtistRequest.setAlbumId(1L);
            newSongArtistRequest.setArtistId(1L);
            newSongArtistRequest.setLabelId(1L);
            newSongArtistRequest.setSongId(1L);

            req.setEntity(newSongArtistRequest);

            SongArtistEntity newSongArtistEntity = new SongArtistEntity();
            newSongArtistEntity.setAlbum(album.get(0));
            newSongArtistEntity.setArtist(artist.get(0));
            newSongArtistEntity.setLabel(label.get(0));
            newSongArtistEntity.setSong(song.get(0));

            var newSongArtistResponse = new SongArtistResponse();
            newSongArtistResponse.setAlbumId(1L);
            newSongArtistResponse.setAlbumName("album test 1");
            newSongArtistResponse.setArtistId(1L);
            newSongArtistResponse.setArtistName("artist test 1");
            newSongArtistResponse.setSongId(1L);
            newSongArtistResponse.setSongName("song test 1");
            newSongArtistResponse.setLabelId(1L);
            newSongArtistResponse.setLabelName("label test 1");
            newSongArtistResponse.setStatus(Status.ACTIVE.getValue());

            Mockito.when(songArtistDAO.persist(newSongArtistEntity)).thenReturn(null);
            Mockito.when(songArtistRequestValidation.validateCreateSongArtistRequest(req)).thenReturn(null);

            Mockito.when(albumDAO.findByPK(1L)).thenReturn(album.get(0));
            Mockito.when(artistDAO.findByPK(1L)).thenReturn(artist.get(0));
            Mockito.when(labelDAO.findByPK(1L)).thenReturn(label.get(0));
            Mockito.when(songDAO.findByPK(1L)).thenReturn(song.get(0));

            PayloadResponse<SongArtistResponse> songArtistCreateResponse = songArtistService.create(req);

            Assertions.assertThat(songArtistCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newSongArtistResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteSongArtist() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(songArtistRequestValidation.validateDeleteSongArtistRequest(req)).thenReturn(null);
            Mockito.doNothing().when(songArtistDAO).removeByPK(req.getEntity());

            var songArtistDeleteResponse = songArtistService.delete(req);

            Assertions.assertThat(songArtistDeleteResponse.getPayload()).isEqualTo("Deleted record successfully!");
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
