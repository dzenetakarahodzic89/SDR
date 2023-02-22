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
import ba.com.zira.sdr.api.GenreService;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.core.impl.GenreServiceImpl;
import ba.com.zira.sdr.core.mapper.GenreMapper;
import ba.com.zira.sdr.core.validation.GenreRequestValidation;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class GenreServiceTest extends BasicTestConfiguration {
    @Autowired
    private GenreMapper genreMapper;

    private GenreDAO genreDAO;
    private SongDAO songDAO;
    private RequestValidator requestValidator;
    private GenreRequestValidation genreRequestValidation;
    private GenreService genreService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.genreDAO = Mockito.mock(GenreDAO.class);
        this.songDAO = Mockito.mock(SongDAO.class);
        this.genreRequestValidation = Mockito.mock(GenreRequestValidation.class);
        this.genreService = new GenreServiceImpl(genreDAO, genreMapper, genreRequestValidation, songDAO);
    }

    @Test(enabled = true)
    public void testFindGenre() {
        try {
            List<GenreEntity> entities = new ArrayList<>();
            GenreEntity firstGenreEntity = new GenreEntity();
            firstGenreEntity.setId(1L);
            firstGenreEntity.setName("Test 1");
            firstGenreEntity.setInformation("Test Information");

            GenreEntity secondGenreEntity = new GenreEntity();
            secondGenreEntity.setId(2L);
            secondGenreEntity.setName("Test 2");
            secondGenreEntity.setInformation("Test Information");
            secondGenreEntity.setMainGenre(firstGenreEntity);

            GenreEntity thirdGenreEntity = new GenreEntity();
            SongEntity firstSongEntity = new SongEntity();
            firstSongEntity.setId(1L);
            firstSongEntity.setName("Test song 1");
            SongEntity secondSongEntity = new SongEntity();
            secondSongEntity.setId(2L);
            secondSongEntity.setName("Test song 2");
            List<SongEntity> firstSongList = new ArrayList<SongEntity>();
            firstSongList.add(firstSongEntity);
            firstSongList.add(secondSongEntity);
            thirdGenreEntity.setId(3L);
            thirdGenreEntity.setName("Test 3");
            thirdGenreEntity.setInformation("Test Information");
            thirdGenreEntity.setSongs(firstSongList);

            GenreEntity fourthGenreEntity = new GenreEntity();
            SongEntity thirdSongEntity = new SongEntity();
            thirdSongEntity.setId(3L);
            thirdSongEntity.setName("Test song 3");
            List<SongEntity> secondSongList = new ArrayList<SongEntity>();
            secondSongList.add(thirdSongEntity);
            fourthGenreEntity.setId(4L);
            fourthGenreEntity.setName("Test 4");
            fourthGenreEntity.setInformation("Test Information");
            fourthGenreEntity.setMainGenre(secondGenreEntity);
            fourthGenreEntity.setSongs(secondSongList);

            List<GenreEntity> firstSubGenreList = new ArrayList<>();
            firstSubGenreList.add(fourthGenreEntity);
            secondGenreEntity.setSubGenres(firstSubGenreList);
            List<GenreEntity> secondSubGenreList = new ArrayList<>();
            secondSubGenreList.add(secondGenreEntity);
            firstGenreEntity.setSubGenres(secondSubGenreList);

            entities.add(firstGenreEntity);
            entities.add(secondGenreEntity);
            entities.add(thirdGenreEntity);
            entities.add(fourthGenreEntity);

            PagedData<GenreEntity> pagedEntities = new PagedData<>();
            pagedEntities.setRecords(entities);

            List<Genre> response = new ArrayList<>();

            Genre firstResponse = new Genre();
            Map<Long, String> firstSubGenreNamesMap = new HashMap<>();
            firstSubGenreNamesMap.put(2L, "Test 2");
            firstResponse.setId(1L);
            firstResponse.setName("Test 1");
            firstResponse.setInformation("Test Information");
            firstResponse.setSubGenreNames(firstSubGenreNamesMap);
            firstResponse.setSongNames(new HashMap<Long, String>());

            Genre secondResponse = new Genre();
            Map<Long, String> secondSubGenreNamesMap = new HashMap<>();
            secondSubGenreNamesMap.put(4L, "Test 4");
            Genre firstMainGenre = new Genre();
            firstMainGenre.setId(1L);
            firstMainGenre.setName("Test 1");
            firstMainGenre.setInformation("Test Information");
            secondResponse.setId(2L);
            secondResponse.setName("Test 2");
            secondResponse.setInformation("Test Information");
            secondResponse.setMainGenre(firstMainGenre);
            secondResponse.setSubGenreNames(secondSubGenreNamesMap);
            secondResponse.setSongNames(new HashMap<Long, String>());

            Genre thirdResponse = new Genre();
            Map<Long, String> firstSongNamesMap = new HashMap<>();
            firstSongNamesMap.put(1L, "Test song 1");
            firstSongNamesMap.put(2L, "Test song 2");
            thirdResponse.setId(3L);
            thirdResponse.setName("Test 3");
            thirdResponse.setInformation("Test Information");
            thirdResponse.setSongNames(firstSongNamesMap);
            thirdResponse.setSubGenreNames(new HashMap<Long, String>());

            Genre fourthResponse = new Genre();
            Map<Long, String> secondSongNamesMap = new HashMap<>();
            secondSongNamesMap.put(3L, "Test song 3");
            Genre secondMainGenre = new Genre();
            secondMainGenre.setId(2L);
            secondMainGenre.setName("Test 2");
            secondMainGenre.setInformation("Test Information");
            secondMainGenre.setMainGenre(firstMainGenre);
            fourthResponse.setId(4L);
            fourthResponse.setName("Test 4");
            fourthResponse.setInformation("Test Information");
            fourthResponse.setSongNames(secondSongNamesMap);
            fourthResponse.setSubGenreNames(new HashMap<Long, String>());
            fourthResponse.setMainGenre(secondMainGenre);

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);
            response.add(fourthResponse);

            PagedData<Genre> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(genreDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntities);
            Mockito.when(genreDAO.subGenresByMainGenre(1L)).thenReturn(firstSubGenreNamesMap);
            Mockito.when(genreDAO.subGenresByMainGenre(2L)).thenReturn(secondSubGenreNamesMap);
            Mockito.when(songDAO.songsByGenre(3L)).thenReturn(firstSongNamesMap);
            Mockito.when(songDAO.songsByGenre(4L)).thenReturn(secondSongNamesMap);

            List<Genre> genreFindResponse = genreService.find(filterRequest).getPayload();

            Assertions.assertThat(genreFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateGenre() {
        try {

            EntityRequest<GenreCreateRequest> req = new EntityRequest<>();

            var newGenreRequest = new GenreCreateRequest();
            newGenreRequest.setName("Test 1");
            newGenreRequest.setType("GENRE");
            newGenreRequest.setInformation("Test Information");
            newGenreRequest.setMainGenreId(1L);

            req.setEntity(newGenreRequest);

            var mainGenreEnt = new GenreEntity();
            mainGenreEnt.setId(1L);
            mainGenreEnt.setName("Main genre");
            var newGenreEnt = new GenreEntity();
            newGenreEnt.setName("Test 1");
            newGenreEnt.setType("GENRE");
            newGenreEnt.setInformation("Test Information");
            newGenreEnt.setMainGenre(mainGenreEnt);

            var mainGenre = new Genre();
            mainGenre.setId(1L);
            mainGenre.setName("Main genre");
            var newGenre = new Genre();
            newGenre.setName("Test 1");
            newGenre.setType("GENRE");
            newGenre.setInformation("Test Information");
            newGenre.setStatus(Status.ACTIVE.getValue());
            newGenre.setMainGenre(mainGenre);

            Mockito.when(genreDAO.persist(newGenreEnt)).thenReturn(null);
            Mockito.when(genreDAO.findByPK(1L)).thenReturn(mainGenreEnt);

            PayloadResponse<Genre> genreCreateResponse = genreService.create(req);

            Assertions.assertThat(genreCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newGenre);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateGenre() {
        try {

            EntityRequest<GenreUpdateRequest> request = new EntityRequest<>();

            GenreEntity genreEntity = new GenreEntity();
            genreEntity.setName("Old Test Name 1");
            genreEntity.setType("GENRE");
            genreEntity.setInformation("Old Test Information");

            Genre genreResponse = new Genre();
            Genre mainGenre = new Genre();
            mainGenre.setId(1L);
            mainGenre.setName("Main genre");
            genreResponse.setName("Update Test Name 1");
            genreResponse.setInformation("Update Test Information");
            genreResponse.setType("GENRE");
            genreResponse.setMainGenre(mainGenre);

            GenreUpdateRequest updateGenreRequest = new GenreUpdateRequest();
            updateGenreRequest.setName("Update Test Name 1");
            updateGenreRequest.setInformation("Update Test Information");
            updateGenreRequest.setType("GENRE");
            updateGenreRequest.setMainGenreId(1L);
            request.setEntity(updateGenreRequest);

            GenreEntity mainGenreEnt = new GenreEntity();
            mainGenreEnt.setId(1L);
            mainGenreEnt.setName("Main genre");

            Mockito.when(genreRequestValidation.validateGenreUpdateRequest(request)).thenReturn(null);

            Mockito.when(genreDAO.findByPK(request.getEntity().getId())).thenReturn(genreEntity);
            Mockito.when(genreDAO.findByPK(1L)).thenReturn(mainGenreEnt);

            Mockito.doNothing().when(genreDAO).merge(genreEntity);

            var genreUpdateResponse = genreService.update(request);

            Assertions.assertThat(genreUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(genreResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteGenre() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(genreDAO).removeByPK(req.getEntity());

            var genreDeleteResponse = genreService.delete(req);

            Assertions.assertThat(genreDeleteResponse.getPayload()).isEqualTo("Genre successfully deleted!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
