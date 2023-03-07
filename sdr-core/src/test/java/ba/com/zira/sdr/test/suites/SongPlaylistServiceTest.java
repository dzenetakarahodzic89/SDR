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
import ba.com.zira.sdr.api.SongPlaylistService;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylist;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistCreateRequest;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;
import ba.com.zira.sdr.core.impl.SongPlaylistServiceImpl;
import ba.com.zira.sdr.core.mapper.SongPlaylistMapper;
import ba.com.zira.sdr.core.validation.SongPlaylistRequestValidation;
import ba.com.zira.sdr.dao.SongPlaylistDAO;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class SongPlaylistServiceTest extends BasicTestConfiguration {

    @Autowired
    private SongPlaylistMapper songplaylistMapper;

    private SongPlaylistDAO songplaylistDAO;
    private RequestValidator requestValidator;
    private SongPlaylistRequestValidation songplaylistValidation;
    private SongPlaylistService songplaylistService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.songplaylistDAO = Mockito.mock(SongPlaylistDAO.class);
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.songplaylistValidation = Mockito.mock(SongPlaylistRequestValidation.class);
        this.songplaylistService = new SongPlaylistServiceImpl(songplaylistDAO, songplaylistMapper, songplaylistValidation);
    }

    @Test(enabled = true)
    public void testFindPlaylist() {
        try {

            List<SongPlaylistEntity> entities = new ArrayList<>();

            SongPlaylistEntity firstSongPlaylistEntity = new SongPlaylistEntity();
            firstSongPlaylistEntity.setPlaylistId(Long.valueOf(5));
            firstSongPlaylistEntity.setSongId(10L);
            firstSongPlaylistEntity.setStatus(Status.ACTIVE.getValue());

            SongPlaylistEntity secondSongPlaylistEntity = new SongPlaylistEntity();
            secondSongPlaylistEntity.setPlaylistId(Long.valueOf(5));
            secondSongPlaylistEntity.setSongId(10L);
            secondSongPlaylistEntity.setStatus(Status.INACTIVE.getValue());

            SongPlaylistEntity thirdSongPlaylistEntity = new SongPlaylistEntity();
            thirdSongPlaylistEntity.setPlaylistId(Long.valueOf(5));
            thirdSongPlaylistEntity.setSongId(10L);
            thirdSongPlaylistEntity.setStatus(Status.ACTIVE.getValue());

            entities.add(firstSongPlaylistEntity);
            entities.add(secondSongPlaylistEntity);
            entities.add(thirdSongPlaylistEntity);

            PagedData<SongPlaylistEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<SongPlaylist> response = new ArrayList<>();

            SongPlaylist firstResponse = new SongPlaylist();
            firstResponse.setPlaylistId(Long.valueOf(5));
            firstResponse.setSongId(10L);
            firstResponse.setStatus(Status.ACTIVE.getValue());

            SongPlaylist secondResponse = new SongPlaylist();
            secondResponse.setPlaylistId(Long.valueOf(5));
            secondResponse.setSongId(10L);
            secondResponse.setStatus(Status.INACTIVE.getValue());

            SongPlaylist thirdResponse = new SongPlaylist();
            thirdResponse.setPlaylistId(Long.valueOf(5));
            thirdResponse.setSongId(10L);
            thirdResponse.setStatus(Status.ACTIVE.getValue());

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<SongPlaylist> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(songplaylistDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<SongPlaylist> songPlaylistFindResponse = songplaylistService.find(filterRequest).getPayload();

            Assertions.assertThat(songPlaylistFindResponse).as("Check all elements").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateSongPlaylist() {
        try {

            EntityRequest<SongPlaylistCreateRequest> req = new EntityRequest<>();

            var newSongPlaylistRequest = new SongPlaylistCreateRequest();
            newSongPlaylistRequest.setPlaylistId(Long.valueOf(5));
            newSongPlaylistRequest.setSongId(10L);

            req.setEntity(newSongPlaylistRequest);

            var songplaylistEntity = new SongPlaylistEntity();
            songplaylistEntity.setPlaylistId(Long.valueOf(5));
            songplaylistEntity.setSongId(10L);
            songplaylistEntity.setStatus(Status.ACTIVE.getValue());

            var newSongPlaylist = new SongPlaylist();
            newSongPlaylist.setPlaylistId(Long.valueOf(5));
            newSongPlaylist.setSongId(10L);
            newSongPlaylist.setStatus(Status.ACTIVE.getValue());

            Mockito.when(songplaylistDAO.persist(songplaylistEntity)).thenReturn(null);

            PayloadResponse<SongPlaylist> songplaylistCreateResponse = songplaylistService.create(req);

            Assertions.assertThat(songplaylistCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newSongPlaylist);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateSongPlaylist() {
        try {

            EntityRequest<SongPlaylistUpdateRequest> request = new EntityRequest<>();

            SongPlaylistEntity songPlaylistEntity = new SongPlaylistEntity();
            songPlaylistEntity.setPlaylistId(Long.valueOf(5));
            songPlaylistEntity.setSongId(10L);

            SongPlaylist songPlaylistResponse = new SongPlaylist();
            songPlaylistResponse.setPlaylistId(Long.valueOf(5));
            songPlaylistResponse.setSongId(10L);

            SongPlaylistUpdateRequest updateSongPlaylistRequest = new SongPlaylistUpdateRequest();
            updateSongPlaylistRequest.setPlaylistId(Long.valueOf(5));
            updateSongPlaylistRequest.setSongId(10L);

            request.setEntity(updateSongPlaylistRequest);

            Mockito.when(songplaylistValidation.validateUpdateSongPlaylistRequest(request)).thenReturn(null);

            Mockito.when(songplaylistDAO.findByPK(request.getEntity().getId())).thenReturn(songPlaylistEntity);

            Mockito.doNothing().when(songplaylistDAO).merge(songPlaylistEntity);

            var songplaylistUpdateResponse = songplaylistService.update(request);
            Assertions.assertThat(songplaylistUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "id", "status").isEqualTo(songPlaylistResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteSongPlaylist() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(songplaylistDAO).removeByPK(req.getEntity());

            var songplaylistDeleteResponse = songplaylistService.delete(req);

            Assertions.assertThat(songplaylistDeleteResponse.getPayload()).isEqualTo("Song Playlist successfully deleted.");

        } catch (Exception e) {
            Assert.fail();
        }
    }
}
