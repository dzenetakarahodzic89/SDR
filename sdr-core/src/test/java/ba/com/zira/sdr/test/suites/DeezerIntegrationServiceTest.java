package ba.com.zira.sdr.test.suites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
import ba.com.zira.sdr.api.DeezerIntegrationService;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.core.impl.DeezerIntegrationServiceImpl;
import ba.com.zira.sdr.core.mapper.DeezerIntegrationMapper;
import ba.com.zira.sdr.core.validation.DeezerIntegrationRequestValidation;
import ba.com.zira.sdr.dao.DeezerIntegrationDAO;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class DeezerIntegrationServiceTest extends BasicTestConfiguration {
    @Autowired
    private DeezerIntegrationMapper deezerIntegrationMapper;
    private DeezerIntegrationDAO deezerIntegrationDAO;
    private RequestValidator requestValidator;
    private DeezerIntegrationRequestValidation deezerIntegrationRequestValidation;
    private DeezerIntegrationService deezerIntegrationService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.deezerIntegrationDAO = Mockito.mock(DeezerIntegrationDAO.class);
        this.deezerIntegrationRequestValidation = Mockito.mock(DeezerIntegrationRequestValidation.class);
        this.deezerIntegrationService = new DeezerIntegrationServiceImpl(deezerIntegrationDAO, deezerIntegrationMapper,
                deezerIntegrationRequestValidation, null, null);
    }

    @Test(enabled = true)
    public void testFindDeezerIntegration() {
        try {

            List<DeezerIntegrationEntity> entities = new ArrayList<>();

            DeezerIntegrationEntity firstDeezerIntegrationEntity = new DeezerIntegrationEntity();
            firstDeezerIntegrationEntity.setRequest("Test Request 1");
            firstDeezerIntegrationEntity.setName("Test Name 1");
            firstDeezerIntegrationEntity.setResponse("Test Response 1");

            DeezerIntegrationEntity secondDeezerIntegrationEntity = new DeezerIntegrationEntity();
            secondDeezerIntegrationEntity.setRequest("Test Request 2");
            secondDeezerIntegrationEntity.setName("Test Name 2");
            secondDeezerIntegrationEntity.setResponse("Test Response 2");

            DeezerIntegrationEntity thirdDeezerIntegrationEntity = new DeezerIntegrationEntity();
            thirdDeezerIntegrationEntity.setRequest("Test Request 3");
            thirdDeezerIntegrationEntity.setName("Test Name 3");
            thirdDeezerIntegrationEntity.setResponse("Test Response 3");

            entities.add(firstDeezerIntegrationEntity);
            entities.add(secondDeezerIntegrationEntity);
            entities.add(thirdDeezerIntegrationEntity);

            PagedData<DeezerIntegrationEntity> pagedEntities = new PagedData<>();
            pagedEntities.setRecords(entities);
            List<DeezerIntegration> response = new ArrayList<>();

            DeezerIntegration firstResponse = new DeezerIntegration();
            firstResponse.setRequest("Test Request 1");
            firstResponse.setName("Test Name 1");
            firstResponse.setResponse("Test Response 1");

            DeezerIntegration secondResponse = new DeezerIntegration();
            secondResponse.setRequest("Test Request 2");
            secondResponse.setName("Test Name 2");
            secondResponse.setResponse("Test Response 2");

            DeezerIntegration thirdResponse = new DeezerIntegration();
            thirdResponse.setRequest("Test Request 3");
            thirdResponse.setName("Test Name 3");
            thirdResponse.setResponse("Test Response 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<DeezerIntegration> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);
            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);
            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(deezerIntegrationDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntities);
            List<DeezerIntegration> deezerIntegrationFindResponse = deezerIntegrationService.find(filterRequest).getPayload();
            Assertions.assertThat(deezerIntegrationFindResponse).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal.").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateDeezerIntegration() {
        try {

            EntityRequest<DeezerIntegrationCreateRequest> req = new EntityRequest<>();

            var newDeezerIntegrationRequest = new DeezerIntegrationCreateRequest();
            newDeezerIntegrationRequest.setRequest("Test information");
            newDeezerIntegrationRequest.setName("Test type 1");
            newDeezerIntegrationRequest.setResponse("Test response 1");
            req.setEntity(newDeezerIntegrationRequest);

            var newDeezerIntegrationEnt = new DeezerIntegrationEntity();
            newDeezerIntegrationEnt.setRequest("Test information");
            newDeezerIntegrationEnt.setName("Test type 1");
            newDeezerIntegrationEnt.setResponse("Test response 1");

            var newDeezerIntegration = new DeezerIntegration();
            newDeezerIntegration.setRequest("Test information");
            newDeezerIntegration.setName("Test type 1");
            newDeezerIntegration.setResponse("Test response 1");
            newDeezerIntegration.setStatus(Status.ACTIVE.getValue());

            Mockito.when(deezerIntegrationDAO.persist(newDeezerIntegrationEnt)).thenReturn(null);
            PayloadResponse<DeezerIntegration> DeezerIntegrationFindResponse = deezerIntegrationService.create(req);
            Assertions.assertThat(DeezerIntegrationFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("id", "created", "createdBy", "modified", "modifiedBy").isEqualTo(newDeezerIntegration);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateDeezerIntegration() {
        try {
            EntityRequest<DeezerIntegrationUpdateRequest> request = new EntityRequest<>();
            DeezerIntegrationEntity deezerIntegrationEntity = new DeezerIntegrationEntity();
            deezerIntegrationEntity.setRequest("Old Test Information");
            deezerIntegrationEntity.setName("Old Test Name");
            deezerIntegrationEntity.setResponse("Old Test Information");
            DeezerIntegration deezerIntegration = new DeezerIntegration();
            deezerIntegration.setRequest("Update Test Information");
            deezerIntegration.setName("Update Test Name");
            deezerIntegration.setResponse("Update Test Information");
            DeezerIntegrationUpdateRequest updateDeezerIntegrationRequest = new DeezerIntegrationUpdateRequest();
            updateDeezerIntegrationRequest.setRequest("Update Test Information");
            updateDeezerIntegrationRequest.setName("Update Test Name");
            updateDeezerIntegrationRequest.setResponse("Update Test Information");
            request.setEntity(updateDeezerIntegrationRequest);
            Mockito.when(deezerIntegrationRequestValidation.validateUpdateDeezerIntegrationRequest(request)).thenReturn(null);
            Mockito.when(deezerIntegrationDAO.findByPK(request.getEntity().getId())).thenReturn(deezerIntegrationEntity);
            Mockito.doNothing().when(deezerIntegrationDAO).merge(deezerIntegrationEntity);
            var deezerIntegrationUpdate = deezerIntegrationService.update(request);
            Assertions.assertThat(deezerIntegrationUpdate.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(deezerIntegration);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteDeezerIntegration() {
        try {

            var req = new EntityRequest<String>();

            req.setEntity(UUID.randomUUID().toString());
            Mockito.when(requestValidator.validate(req)).thenReturn(null);
            Mockito.doNothing().when(deezerIntegrationDAO).removeByPK(req.getEntity());
            var deezerIntegrationFindResponse = deezerIntegrationService.delete(req);
            Assertions.assertThat(deezerIntegrationFindResponse.getPayload()).isEqualTo("Deezer integration is removed.");

        } catch (Exception e) {
            Assert.fail();
        }
    }
}