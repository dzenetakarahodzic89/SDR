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
import ba.com.zira.sdr.api.ChordProgressionService;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;
import ba.com.zira.sdr.core.impl.ChordProgressionServiceImpl;
import ba.com.zira.sdr.core.mapper.ChordProgressionMapper;
import ba.com.zira.sdr.core.validation.ChordProgressionValidation;
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class ChordProgressionServiceTest extends BasicTestConfiguration {

    private ChordProgressionDAO chordProgressionDAO;
    private ChordProgressionValidation chordProgressionValidation;
    private ChordProgressionService chordProgressionService;
    @Autowired
    private ChordProgressionMapper chordProgressionMapper;
    private RequestValidator requestValidator;
    private EraDAO eraDAO;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.chordProgressionDAO = Mockito.mock(ChordProgressionDAO.class);
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.chordProgressionValidation = Mockito.mock(ChordProgressionValidation.class);
        this.chordProgressionService = new ChordProgressionServiceImpl(chordProgressionDAO, chordProgressionMapper,
                chordProgressionValidation,eraDAO);

    }

    @Test(enabled = true)
    public void testFindChord() {
        try {
            List<ChordProgressionEntity> entities = new ArrayList<>();
            ChordProgressionEntity firstChordEntity = new ChordProgressionEntity();
            firstChordEntity.setName("Name 1");
            firstChordEntity.setId(1L);
            firstChordEntity.setStatus(Status.ACTIVE.getValue());
            firstChordEntity.setInformation("New info 1");

            List<SongEntity> chordOneSongs = new ArrayList<>();

            SongEntity songOne = new SongEntity();
            songOne.setId(1L);
            songOne.setName("First Song");

            SongEntity songTwo = new SongEntity();
            songTwo.setId(2L);
            songTwo.setName("Second Song");
            chordOneSongs.add(songOne);
            chordOneSongs.add(songTwo);
            firstChordEntity.setSongs(chordOneSongs);

            ChordProgressionEntity secondChordEntity = new ChordProgressionEntity();
            secondChordEntity.setName("Name 2");
            secondChordEntity.setId(2L);
            secondChordEntity.setStatus(Status.ACTIVE.getValue());
            secondChordEntity.setInformation("New info 2");

            List<SongEntity> chordTwo = new ArrayList<>();

            SongEntity songThree = new SongEntity();
            songOne.setId(3L);
            songOne.setName("Third Song");

            SongEntity songFour = new SongEntity();
            songTwo.setId(4L);
            songTwo.setName("Four Song");
            chordOneSongs.add(songThree);
            chordOneSongs.add(songFour);
            firstChordEntity.setSongs(chordTwo);

            entities.add(firstChordEntity);
            entities.add(secondChordEntity);

            PagedData<ChordProgressionEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<ChordProgressionResponse> chordResponses = new ArrayList<>();
            Map<Long, String> firstSongs = new HashMap<>();
            firstSongs.put(1L, "First Song");
            firstSongs.put(2L, "Second Song");
            ChordProgressionResponse firstChordResponse = new ChordProgressionResponse(1L, "Name 1", Status.ACTIVE.getValue(), "New info 1",
                    firstSongs);
            Map<Long, String> secondSongs = new HashMap<>();
            secondSongs.put(3L, "Third Song");
            secondSongs.put(4L, "Four Song");
            ChordProgressionResponse secondChordResponse = new ChordProgressionResponse(2L, "Name 2", Status.ACTIVE.getValue(),
                    "New info 2", secondSongs);

            chordResponses.add(firstChordResponse);
            chordResponses.add(secondChordResponse);

            PagedData<ChordProgressionResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(chordResponses);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(chordProgressionDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);
            Mockito.when(chordProgressionDAO.songsByChordProgression(1L)).thenReturn(firstSongs);
            Mockito.when(chordProgressionDAO.songsByChordProgression(2L)).thenReturn(secondSongs);

            List<ChordProgressionResponse> chordFindResponse = chordProgressionService.find(filterRequest).getPayload();

            Assertions.assertThat(chordFindResponse).as("Check all elements").hasSameElementsAs(chordResponses);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateChord() {
        try {

            EntityRequest<ChordProgressionCreateRequest> req = new EntityRequest<>();

            var chordCreateReq = new ChordProgressionCreateRequest();
            chordCreateReq.setName("New chord 1");
            chordCreateReq.setInformation("New info 1");

            req.setEntity(chordCreateReq);

            var newChordEntity = new ChordProgressionEntity();
            newChordEntity.setName("New chord 1");
            newChordEntity.setInformation("New info 1");

            var chordresponse = new ChordProgressionResponse(null, "New chord 1", Status.ACTIVE.getValue(), "New info 1", null);

            Mockito.when(chordProgressionDAO.persist(newChordEntity)).thenReturn(null);

            PayloadResponse<ChordProgressionResponse> chordFindResponse = chordProgressionService.create(req);

            Assertions.assertThat(chordFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(chordresponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateChord() {
        try {
            EntityRequest<ChordProgressionUpdateRequest> request = new EntityRequest<>();
            ChordProgressionEntity chordEntity = new ChordProgressionEntity();
            chordEntity.setName("Old Test Name 1");
            chordEntity.setInformation("Old Test Information");

            ChordProgressionResponse chordResponse = new ChordProgressionResponse(1L, "Update Test Name 1", null, "Update Test Information",
                    null);

            ChordProgressionUpdateRequest updateChordReq = new ChordProgressionUpdateRequest();
            updateChordReq.setName("Update Test Name 1");
            updateChordReq.setInformation("Update Test Information");

            request.setEntity(updateChordReq);

            Mockito.when(chordProgressionValidation.validateUpdateChordProgressionRequest(request)).thenReturn(null);

            Mockito.when(chordProgressionDAO.findByPK(request.getEntity().getId())).thenReturn(chordEntity);

            Mockito.doNothing().when(chordProgressionDAO).merge(chordEntity);

            var chordUpdateResponse = chordProgressionService.update(request);

            Assertions.assertThat(chordUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("id", "status", "created", "createdBy", "modified", "modifiedBy").isEqualTo(chordResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteChord() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(chordProgressionDAO).removeByPK(req.getEntity());

            var chordFindResponse = chordProgressionService.delete(req);

            Assertions.assertThat(chordFindResponse.getPayload()).isEqualTo("successfully deleted record.");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
