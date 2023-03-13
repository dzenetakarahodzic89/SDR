package ba.com.zira.sdr.test.suites;

import java.time.LocalDateTime;
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
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.core.impl.ArtistServiceImpl;
import ba.com.zira.sdr.core.mapper.ArtistMapper;
import ba.com.zira.sdr.core.validation.ArtistValidation;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class ArtistServiceTest extends BasicTestConfiguration {

    @Autowired
    private ArtistMapper artistMapper;
    private ArtistDAO artistDAO;
    EraDAO eraDAO;
    PersonDAO personDAO;
    private PersonArtistDAO personArtistDAO;
    private SongArtistDAO songArtistDAO;
    private RequestValidator requestValidator;
    private ArtistValidation artistValidation;
    private ArtistService artistService;
    private PersonRequestValidation personRequestValidation;

    ArtistValidation artistRequestValidation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.artistDAO = Mockito.mock(ArtistDAO.class);
        this.songArtistDAO = Mockito.mock(SongArtistDAO.class);
        this.personArtistDAO = Mockito.mock(PersonArtistDAO.class);
        this.artistValidation = Mockito.mock(ArtistValidation.class);
        this.personRequestValidation = Mockito.mock(PersonRequestValidation.class);

        this.artistService = new ArtistServiceImpl(artistDAO, eraDAO, null, personDAO, artistMapper, null, null, null, null,
                artistValidation, personArtistDAO, songArtistDAO, null, personRequestValidation);
        this.artistService = new ArtistServiceImpl(artistDAO, eraDAO, personDAO, artistMapper, artistValidation, personArtistDAO,
                songArtistDAO, personRequestValidation, null);
    }

    @Test(enabled = true)
    public void testFindArtist() {
        try {
            List<ArtistEntity> entities = new ArrayList<>();

            ArtistEntity firstArtistEntity = new ArtistEntity();
            SongArtistEntity firstSongArtistEntity = new SongArtistEntity();
            firstSongArtistEntity.setId(1L);
            firstSongArtistEntity.setStatus("Test song 1");
            SongArtistEntity secondSongArtistEntity = new SongArtistEntity();
            secondSongArtistEntity.setId(2L);
            secondSongArtistEntity.setStatus("Test song 2");

            List<SongArtistEntity> firstSongArtistList = new ArrayList<SongArtistEntity>();
            firstSongArtistList.add(firstSongArtistEntity);
            firstSongArtistList.add(secondSongArtistEntity);
            firstArtistEntity.setId(1L);
            firstArtistEntity.setName("Test 1");
            firstArtistEntity.setInformation("Test Information");
            firstArtistEntity.setSongArtists(firstSongArtistList);

            ArtistEntity secondArtistEntity = new ArtistEntity();
            PersonArtistEntity firstPersonArtistEntity = new PersonArtistEntity();
            firstPersonArtistEntity.setId(1L);
            firstPersonArtistEntity.setStatus("Test person 1");
            PersonArtistEntity secondPersonArtistEntity = new PersonArtistEntity();
            secondPersonArtistEntity.setId(2L);
            secondPersonArtistEntity.setStatus("Test person 2");
            List<PersonArtistEntity> firstPersonArtistList = new ArrayList<PersonArtistEntity>();
            firstPersonArtistList.add(firstPersonArtistEntity);
            firstPersonArtistList.add(secondPersonArtistEntity);
            secondArtistEntity.setId(2L);
            secondArtistEntity.setName("Test 2");
            secondArtistEntity.setInformation("Test Information");
            secondArtistEntity.setSongArtists(firstSongArtistList);

            entities.add(firstArtistEntity);
            entities.add(secondArtistEntity);

            PagedData<ArtistEntity> pagedEntities = new PagedData<>();
            pagedEntities.setRecords(entities);

            List<ArtistResponse> response = new ArrayList<>();

            ArtistResponse firstResponse = new ArtistResponse();
            Map<Long, String> firstSongArtistNamesMap = new HashMap<>();
            firstSongArtistNamesMap.put(1L, "Test song 1");
            firstSongArtistNamesMap.put(2L, "Test song 2");
            firstResponse.setId(1L);
            firstResponse.setName("Test 1");
            firstResponse.setInformation("Test Information");
            firstResponse.setSongArtistNames(firstSongArtistNamesMap);
            firstResponse.setPersonArtistNames(new HashMap<Long, String>());

            ArtistResponse secondResponse = new ArtistResponse();
            Map<Long, String> firstPersonArtistNamesMap = new HashMap<>();
            firstPersonArtistNamesMap.put(1L, "Test person 1");
            secondResponse.setId(2L);
            secondResponse.setName("Test 2");
            secondResponse.setInformation("Test Information");
            secondResponse.setPersonArtistNames(firstPersonArtistNamesMap);
            secondResponse.setSongArtistNames(new HashMap<Long, String>());

            response.add(firstResponse);
            response.add(secondResponse);

            PagedData<ArtistResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(artistDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntities);
            Mockito.when(personArtistDAO.personArtistEntityByArtist(2L)).thenReturn(firstPersonArtistNamesMap);
            Mockito.when(songArtistDAO.songArtistEntityByArtist(1L)).thenReturn(firstSongArtistNamesMap);

            List<ArtistResponse> artistFindResponse = artistService.find(filterRequest).getPayload();

            Assertions.assertThat(artistFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateArtist() {
        try {

            EntityRequest<ArtistCreateRequest> req = new EntityRequest<>();
            LocalDateTime dateOfBirth = LocalDateTime.of(2007, 12, 3, 0, 0);

            var newArtistRequest = new ArtistCreateRequest();
            newArtistRequest.setName("Test 1");
            newArtistRequest.setDateOfBirth(dateOfBirth);
            newArtistRequest.setDateOfDeath(null);
            newArtistRequest.setInformation("Test Information");
            newArtistRequest.setStatus("Test");
            newArtistRequest.setSurname("Test 1");
            newArtistRequest.setType("Test");

            req.setEntity(newArtistRequest);

            var newArtistEnt = new ArtistEntity();
            newArtistEnt.setName("Test 1");
            newArtistEnt.setDateOfBirth(dateOfBirth);
            newArtistEnt.setDateOfDeath(null);
            newArtistEnt.setInformation("Test Information");
            newArtistEnt.setStatus("Test");
            newArtistEnt.setSurname("Test 1");
            newArtistEnt.setType("Test");

            var newArtist = new ArtistResponse();
            newArtist.setName("Test 1");
            newArtist.setDateOfBirth(dateOfBirth);
            newArtist.setDateOfDeath(null);
            newArtist.setInformation("Test Information");
            newArtist.setStatus("Test");
            newArtist.setSurname("Test 1");
            newArtist.setType("Test");

            Mockito.when(artistDAO.persist(newArtistEnt)).thenReturn(null);

            PayloadResponse<ArtistResponse> genreCreateResponse = artistService.create(req);

            Assertions.assertThat(genreCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newArtist);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteArtist() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(artistDAO).removeByPK(req.getEntity());

            var artistResponse = artistService.delete(req);

            Assertions.assertThat(artistResponse.getPayload()).isEqualTo("Artist successfully deleted!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateArtist() {
        try {
            EntityRequest<ArtistUpdateRequest> request = new EntityRequest<>();
            LocalDateTime dateOfBirth = LocalDateTime.of(2007, 12, 3, 0, 0);
            ArtistEntity artistEntity = new ArtistEntity();
            artistEntity.setId(0L);
            artistEntity.setName("Test 1");
            artistEntity.setDateOfBirth(dateOfBirth);
            artistEntity.setDateOfDeath(null);
            artistEntity.setInformation("Test Information");
            artistEntity.setStatus("Test");
            artistEntity.setSurname("Test 1");
            artistEntity.setType("Test");

            ArtistResponse artistResponse = new ArtistResponse();
            artistResponse.setId(0L);
            artistResponse.setName("Test 2");

            ArtistUpdateRequest updateArtistRequest = new ArtistUpdateRequest();
            updateArtistRequest.setId(0L);
            updateArtistRequest.setName("Test 2");

            request.setEntity(updateArtistRequest);

            Mockito.when(artistValidation.validateUpdateArtistRequest(request)).thenReturn(null);

            Mockito.when(artistDAO.findByPK(artistEntity.getId())).thenReturn(artistEntity);

            Mockito.doNothing().when(artistDAO).merge(artistEntity);

            var artistUpdateRequest = artistService.update(request);
            Assertions.assertThat(artistUpdateRequest.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.\nExpected: %s\nActual: %s", artistResponse,
                            artistUpdateRequest.getPayload())
                    .usingRecursiveComparison().ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(artistResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
