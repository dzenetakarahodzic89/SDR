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
import ba.com.zira.sdr.api.PersonService;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.core.impl.PersonServiceImpl;
import ba.com.zira.sdr.core.mapper.PersonMapper;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class PersonServiceTest extends BasicTestConfiguration {

//    @Autowired
//    private PersonMapper personMapper;
//
//    private PersonDAO personDAO;
//    private RequestValidator requestValidator;
//    private PersonRequestValidation personRequestValidation;
//    private PersonService personService;
//
//    @BeforeMethod
//    public void beforeMethod() throws ApiException {
//        this.requestValidator = Mockito.mock(RequestValidator.class);
//        this.personDAO = Mockito.mock(PersonDAO.class);
//        this.personRequestValidation = Mockito.mock(PersonRequestValidation.class);
//        this.personService = new PersonServiceImpl(personDAO, personMapper, personRequestValidation,null,null);
//    }

//    @Test(enabled = true)
//    public void testFindPerson() {
//        try {
//
//            List<PersonEntity> entities = new ArrayList<>();
//
//            PersonEntity firstPersonEntity = new PersonEntity();
//            firstPersonEntity.setId(1L);
//            firstPersonEntity.setName("test person name 1");
//            firstPersonEntity.setSurname("test person surname 1");
//            firstPersonEntity.setGender("male");
//            firstPersonEntity.setStatus(Status.ACTIVE.getValue());
//            firstPersonEntity.setInformation("test male gendered person 1");
//            firstPersonEntity.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
//            firstPersonEntity.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
//
//            /*firstPersonEntity.setInformation("test male gendered person 1");
//            firstPersonEntity.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
//            firstPersonEntity.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
//            secondPersonEntity.setInformation("test male gendered person 2");
//            thirdPersonEntity.setInformation("test male gendered person 3");
//            firstSongFttResultEntity.setStatus(Status.ACTIVE.getValue());
//
//             */
//
//            PersonEntity secondPersonEntity = new PersonEntity();
//            secondPersonEntity.setId(2L);
//            secondPersonEntity.setName("test person name 2");
//            secondPersonEntity.setSurname("test person surname 2");
//            secondPersonEntity.setGender("male");
//            secondPersonEntity.setStatus(Status.ACTIVE.getValue());
//            secondPersonEntity.setInformation("test male gendered person 2");
//            secondPersonEntity.setDateOfBirth(LocalDateTime.parse("2008-12-03T10:15:30"));
//            secondPersonEntity.setDateOfDeath(LocalDateTime.parse("2018-12-03T10:15:30"));
//
//
//
//            PersonEntity thirdPersonEntity = new PersonEntity();
//            thirdPersonEntity.setId(3L);
//            thirdPersonEntity.setName("test person name 3");
//            thirdPersonEntity.setSurname("test person surname 3");
//            thirdPersonEntity.setGender("male");
//            thirdPersonEntity.setStatus(Status.ACTIVE.getValue());
//            thirdPersonEntity.setInformation("test male gendered person 3");
//            thirdPersonEntity.setDateOfBirth(LocalDateTime.parse("2009-12-03T10:15:30"));
//            thirdPersonEntity.setDateOfDeath(LocalDateTime.parse("2019-12-03T10:15:30"));
//
//
//
//
//            entities.add(firstPersonEntity);
//            entities.add(secondPersonEntity);
//            entities.add(thirdPersonEntity);
//
//            PagedData<PersonEntity> pagedEntites = new PagedData<>();
//            pagedEntites.setRecords(entities);
//
//            List<PersonResponse> response = new ArrayList<>();
//
//            PersonResponse firstResponse = new PersonResponse();
//            firstResponse.setId(1L);
//            firstResponse.setPersonName("test person name 1");
//            firstResponse.setPersonSurname("test person surname 1");
//            firstResponse.setPersonGender("male");
//            firstResponse.setPersonStatus(Status.ACTIVE.getValue());
//            firstResponse.setPersonInformation("test male gendered person 1");
//            firstResponse.setPersonDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
//            firstResponse.setPersonDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
//
//            PersonResponse secondResponse = new PersonResponse();
//            secondResponse.setId(2L);
//            secondResponse.setPersonName("test person name 2");
//            secondResponse.setPersonSurname("test person surname 2");
//            secondResponse.setPersonGender("male");
//            secondResponse.setPersonStatus(Status.ACTIVE.getValue());
//            secondResponse.setPersonInformation("test male gendered person 2");
//            secondResponse.setPersonDateOfBirth(LocalDateTime.parse("2008-12-03T10:15:30"));
//            secondResponse.setPersonDateOfDeath(LocalDateTime.parse("2018-12-03T10:15:30"));
//
//
//            PersonResponse thirdResponse = new PersonResponse();
//            thirdResponse.setId(3L);
//            thirdResponse.setPersonName("test person name 3");
//            thirdResponse.setPersonSurname("test person surname 3");
//            thirdResponse.setPersonGender("male");
//            thirdResponse.setPersonStatus(Status.ACTIVE.getValue());
//            thirdResponse.setPersonInformation("test male gendered person 3");
//            thirdResponse.setPersonDateOfBirth(LocalDateTime.parse("2009-12-03T10:15:30"));
//            thirdResponse.setPersonDateOfDeath(LocalDateTime.parse("2019-12-03T10:15:30"));
//
//
//            response.add(firstResponse);
//            response.add(secondResponse);
//            response.add(thirdResponse);
//
//            PagedData<PersonResponse> pagedResponse = new PagedData<>();
//            pagedResponse.setRecords(response);
//
//            Map<String, Object> filterCriteria = new HashMap<String, Object>();
//            QueryConditionPage queryConditionPage = new QueryConditionPage();
//            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);
//
//            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
//            Mockito.when(personDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);
//
//
//            List<PersonResponse> personFindResponse = personService.find(filterRequest).getPayload();
//
//            Assertions.assertThat(personFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
//            .hasSameElementsAs(response);
//
//        } catch (Exception e) {
//            Assert.fail();
//        }
//    }
//    @Test(enabled = true)
//    public void testCreatePerson() {
//        try {
//
//            EntityRequest<PersonCreateRequest> req = new EntityRequest<>();
//
//            var newPersonRequest = new PersonCreateRequest();
//            newPersonRequest.setPersonName("Test person name 1");
//            newPersonRequest.setPersonSurname("Test person surname 1");
//            newPersonRequest.setPersonGender("male");
//            newPersonRequest.setPersonInformation("Test information 1");
//            newPersonRequest.setPersonDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
//            newPersonRequest.setPersonDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
//
//
//            req.setEntity(newPersonRequest);
//
//            var newPersonEnt = new PersonEntity();
//            newPersonEnt.setName("Test person name 1");
//            newPersonEnt.setSurname("Test person surname 1");
//            newPersonEnt.setGender("male");
//            newPersonEnt.setInformation("Test information 1");
//            newPersonEnt.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
//            newPersonEnt.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
//
//
//            var newPerson = new PersonResponse();
//            newPerson.setPersonName("Test person name 1");
//            newPerson.setPersonSurname("Test person surname 1");
//            newPerson.setPersonGender("male");
//            newPerson.setPersonInformation("Test information 1");
//            newPerson.setPersonDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
//            newPerson.setPersonDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
//
//            Mockito.when(personDAO.persist(newPersonEnt)).thenReturn(null);
//
//            PayloadResponse<PersonResponse> personFindResponse = personService.create(req);
//
//            Assertions.assertThat(personFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
//            .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl").isEqualTo(newPerson);
//
//
//        } catch (Exception e) {
//            Assert.fail();
//        }
//    }
//    @Test(enabled = true)
//    public void testUpdatePerson() {
//        try {
//
//            EntityRequest<PersonUpdateRequest> request = new EntityRequest<>();
//
//            PersonEntity personEntity = new PersonEntity();
//            personEntity.setId(11L);
//            personEntity.setName("old test person name 1");
//            personEntity.setSurname("old test person surname 1");
//            personEntity.setGender("male");
//            personEntity.setInformation("old test male gendered person 1");
//            personEntity.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:30"));
//            personEntity.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:30"));
//
//            PersonResponse personResponse = new PersonResponse();
//            personResponse.setPersonName("update test person name 1");
//            personResponse.setPersonSurname("update Test person surname 1");
//            personResponse.setPersonGender("male");
//            personResponse.setPersonInformation("update Test information 1");
//            personResponse.setPersonDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:31"));
//            personResponse.setPersonDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:31"));
//            //provjeriti
//            PersonUpdateRequest personUpdateRequest = new PersonUpdateRequest();
//            personUpdateRequest.setId(11L);
//            personUpdateRequest.setPersonName("update test person name 1");
//            personEntity.setSurname("update Test person surname 1");
//            personEntity.setGender("male");
//            personEntity.setInformation("update Test information 1");
//            personEntity.setDateOfBirth(LocalDateTime.parse("2007-12-03T10:15:31"));
//            personEntity.setDateOfDeath(LocalDateTime.parse("2017-12-03T10:15:31"));
//            request.setEntity(personUpdateRequest);
//
//            Mockito.when(personRequestValidation.validateUpdatePersonRequest(request)).thenReturn(null);
//
//            Mockito.when(personDAO.findByPK(request.getEntity().getId())).thenReturn(personEntity);
//
//            Mockito.doNothing().when(personDAO).merge(personEntity);
//
//            var personUpdateResponse = personService.update(request);
//            Assertions.assertThat(personUpdateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
//            .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(personResponse);
//
//        } catch (Exception e) {
//            Assert.fail();
//        }
//    }
//    @Test(enabled = true)
//    public void testDeletePerson() {
//        try {
//            var req = new EntityRequest<Long>();
//
//            req.setEntity(1L);
//
//            Mockito.when(requestValidator.validate(req)).thenReturn(null);
//
//            Mockito.doNothing().when(personDAO).removeByPK(req.getEntity());
//
//            var personFindResponse = personService.delete(req);
//
//            Assertions.assertThat(personFindResponse.getPayload()).isEqualTo("Person with id 1 is successfully deleted!");
//
//        } catch (Exception e) {
//            Assert.fail();
//        }
//    }
//
//
}
