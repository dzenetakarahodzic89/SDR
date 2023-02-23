package ba.com.zira.sdr.test.suites;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.PersonArtistService;
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.api.model.personartist.PersonArtistResponse;
import ba.com.zira.sdr.core.impl.PersonArtistServiceImpl;
import ba.com.zira.sdr.core.mapper.PersonArtistMapper;
import ba.com.zira.sdr.core.validation.AlbumRequestValidation;
import ba.com.zira.sdr.core.validation.PersonArtistRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class PersonArtistServiceTest extends BasicTestConfiguration {

    @Autowired
    private PersonArtistMapper personArtistMapper;

    private AlbumDAO albumDAO;
    private PersonArtistDAO personArtistDAO;
    private ArtistDAO artistDAO;
    private PersonDAO personDAO;
    private PersonArtistRequestValidation personArtistRequestValidation;
    private RequestValidator requestValidator;
    private AlbumRequestValidation albumRequestValidation;
    private PersonArtistService personArtistService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.albumDAO = Mockito.mock(AlbumDAO.class);
        this.personArtistDAO = Mockito.mock(PersonArtistDAO.class);
        this.artistDAO = Mockito.mock(ArtistDAO.class);
        this.personDAO = Mockito.mock(PersonDAO.class);
        this.personArtistRequestValidation = Mockito.mock(PersonArtistRequestValidation.class);
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.albumRequestValidation = Mockito.mock(AlbumRequestValidation.class);
        this.albumRequestValidation = Mockito.mock(AlbumRequestValidation.class);
        this.personArtistService = new PersonArtistServiceImpl(personArtistDAO, artistDAO, personDAO, personArtistMapper,
                personArtistRequestValidation);
    }

    @Test(enabled = true)
    public void testFindPersonArtist() {
        try {

            List<PersonArtistEntity> entities = new ArrayList<>();
            PersonArtistEntity firstPersonArtistEntity = new PersonArtistEntity();
            firstPersonArtistEntity.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            firstPersonArtistEntity.setCreatedBy("1");
            firstPersonArtistEntity.setStatus(Status.ACTIVE.getValue());

            PersonArtistEntity secondPersonArtistEntity = new PersonArtistEntity();
            secondPersonArtistEntity.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            secondPersonArtistEntity.setCreatedBy("2");
            secondPersonArtistEntity.setStatus(Status.INACTIVE.getValue());

            PersonArtistEntity thirdPersonArtistEntity = new PersonArtistEntity();
            thirdPersonArtistEntity.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            thirdPersonArtistEntity.setCreatedBy("3");
            thirdPersonArtistEntity.setStatus(Status.INACTIVE.getValue());

            entities.add(firstPersonArtistEntity);
            entities.add(secondPersonArtistEntity);
            entities.add(thirdPersonArtistEntity);

            PagedData<PersonArtistEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<PersonArtistResponse> response = new ArrayList<>();

            PersonArtistResponse firstResponse = new PersonArtistResponse();
            firstResponse.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            firstResponse.setCreatedBy("1");
            firstResponse.setStatus(Status.ACTIVE.getValue());

            PersonArtistResponse secondResponse = new PersonArtistResponse();
            secondResponse.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            secondResponse.setCreatedBy("2");
            secondResponse.setStatus(Status.INACTIVE.getValue());

            PersonArtistResponse thirdResponse = new PersonArtistResponse();
            thirdResponse.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            thirdResponse.setCreatedBy("3");
            thirdResponse.setStatus(Status.INACTIVE.getValue());

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<PersonArtistResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(personArtistDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<PersonArtistResponse> personArtistFindResponse = personArtistService.get(filterRequest).getPayload();

            Assertions.assertThat(personArtistFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreatePersonArtist() {
        try {

            EntityRequest<PersonArtistCreateRequest> req = new EntityRequest<>();

            var newPersonArtistRequest = new PersonArtistCreateRequest();
            newPersonArtistRequest.setArtistId(1L);
            newPersonArtistRequest.setPersonId(1L);

            req.setEntity(newPersonArtistRequest);

            var personArtistEntity = new PersonArtistEntity();
            personArtistEntity.setArtist(
                    new ArtistEntity(1L, null, null, null, null, null, null, null, null, null, null, null, null, null));
            personArtistEntity.setPerson(
                    new PersonEntity(1L, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));

            var newPersonArtist = new PersonArtistResponse();
            newPersonArtist.setPersonId(1L);
            newPersonArtist.setArtistId(1L);
            Mockito.when(personDAO.findByPK(1L)).thenReturn(
                    new PersonEntity(1L, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
            Mockito.when(artistDAO.findByPK(1L))
                    .thenReturn(new ArtistEntity(1L, null, null, null, null, null, null, null, null, null, null, null, null, null));
            Mockito.when(personArtistDAO.persist(personArtistEntity)).thenReturn(null);

            PayloadResponse<PersonArtistResponse> personArtistCreateResponse = personArtistService.create(req);
            System.out.println(personArtistCreateResponse.getPayload());
            System.out.println(newPersonArtist);
            Assertions.assertThat(personArtistCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "status", "startOfRelaltionship", "endOfRelationship")
                    .isEqualTo(newPersonArtist);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeletePersonArtist() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(personArtistDAO).removeByPK(req.getEntity());

            var personArtistDeleteResponse = personArtistService.delete(req);

            Assertions.assertThat(personArtistDeleteResponse.getPayload()).isEqualTo("Deleted record successfully!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
