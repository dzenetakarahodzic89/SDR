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
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.PlaylistService;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.core.impl.PlaylistServiceImpl;
import ba.com.zira.sdr.core.mapper.PlaylistMapper;
import ba.com.zira.sdr.core.validation.PlaylistRequestValidation;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.dao.model.PlaylistEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class PlaylistServiceTest extends BasicTestConfiguration {

    @Autowired
    private PlaylistMapper playlistMapper;

    private PlaylistDAO playlistDAO;
    private RequestValidator requestValidator;
    private PlaylistRequestValidation playlistValidation;
    private PlaylistService playlistService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.playlistDAO = Mockito.mock(PlaylistDAO.class);
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.playlistValidation = Mockito.mock(PlaylistRequestValidation.class);

        this.playlistService = new PlaylistServiceImpl(playlistDAO, playlistMapper, playlistValidation);
    }

    @Test(enabled = true)
    public void testFindPlaylist() {
        try {

            List<PlaylistEntity> entities = new ArrayList<>();

            PlaylistEntity firstPlaylistEntity = new PlaylistEntity();
            firstPlaylistEntity.setName("Test Playlist 1");
            firstPlaylistEntity.setInformation("Information about Playlist");
            firstPlaylistEntity.setStatus(Status.ACTIVE.getValue());

            PlaylistEntity secondPlaylistEntity = new PlaylistEntity();
            secondPlaylistEntity.setName("Test Playlist 2");
            secondPlaylistEntity.setInformation("Information about second Playlist");
            secondPlaylistEntity.setStatus(Status.INACTIVE.getValue());

            PlaylistEntity thirdPlaylistEntity = new PlaylistEntity();
            thirdPlaylistEntity.setName("Test Playlist 3");
            thirdPlaylistEntity.setInformation("Information about third Playlist");
            thirdPlaylistEntity.setStatus(Status.ACTIVE.getValue());

            entities.add(firstPlaylistEntity);
            entities.add(secondPlaylistEntity);
            entities.add(thirdPlaylistEntity);

            PagedData<PlaylistEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<Playlist> response = new ArrayList<>();

            Playlist firstResponse = new Playlist();
            firstResponse.setName("Test Playlist 1");
            firstResponse.setInformation("Information about Playlist");
            firstResponse.setStatus(Status.ACTIVE.getValue());

            Playlist secondResponse = new Playlist();
            secondResponse.setName("Test Playlist 2");
            secondResponse.setInformation("Information about second Playlist");
            secondResponse.setStatus(Status.INACTIVE.getValue());

            Playlist thirdResponse = new Playlist();
            thirdResponse.setName("Test Playlist 3");
            thirdResponse.setInformation("Information about third Playlist");
            thirdResponse.setStatus(Status.ACTIVE.getValue());

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<Playlist> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(playlistDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<Playlist> playlistFindResponse = playlistService.find(filterRequest).getPayload();

            Assertions.assertThat(playlistFindResponse).as("Check all elements").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreatePlaylist() {
        try {

            EntityRequest<PlaylistCreateRequest> req = new EntityRequest<>();

            var newPlaylistRequest = new PlaylistCreateRequest();
            newPlaylistRequest.setInformation("Test Information");
            newPlaylistRequest.setName("Test Playlist 1");
            newPlaylistRequest.setStatus(Status.ACTIVE.getValue());

            req.setEntity(newPlaylistRequest);

            var playlistEntity = new PlaylistEntity();
            playlistEntity.setName("Test Playlist 1");
            playlistEntity.setInformation("Test Information");
            playlistEntity.setStatus(Status.ACTIVE.getValue());

            var newPlaylist = new Playlist();
            newPlaylist.setInformation("Test Information");
            newPlaylist.setName("Test Playlist 1");
            newPlaylist.setStatus(Status.ACTIVE.getValue());

            Mockito.when(playlistDAO.persist(playlistEntity)).thenReturn(null);

            PayloadResponse<Playlist> playlistCreateResponse = playlistService.create(req);

            Assertions.assertThat(playlistCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
            .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newPlaylist);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdatePlaylist() {
        try {

            EntityRequest<PlaylistUpdateRequest> request = new EntityRequest<>();

            PlaylistEntity playlistEntity = new PlaylistEntity();
            playlistEntity.setName("Old Test Name 1");
            playlistEntity.setInformation("Old Test Information");

            Playlist playlistResponse = new Playlist();
            playlistResponse.setName("Update Test Name 1");
            playlistResponse.setInformation("Update Test Information");

            PlaylistUpdateRequest updatePlaylistRequest = new PlaylistUpdateRequest();
            updatePlaylistRequest.setName("Update Test Name 1");
            updatePlaylistRequest.setInformation("Update Test Information");

            request.setEntity(updatePlaylistRequest);

            Mockito.when(playlistValidation.validateUpdatePlaylistRequest(request)).thenReturn(null);

            Mockito.when(playlistDAO.findByPK(request.getEntity().getId())).thenReturn(playlistEntity);

            Mockito.doNothing().when(playlistDAO).merge(playlistEntity);

            var playlistUpdateResponse = playlistService.update(request);
            Assertions.assertThat(playlistUpdateResponse.getPayload()).as("Check all fields")
            .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
            .ignoringFields("created", "createdBy", "modified", "modifiedBy", "id", "status").isEqualTo(playlistResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeletePlaylist() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(playlistDAO).removeByPK(req.getEntity());

            var playlistDeleteResponse = playlistService.delete(req);

            Assertions.assertThat(playlistDeleteResponse.getPayload()).isEqualTo("Playlist successfully deleted.");

        } catch (Exception e) {
            Assert.fail();
        }
    }
}
