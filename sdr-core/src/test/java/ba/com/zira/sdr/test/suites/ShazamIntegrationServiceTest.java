package ba.com.zira.sdr.test.suites;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.ShazamIntegrationService;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationResponse;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationUpdateRequest;
import ba.com.zira.sdr.core.impl.ShazamIntegrationServiceImpl;
import ba.com.zira.sdr.core.mapper.ShazamIntegrationMapper;
import ba.com.zira.sdr.core.validation.ShazamIntegrationRequestValidation;
import ba.com.zira.sdr.dao.ShazamIntegrationDAO;
import ba.com.zira.sdr.dao.model.ShazaamIntegrationEntity;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class ShazamIntegrationServiceTest extends BasicTestConfiguration {
    @Autowired
    private ShazamIntegrationMapper shazamIntegrationMapper;
    private ShazamIntegrationDAO shazamIntegrationDAO;
    private RequestValidator requestValidator;
    private ShazamIntegrationRequestValidation shazamIntegrationRequestValidation;
    private ShazamIntegrationService shazamIntegrationService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.shazamIntegrationDAO = Mockito.mock(ShazamIntegrationDAO.class);
        this.shazamIntegrationRequestValidation = Mockito.mock(ShazamIntegrationRequestValidation.class);
        this.shazamIntegrationService = new ShazamIntegrationServiceImpl(shazamIntegrationDAO, shazamIntegrationMapper,
                shazamIntegrationRequestValidation);
    }

    @Test(enabled = true)
    public void testFindShazamIntegration() {
        try {
            List<ShazaamIntegrationEntity> entities = new ArrayList<>();
            ShazaamIntegrationEntity firstShazamIntegrationEntity = new ShazaamIntegrationEntity();

            firstShazamIntegrationEntity.setRequest("Test Request 1");
            firstShazamIntegrationEntity.setName("Test Name 1");
            firstShazamIntegrationEntity.setResponse("Test Response 1");

            ShazaamIntegrationEntity secondShazamIntegrationEntity = new ShazaamIntegrationEntity();
            firstShazamIntegrationEntity.setRequest("Test Request 2");
            firstShazamIntegrationEntity.setName("Test Name 2");
            firstShazamIntegrationEntity.setResponse("Test Response 2");

            ShazaamIntegrationEntity thirdShazamIntegrationEntity = new ShazaamIntegrationEntity();
            firstShazamIntegrationEntity.setRequest("Test Request 3");
            firstShazamIntegrationEntity.setName("Test Name 3");
            firstShazamIntegrationEntity.setResponse("Test Response 3");

            entities.add(firstShazamIntegrationEntity);
            entities.add(secondShazamIntegrationEntity);
            entities.add(thirdShazamIntegrationEntity);

            PagedData<ShazaamIntegrationEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);
            List<ShazamIntegrationResponse> response = new ArrayList<>();

            ShazamIntegrationResponse firstResponse = new ShazamIntegrationResponse();
            firstResponse.setRequest("Test Request 1");
            firstResponse.setName("Test Name 1");
            firstResponse.setResponse("Test Response 1");

            ShazamIntegrationResponse secondResponse = new ShazamIntegrationResponse();
            firstResponse.setRequest("Test Request 2");
            firstResponse.setName("Test Name 2");
            firstResponse.setResponse("Test Response 2");

            ShazamIntegrationResponse thirdResponse = new ShazamIntegrationResponse();
            firstResponse.setRequest("Test Request 3");
            firstResponse.setName("Test Name 3");
            firstResponse.setResponse("Test Response 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<ShazamIntegrationResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);
            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);
            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(shazamIntegrationDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);
            List<ShazamIntegrationResponse> shazamIntegrationFindResponse = shazamIntegrationService.find(filterRequest).getPayload();
            Assertions.assertThat(shazamIntegrationFindResponse).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal.").hasSameElementsAs(response);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateShazamIntegration() {
        try {
            EntityRequest<ShazamIntegrationCreateRequest> req = new EntityRequest<>();
            var newShazamIntegrationRequest = new ShazamIntegrationCreateRequest();
            newShazamIntegrationRequest.setRequest("Test Information");
            newShazamIntegrationRequest.setName("Test Type 1");
            newShazamIntegrationRequest.setResponse("Response 1");
            req.setEntity(newShazamIntegrationRequest);

            var newShazamIntegrationEnt = new ShazaamIntegrationEntity();
            newShazamIntegrationEnt.setRequest("Test Information");
            newShazamIntegrationEnt.setName("Test Type 1");
            newShazamIntegrationEnt.setResponse("Response 1");

            var newShazamIntegration = new ShazamIntegrationResponse();
            newShazamIntegration.setRequest("Test Information");
            newShazamIntegration.setName("Test Type 1");
            newShazamIntegration.setResponse("Response 1");
            newShazamIntegration.setStatus(Status.ACTIVE.getValue());
            Mockito.when(shazamIntegrationDAO.persist(newShazamIntegrationEnt)).thenReturn(null);
            PayloadResponse<ShazamIntegrationResponse> ShazamIntegrationFindResponse = shazamIntegrationService.create(req);
            Assertions.assertThat(ShazamIntegrationFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newShazamIntegration);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateShazamIntegration() {
        try {
            EntityRequest<ShazamIntegrationUpdateRequest> request = new EntityRequest<>();
            ShazaamIntegrationEntity shazamIntegrationEntity = new ShazaamIntegrationEntity();
            shazamIntegrationEntity.setRequest("Old Test Information 2");
            shazamIntegrationEntity.setName("Old Test Name 1");
            shazamIntegrationEntity.setResponse("Old Test Information");

            ShazamIntegrationResponse shazamIntegrationResponse = new ShazamIntegrationResponse();
            shazamIntegrationResponse.setRequest("Update Test Information 2");
            shazamIntegrationResponse.setName("Update Test Name 1");
            shazamIntegrationResponse.setResponse("Update Test Information");

            ShazamIntegrationUpdateRequest updateshazamIntegrationRequest = new ShazamIntegrationUpdateRequest();
            updateshazamIntegrationRequest.setRequest("Update Test Information 2");
            updateshazamIntegrationRequest.setName("Update Test Name 1");
            updateshazamIntegrationRequest.setResponse("Update Test Information");
            request.setEntity(updateshazamIntegrationRequest);

            Mockito.when(shazamIntegrationRequestValidation.validateUpdateShazamIntegrationRequest(request)).thenReturn(null);
            Mockito.when(shazamIntegrationDAO.findByPK(request.getEntity().getId())).thenReturn(shazamIntegrationEntity);
            Mockito.doNothing().when(shazamIntegrationDAO).merge(shazamIntegrationEntity);

            var shazamIntegrationUpdateResponse = shazamIntegrationService.update(request);
            Assertions.assertThat(shazamIntegrationUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(shazamIntegrationResponse);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteShazamIntegration() {
        try {
            var req = new EntityRequest<Long>();
            req.setEntity(1L);
            Mockito.when(requestValidator.validate(req)).thenReturn(null);
            Mockito.doNothing().when(shazamIntegrationDAO).removeByPK(req.getEntity());
            var shazamIntegrationFindResponse = shazamIntegrationService.delete(req);
            Assertions.assertThat(shazamIntegrationFindResponse.getPayload()).isEqualTo("Shazam Integration removed successfully!");
        } catch (Exception e) {
            Assert.fail();
        }
    }
}