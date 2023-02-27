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
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.AlbumService;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.core.impl.AlbumServiceImpl;
import ba.com.zira.sdr.core.mapper.AlbumMapper;
import ba.com.zira.sdr.core.mapper.SongArtistMapper;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.AlbumRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class AlbumServiceTest extends BasicTestConfiguration {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private SongArtistMapper songArtistMapper;

    @Autowired
    private SongMapper songMapper;

    private AlbumDAO albumDAO;
    private SongArtistDAO songArtistDAO;
    private RequestValidator requestValidator;
    private AlbumRequestValidation albumRequestValidation;
    private AlbumService albumService;
    private LookupService lookupService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.albumDAO = Mockito.mock(AlbumDAO.class);
        this.albumRequestValidation = Mockito.mock(AlbumRequestValidation.class);
        this.albumService = new AlbumServiceImpl(albumDAO, songArtistDAO, songArtistMapper, albumMapper, songMapper, albumRequestValidation,
                lookupService);
    }

    @Test(enabled = true)
    public void testFindAlbum() {
        try {

            List<AlbumEntity> entities = new ArrayList<>();
            AlbumEntity firstAlbumEntity = new AlbumEntity();
            firstAlbumEntity.setName("Test Album 1");
            firstAlbumEntity.setInformation("Information about album");
            firstAlbumEntity.setStatus(Status.ACTIVE.getValue());

            AlbumEntity secondAlbumEntity = new AlbumEntity();
            secondAlbumEntity.setName("Test Album 2");
            secondAlbumEntity.setInformation("Information about second album");
            secondAlbumEntity.setStatus(Status.INACTIVE.getValue());

            AlbumEntity thirdAlbumEntity = new AlbumEntity();
            thirdAlbumEntity.setName("Test Album 3");
            thirdAlbumEntity.setInformation("Information about third album");
            thirdAlbumEntity.setStatus(Status.ACTIVE.getValue());

            entities.add(firstAlbumEntity);
            entities.add(secondAlbumEntity);
            entities.add(thirdAlbumEntity);

            PagedData<AlbumEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<AlbumResponse> response = new ArrayList<>();

            AlbumResponse firstResponse = new AlbumResponse();
            firstResponse.setName("Test Album 1");
            firstResponse.setInformation("Information about album");
            firstResponse.setStatus(Status.ACTIVE.getValue());

            AlbumResponse secondResponse = new AlbumResponse();
            secondResponse.setName("Test Album 2");
            secondResponse.setInformation("Information about second album");
            secondResponse.setStatus(Status.INACTIVE.getValue());

            AlbumResponse thirdResponse = new AlbumResponse();
            thirdResponse.setName("Test Album 3");
            thirdResponse.setInformation("Information about third album");
            thirdResponse.setStatus(Status.ACTIVE.getValue());

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<AlbumResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(albumDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<AlbumResponse> albumFindResponse = albumService.find(filterRequest).getPayload();

            Assertions.assertThat(albumFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateAlbum() {
        try {

            EntityRequest<AlbumCreateRequest> req = new EntityRequest<>();

            var newAlbumRequest = new AlbumCreateRequest();
            newAlbumRequest.setInformation("Test Information");
            newAlbumRequest.setName("Test Album 1");
            newAlbumRequest.setStatus(Status.ACTIVE.getValue());
            newAlbumRequest.setDateOfRelease(LocalDateTime.parse("2023-02-21T15:29:11.696"));

            req.setEntity(newAlbumRequest);

            var albumEntity = new AlbumEntity();
            albumEntity.setName("Test Album 1");
            albumEntity.setInformation("Test Information");
            albumEntity.setStatus(Status.ACTIVE.getValue());
            albumEntity.setDateOfRelease(LocalDateTime.parse("2023-02-21T15:29:11.696"));

            var newAlbum = new AlbumResponse();
            newAlbum.setInformation("Test Information");
            newAlbum.setName("Test Album 1");
            newAlbum.setDateOfRelease(LocalDateTime.parse("2023-02-21T15:29:11.696"));
            newAlbum.setStatus(Status.ACTIVE.getValue());

            Mockito.when(albumDAO.persist(albumEntity)).thenReturn(null);

            PayloadResponse<AlbumResponse> albumFindResponse = albumService.create(req);

            Assertions.assertThat(albumFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newAlbum);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateAlbum() {
        try {

            EntityRequest<AlbumUpdateRequest> request = new EntityRequest<>();

            AlbumEntity albumEntity = new AlbumEntity();
            albumEntity.setName("Old Test Name 1");
            albumEntity.setInformation("Old Test Information");

            AlbumResponse albumResponse = new AlbumResponse();
            albumResponse.setName("Update Test Name 1");
            albumResponse.setInformation("Update Test Information");

            AlbumUpdateRequest updateAlbumRequest = new AlbumUpdateRequest();
            updateAlbumRequest.setName("Update Test Name 1");
            updateAlbumRequest.setInformation("Update Test Information");
            request.setEntity(updateAlbumRequest);

            Mockito.when(albumRequestValidation.validateUpdateAlbumRequest(request)).thenReturn(null);

            Mockito.when(albumDAO.findByPK(request.getEntity().getId())).thenReturn(albumEntity);

            Mockito.doNothing().when(albumDAO).merge(albumEntity);

            var countryUpdateResponse = albumService.update(request);
            Assertions.assertThat(countryUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(albumResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteAlbum() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(albumDAO).removeByPK(req.getEntity());

            var albumDeleteResponse = albumService.delete(req);

            Assertions.assertThat(albumDeleteResponse.getPayload()).isEqualTo("Album deleted successfully!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
