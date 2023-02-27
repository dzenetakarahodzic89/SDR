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
import ba.com.zira.sdr.api.SongFftResultService;
import ba.com.zira.sdr.api.model.song.fft.SongFftResult;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;
import ba.com.zira.sdr.core.impl.SongFftResultImpl;
import ba.com.zira.sdr.core.mapper.SongFftMapper;
import ba.com.zira.sdr.core.validation.SongFftResultValidation;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongFftResultDAO;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongFttResultEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class SongFftResultServiceTest extends BasicTestConfiguration {
    @Autowired
    private SongFftMapper songFftMapper;

    private SongFftResultDAO songFftResultDAO;
    private RequestValidator requestValidator;
    private SongFftResultValidation songFftResultValidation;
    private SongFftResultService songFftResultService;
    private SongDAO songDAO;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.songFftResultDAO = Mockito.mock(SongFftResultDAO.class);
        this.songDAO = Mockito.mock(SongDAO.class);
        this.songFftResultValidation = Mockito.mock(SongFftResultValidation.class);
        this.songFftResultService = new SongFftResultImpl(songFftResultDAO, songFftMapper, songFftResultValidation,songDAO);
    }


    @Test(enabled = true)
    public void testFindSongFftResult() {
        try {

            List<SongFttResultEntity> entities = new ArrayList<>();
            var firstsongEnt = new SongEntity();
            firstsongEnt.setId(1L);

            SongFttResultEntity firstSongFttResultEntity  = new SongFttResultEntity();
            firstSongFttResultEntity.setId(1L);
            firstSongFttResultEntity.setFftResults("Test Information 1");
            firstSongFttResultEntity.setSong(firstsongEnt);
            firstSongFttResultEntity.setStatus(Status.ACTIVE.getValue());


            var secondSongEnt = new SongEntity();
            secondSongEnt.setId(2L);

            SongFttResultEntity  secondSongFttResultEntity = new SongFttResultEntity();
            secondSongFttResultEntity.setId(2L);
            secondSongFttResultEntity.setFftResults("Test Information 2");
            secondSongFttResultEntity.setSong(secondSongEnt);
            secondSongFttResultEntity.setStatus(Status.ACTIVE.getValue());

            var thirdSongEnt = new SongEntity();
            thirdSongEnt.setId(3L);
            SongFttResultEntity thirdSongFttResultEntity = new SongFttResultEntity();
            thirdSongFttResultEntity.setId(3L);
            thirdSongFttResultEntity.setFftResults("Test Information 3");
            thirdSongFttResultEntity.setSong(thirdSongEnt);
            thirdSongFttResultEntity.setStatus(Status.ACTIVE.getValue());

            entities.add(firstSongFttResultEntity);
            entities.add(secondSongFttResultEntity);
            entities.add(thirdSongFttResultEntity);

            PagedData<SongFttResultEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<SongFftResult> response = new ArrayList<>();

            SongFftResult firstResponse = new SongFftResult();
            firstResponse.setId(1L);
            firstResponse.setFftResults("Test Information 1");
            firstResponse.setSongID(1L);
            firstResponse.setStatus(Status.ACTIVE.getValue());

            SongFftResult secondResponse = new SongFftResult();
            secondResponse.setId(2L);
            secondResponse.setFftResults("Test Information 2");
            secondResponse.setSongID(2L);
            secondResponse.setStatus(Status.ACTIVE.getValue());

            SongFftResult thirdResponse = new SongFftResult();
            thirdResponse.setId(3L);
            thirdResponse.setFftResults("Test Information 3");
            thirdResponse.setSongID(3L);
            thirdResponse.setStatus(Status.ACTIVE.getValue());

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<SongFftResult> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);


            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(songFftResultDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            Mockito.when(songDAO.findByPK(1L)).thenReturn(firstsongEnt);
            Mockito.when(songDAO.findByPK(2L)).thenReturn(secondSongEnt);
            Mockito.when(songDAO.findByPK(3L)).thenReturn(thirdSongEnt);


            List<SongFftResult> songFftResultFindResponse = songFftResultService.find(filterRequest).getPayload();

            Assertions.assertThat(songFftResultFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
            .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateSongFftResult() {
        try {

            EntityRequest<SongFftResultCreateRequest> req = new EntityRequest<>();

            var newSongFftResultRequest = new SongFftResultCreateRequest();
            newSongFftResultRequest.setFftResults("Test Information ");
            newSongFftResultRequest.setSongID(1L);


            req.setEntity(newSongFftResultRequest);

            var songEnt = new SongEntity();
            songEnt.setId(1L);
            var newSongFftResultEnt = new SongFttResultEntity();
            newSongFftResultEnt.setId(1L);
            newSongFftResultEnt.setFftResults("Test Information ");
            newSongFftResultEnt.setSong(songEnt);
            newSongFftResultEnt.setStatus(Status.ACTIVE.getValue());

            var newSongFftResult = new SongFftResult();
            newSongFftResult.setFftResults("Test Information ");
            newSongFftResult.setSongID(1L);
            newSongFftResult.setStatus(Status.ACTIVE.getValue());

            Mockito.when(songFftResultDAO.persist(newSongFftResultEnt)).thenReturn(null);
            Mockito.when(songDAO.findByPK(req.getEntity().getSongID())).thenReturn(songEnt);

            PayloadResponse<SongFftResult> songFftResultFindResponse = songFftResultService.create(req);

            Assertions.assertThat(songFftResultFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
            .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newSongFftResult);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateSongFftResult() {
        try {

            EntityRequest<SongFftResultUpdateRequest> req = new EntityRequest<>();

            var songEnt = new SongEntity();
            songEnt.setId(1L);

            SongFftResultUpdateRequest updatesongFftResulUpdateRequest = new SongFftResultUpdateRequest();
            updatesongFftResulUpdateRequest.setId(1L);
            updatesongFftResulUpdateRequest.setFftResults("Test 1");
            updatesongFftResulUpdateRequest.setSongID(1L);
            req.setEntity(updatesongFftResulUpdateRequest);

            var songFftResultEnt = new SongFttResultEntity();
            songFftResultEnt.setId(1L);
            songFftResultEnt.setFftResults("Test 1");
            songFftResultEnt.setSong(songEnt);
            songFftResultEnt.setStatus(Status.ACTIVE.getValue());

            var songFftResult = new SongFftResult();
            songFftResult.setId(1L);
            songFftResult.setFftResults("Test 1");
            songFftResult.setSongID(1L);
            songFftResult.setStatus(Status.ACTIVE.getValue());


            Mockito.when(songFftResultValidation.validateUpdateSongFftResultRequest(req)).thenReturn(null);

            Mockito.when(songFftResultDAO.findByPK(req.getEntity().getId())).thenReturn(songFftResultEnt);
            Mockito.when(songDAO.findByPK(1L)).thenReturn(songEnt);


            Mockito.doNothing().when(songFftResultDAO).merge(songFftResultEnt);

            var songFftResultUpdateResponse = songFftResultService.update(req);
            Assertions.assertThat(songFftResultUpdateResponse.getPayload()).as("Check all fields")
            .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
            .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(songFftResult);
        } catch (Exception e) {
            Assert.fail();
        }
    }


    @Test(enabled = true)
    public void testDeleteSongFftResult() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(songFftResultValidation.validateExistsSongFftResultRequest(req)).thenReturn(null);

            Mockito.doNothing().when(songFftResultDAO).removeByPK(req.getEntity());

            var songFftResultDeleteResponse = songFftResultService.delete(req);

            Assertions.assertThat(songFftResultDeleteResponse.getPayload()).isNull();

        } catch (Exception e) {
            Assert.fail();
        }
    }

}

