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
import ba.com.zira.sdr.api.MoritsIntegrationService;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegration;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;
import ba.com.zira.sdr.core.impl.MoritsIntegrationServiceImpl;
import ba.com.zira.sdr.core.mapper.MoritsIntegrationMapper;
import ba.com.zira.sdr.core.validation.MoritsIntegrationRequestValidation;
import ba.com.zira.sdr.dao.MoritsIntegrationDAO;
import ba.com.zira.sdr.dao.model.MoritsIntegrationEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class MoritsIntegrationServiceTest extends BasicTestConfiguration {

    @Autowired
    private MoritsIntegrationMapper moritsIntegrationMapper;

    private MoritsIntegrationDAO moritsIntegrationDAO;
    private RequestValidator requestValidator;
    private MoritsIntegrationRequestValidation moritsIntegrationRequestValidation;
    private MoritsIntegrationService moritsIntegrationService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.moritsIntegrationDAO = Mockito.mock(MoritsIntegrationDAO.class);
        this.moritsIntegrationRequestValidation = Mockito.mock(MoritsIntegrationRequestValidation.class);
        this.moritsIntegrationService = new MoritsIntegrationServiceImpl(moritsIntegrationDAO, moritsIntegrationMapper,
                moritsIntegrationRequestValidation);
    }

    @Test(enabled = true)
    public void testFindMoritsIntegration() {
        try {
            List<MoritsIntegrationEntity> entities = new ArrayList<>();

            MoritsIntegrationEntity firstMoritsIntegrationEntity = new MoritsIntegrationEntity();
            firstMoritsIntegrationEntity.setName("Test Type 1");
            firstMoritsIntegrationEntity.setRequest("Request 1");
            firstMoritsIntegrationEntity.setResponse("Response 1");

            MoritsIntegrationEntity secondMoritsIntegrationEntity = new MoritsIntegrationEntity();
            secondMoritsIntegrationEntity.setName("Test Type 2");
            secondMoritsIntegrationEntity.setRequest("Request 2");
            secondMoritsIntegrationEntity.setResponse("Response 2");

            MoritsIntegrationEntity thirdMoritsIntegrationEntity = new MoritsIntegrationEntity();
            thirdMoritsIntegrationEntity.setName("Test Type 3");
            thirdMoritsIntegrationEntity.setRequest("Request 3");
            thirdMoritsIntegrationEntity.setResponse("Response 3");

            entities.add(firstMoritsIntegrationEntity);
            entities.add(secondMoritsIntegrationEntity);
            entities.add(thirdMoritsIntegrationEntity);

            PagedData<MoritsIntegrationEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<MoritsIntegration> response = new ArrayList<>();

            MoritsIntegration firstResponse = new MoritsIntegration();
            firstResponse.setName("Test Type 1");
            firstResponse.setRequest("Request 1");
            firstResponse.setResponse("Response 1");

            MoritsIntegration secondResponse = new MoritsIntegration();
            secondResponse.setName("Test Type 2");
            secondResponse.setRequest("Request 2");
            secondResponse.setResponse("Response 2");

            MoritsIntegration thirdResponse = new MoritsIntegration();
            thirdResponse.setName("Test Type 3");
            thirdResponse.setRequest("Request 3");
            thirdResponse.setResponse("Response 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<MoritsIntegration> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(moritsIntegrationDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<MoritsIntegration> moritsIntegrationFindResponse = moritsIntegrationService.find(filterRequest).getPayload();

            Assertions.assertThat(moritsIntegrationFindResponse).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal.").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateMoritsIntegration() {
        try {

            EntityRequest<MoritsIntegrationCreateRequest> req = new EntityRequest<>();

            var newMoritsIntegrationRequest = new MoritsIntegrationCreateRequest();
            newMoritsIntegrationRequest.setName("Test Type 1");
            newMoritsIntegrationRequest.setRequest("Request 1");
            newMoritsIntegrationRequest.setResponse("Response 1");

            req.setEntity(newMoritsIntegrationRequest);

            var newMoritsIntegrationEnt = new MoritsIntegrationEntity();
            newMoritsIntegrationEnt.setName("Test Type 1");
            newMoritsIntegrationEnt.setRequest("Request 1");
            newMoritsIntegrationEnt.setResponse("Response 1");

            var newMoritsIntegration = new MoritsIntegration();
            newMoritsIntegration.setName("Test Type 1");
            newMoritsIntegration.setRequest("Request 1");
            newMoritsIntegration.setResponse("Response 1");
            newMoritsIntegration.setStatus(Status.ACTIVE.getValue());

            Mockito.when(moritsIntegrationDAO.persist(newMoritsIntegrationEnt)).thenReturn(null);

            PayloadResponse<MoritsIntegration> moritsIntegrationFindResponse = moritsIntegrationService.create(req);

            Assertions.assertThat(moritsIntegrationFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newMoritsIntegration);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateMoritsIntegration() {
        try {

            EntityRequest<MoritsIntegrationUpdateRequest> request = new EntityRequest<>();

            MoritsIntegrationEntity moritsIntegrationEntity = new MoritsIntegrationEntity();
            moritsIntegrationEntity.setName("Old Test Name");
            moritsIntegrationEntity.setRequest("Old Request");
            moritsIntegrationEntity.setResponse("Old Response");

            MoritsIntegration moritsIntegrationResponse = new MoritsIntegration();
            moritsIntegrationResponse.setName("Update Test Name");
            moritsIntegrationResponse.setRequest("Update Request");
            moritsIntegrationResponse.setResponse("Update Response");

            MoritsIntegrationUpdateRequest updateMoritsIntegrationRequest = new MoritsIntegrationUpdateRequest();
            updateMoritsIntegrationRequest.setName("Update Test Name");
            updateMoritsIntegrationRequest.setRequest("Update Request");
            updateMoritsIntegrationRequest.setResponse("Update Response");
            request.setEntity(updateMoritsIntegrationRequest);

            Mockito.when(moritsIntegrationRequestValidation.validateUpdateMoritsIntegrationRequest(request)).thenReturn(null);

            Mockito.when(moritsIntegrationDAO.findByPK(request.getEntity().getId())).thenReturn(moritsIntegrationEntity);

            Mockito.doNothing().when(moritsIntegrationDAO).merge(moritsIntegrationEntity);

            var moritsIntegrationUpdateResponse = moritsIntegrationService.update(request);
            Assertions.assertThat(moritsIntegrationUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(moritsIntegrationResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteMoritsIntegration() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(moritsIntegrationRequestValidation.validateExistsMoritsIntegrationRequest(req)).thenReturn(null);

            Mockito.doNothing().when(moritsIntegrationDAO).removeByPK(req.getEntity());

            var moritsIntegrationDeleteResponse = moritsIntegrationService.delete(req);

            Assertions.assertThat(moritsIntegrationDeleteResponse.getPayload()).isEqualTo("Morits lyric integration removed successfully!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
