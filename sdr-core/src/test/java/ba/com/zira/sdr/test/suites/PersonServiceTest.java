package ba.com.zira.sdr.test.suites;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
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
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.PersonService;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.core.impl.PersonServiceImpl;
import ba.com.zira.sdr.core.mapper.PersonMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.dao.model.CountryEntity;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class PersonServiceTest extends BasicTestConfiguration {

    @Autowired
    private PersonMapper personMapper;

    private ArtistDAO artistDAO;
    private AlbumDAO albumDAO;
    private SongDAO songDAO;
    private SongInstrumentDAO songInstrumentDAO;
    private ConnectedMediaDAO connectedMediaDAO;

    private PersonDAO personDAO;
    private CountryDAO countryDAO;
    private RequestValidator requestValidator;
    private PersonRequestValidation personRequestValidation;
    private PersonService personService;
    private LookupService lookupService;
    private MediaService mediaService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.personDAO = Mockito.mock(PersonDAO.class);
        this.countryDAO = Mockito.mock(CountryDAO.class);
        this.lookupService = Mockito.mock(LookupService.class);
        this.mediaService = Mockito.mock(MediaService.class);
        this.personRequestValidation = Mockito.mock(PersonRequestValidation.class);
        this.lookupService = Mockito.mock(LookupService.class);
        this.mediaService = Mockito.mock(MediaService.class);
        this.personService = new PersonServiceImpl(personDAO, artistDAO, albumDAO, songDAO, songInstrumentDAO, countryDAO,
                connectedMediaDAO, personMapper, personRequestValidation, lookupService, mediaService);
    }

    @Test(enabled = true)
    public void testFindPerson() {
        try {

            List<PersonEntity> entities = new ArrayList<>();

            PersonEntity firstPersonEntity = new PersonEntity();
            firstPersonEntity.setId(1L);
            firstPersonEntity.setName("test person name 1");
            firstPersonEntity.setSurname("test person surname 1");
            firstPersonEntity.setGender("male");
            firstPersonEntity.setStatus(Status.ACTIVE.getValue());
            firstPersonEntity.setInformation("test male gendered person 1");
            firstPersonEntity.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
            firstPersonEntity.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));

            PersonEntity secondPersonEntity = new PersonEntity();
            secondPersonEntity.setId(2L);
            secondPersonEntity.setName("test person name 2");
            secondPersonEntity.setSurname("test person surname 2");
            secondPersonEntity.setGender("male");
            secondPersonEntity.setStatus(Status.ACTIVE.getValue());
            secondPersonEntity.setInformation("test male gendered person 2");
            secondPersonEntity.setDateOfBirth(LocalDateTime.parse("2008-12-03T10:15:30"));
            secondPersonEntity.setDateOfDeath(LocalDateTime.parse("2018-12-03T10:15:30"));

            PersonEntity thirdPersonEntity = new PersonEntity();
            thirdPersonEntity.setId(3L);
            thirdPersonEntity.setName("test person name 3");
            thirdPersonEntity.setSurname("test person surname 3");
            thirdPersonEntity.setGender("male");
            thirdPersonEntity.setStatus(Status.ACTIVE.getValue());
            thirdPersonEntity.setInformation("test male gendered person 3");
            thirdPersonEntity.setDateOfBirth(LocalDateTime.parse("2009-12-03T10:15:30"));
            thirdPersonEntity.setDateOfDeath(LocalDateTime.parse("2019-12-03T10:15:30"));

            entities.add(firstPersonEntity);
            entities.add(secondPersonEntity);
            entities.add(thirdPersonEntity);

            PagedData<PersonEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<PersonResponse> response = new ArrayList<>();

            PersonResponse firstResponse = new PersonResponse();
            firstResponse.setId(1L);
            firstResponse.setName("test person name 1");
            firstResponse.setSurname("test person surname 1");
            firstResponse.setGender("male");
            firstResponse.setStatus(Status.ACTIVE.getValue());
            firstResponse.setInformation("test male gendered person 1");
            firstResponse.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
            firstResponse.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
            firstResponse.setFullName(firstResponse.getName() + ' ' + firstResponse.getSurname());

            PersonResponse secondResponse = new PersonResponse();
            secondResponse.setId(2L);
            secondResponse.setName("test person name 2");
            secondResponse.setSurname("test person surname 2");
            secondResponse.setGender("male");
            secondResponse.setStatus(Status.ACTIVE.getValue());
            secondResponse.setInformation("test male gendered person 2");
            secondResponse.setDateOfBirth(LocalDateTime.parse("2008-12-03T10:15:30"));
            secondResponse.setDateOfDeath(LocalDateTime.parse("2018-12-03T10:15:30"));
            secondResponse.setFullName(secondResponse.getName() + ' ' + secondResponse.getSurname());

            PersonResponse thirdResponse = new PersonResponse();
            thirdResponse.setId(3L);
            thirdResponse.setName("test person name 3");
            thirdResponse.setSurname("test person surname 3");
            thirdResponse.setGender("male");
            thirdResponse.setStatus(Status.ACTIVE.getValue());
            thirdResponse.setInformation("test male gendered person 3");
            thirdResponse.setDateOfBirth(LocalDateTime.parse("2009-12-03T10:15:30"));
            thirdResponse.setDateOfDeath(LocalDateTime.parse("2019-12-03T10:15:30"));
            thirdResponse.setFullName(thirdResponse.getName() + ' ' + thirdResponse.getSurname());

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<PersonResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(personDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<PersonResponse> personFindResponse = personService.find(filterRequest).getPayload();

            Assertions.assertThat(personFindResponse).as("Check all elements").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreatePerson() {
        try {

            EntityRequest<PersonCreateRequest> req = new EntityRequest<>();

            var newPersonRequest = new PersonCreateRequest();
            newPersonRequest.setName("Test person name 1");
            newPersonRequest.setSurname("Test person surname 1");
            newPersonRequest.setGender("male");
            newPersonRequest.setInformation("Test information 1");
            newPersonRequest.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
            newPersonRequest.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));

            req.setEntity(newPersonRequest);

            var newPersonEnt = new PersonEntity();
            newPersonEnt.setName("Test person name 1");
            newPersonEnt.setSurname("Test person surname 1");
            newPersonEnt.setGender("male");
            newPersonEnt.setInformation("Test information 1");
            newPersonEnt.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
            newPersonEnt.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));

            var newPerson = new PersonResponse();
            newPerson.setName("Test person name 1");
            newPerson.setSurname("Test person surname 1");
            newPerson.setGender("male");
            newPerson.setInformation("Test information 1");
            newPerson.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
            newPerson.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
            newPerson.setFullName(newPerson.getName() + " " + newPerson.getSurname());
            newPerson.setStatus(Status.ACTIVE.getValue());

            Mockito.when(personDAO.persist(newPersonEnt)).thenReturn(null);
            Mockito.when(countryDAO.findByPK(req.getEntity().getCountryId())).thenReturn(new CountryEntity());

            PayloadResponse<PersonResponse> personFindResponse = personService.create(req);

            Assertions.assertThat(personFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl").isEqualTo(newPerson);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdatePerson() {
        try {
            EntityRequest<PersonUpdateRequest> request = new EntityRequest<>();

            PersonEntity personEntity = new PersonEntity();
            personEntity.setId(0L);
            personEntity.setName("old test person name 1");
            personEntity.setSurname("old test person surname 1");
            personEntity.setGender("male");
            personEntity.setInformation("old test male gendered person 1");
            personEntity.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
            personEntity.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));

            PersonResponse personResponse = new PersonResponse();
            personResponse.setId(0L);
            personResponse.setName("update test person name 1");
            personResponse.setSurname("update Test person surname 1");
            personResponse.setGender("male");
            personResponse.setInformation("update Test information 1");
            personResponse.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:31"));
            personResponse.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:31"));
            personResponse.setFullName(personResponse.getName() + ' ' + personResponse.getSurname());

            PersonUpdateRequest personUpdateRequest = new PersonUpdateRequest();
            personUpdateRequest.setId(0L);
            personUpdateRequest.setName("update test person name 1");
            personUpdateRequest.setSurname("update Test person surname 1");
            personUpdateRequest.setGender("male");
            personUpdateRequest.setInformation("update Test information 1");
            personUpdateRequest.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:31"));
            personUpdateRequest.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:31"));
            personUpdateRequest.setCountryId(1L); // Set a non-null value for
                                                  // countryId
            request.setEntity(personUpdateRequest);

            Mockito.when(personRequestValidation.validateUpdatePersonRequest(request)).thenReturn(null);

            Mockito.when(personDAO.findByPK(request.getEntity().getId())).thenReturn(personEntity);

            Mockito.when(countryDAO.findByPK(request.getEntity().getCountryId())).thenReturn(new CountryEntity());

            Mockito.doNothing().when(personDAO).merge(personEntity);

            var personUpdateResponse = personService.update(request);
            Assertions.assertThat(personUpdateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("information", "dateOfBirth", "dateOfDeath", "created",
                            "createdBy", "modified", "modifiedBy")
                    .isEqualTo(personResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeletePerson() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(personDAO).removeByPK(req.getEntity());

            var personFindResponse = personService.delete(req);

            Assertions.assertThat(personFindResponse.getPayload()).isEqualTo("Person with id 1 is successfully deleted!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
