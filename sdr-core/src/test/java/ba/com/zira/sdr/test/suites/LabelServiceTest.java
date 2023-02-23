package ba.com.zira.sdr.test.suites;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
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
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.LabelService;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.core.impl.LabelServiceImpl;
import ba.com.zira.sdr.core.mapper.LabelMapper;
import ba.com.zira.sdr.core.validation.LabelRequestValidation;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class LabelServiceTest extends BasicTestConfiguration {

    @Autowired
    private LabelMapper labelMapper;

    private LabelDAO labelDAO;
    private PersonDAO personDAO;
    private RequestValidator requestValidator;
    private LabelRequestValidation labelRequestValidation;
    private LabelService labelService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.labelDAO = Mockito.mock(LabelDAO.class);
        this.personDAO = Mockito.mock(PersonDAO.class);
        this.labelRequestValidation = Mockito.mock(LabelRequestValidation.class);
        this.labelService = new LabelServiceImpl(labelDAO, labelMapper, personDAO, labelRequestValidation);

    }

    @Test(enabled = true)
    public void testFindLabel() {
        try {

            List<LabelEntity> entities = new ArrayList<>();
            LabelEntity firstLabelEntity = new LabelEntity();
            firstLabelEntity.setInformation("Test Information");
            firstLabelEntity.setName("Test Type 1");
            firstLabelEntity.setOutlineText("Outline text 1");

            LabelEntity secondLabelEntity = new LabelEntity();
            secondLabelEntity.setInformation("Test Information");
            secondLabelEntity.setName("Test Type 2");
            secondLabelEntity.setOutlineText("Outline text 2");

            LabelEntity thirdLabelEntity = new LabelEntity();
            thirdLabelEntity.setInformation("Test Information");
            thirdLabelEntity.setName("Test Type 3");
            thirdLabelEntity.setOutlineText("Outline text 3");

            entities.add(firstLabelEntity);
            entities.add(secondLabelEntity);
            entities.add(thirdLabelEntity);

            PagedData<LabelEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<LabelResponse> response = new ArrayList<>();

            LabelResponse firstResponse = new LabelResponse();
            firstResponse.setInformation("Test Information");
            firstResponse.setLabelName("Test Type 1");
            firstResponse.setOutlineText("Outline text 1");

            LabelResponse secondResponse = new LabelResponse();
            secondResponse.setInformation("Test Information");
            secondResponse.setLabelName("Test Type 2");
            secondResponse.setOutlineText("Outline text 2");

            LabelResponse thirdResponse = new LabelResponse();
            thirdResponse.setInformation("Test Information");
            thirdResponse.setLabelName("Test Type 3");
            thirdResponse.setOutlineText("Outline text 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<LabelResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(labelDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<LabelResponse> labelFindResponse = labelService.find(filterRequest).getPayload();

            Assertions.assertThat(labelFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testFindByIdLabel() {
        try {
            EntityRequest<Long> req = new EntityRequest<Long>();
            req.setEntity(1L);

            var labelEntity = new LabelEntity();
            labelEntity.setId(1L);

            LabelResponse resp = new LabelResponse();
            resp.setId(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.when(labelDAO.findByPK(req.getEntity())).thenReturn(labelEntity);
            PayloadResponse<LabelResponse> labelFindByIdResponse = labelService.findById(req);

            Assertions.assertThat(labelFindByIdResponse.getPayload()).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal").usingRecursiveComparison()
                    .ignoringFields("createdBy", "created", "foundingDate", "information", "modified", "modifiedBy", "labelName", "status",
                            "founderId", "outlineText")
                    .isEqualTo(resp);

        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test(enabled = true)
    public void testDeleteLabel() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(labelRequestValidation.validateExistsLabelRequest(req)).thenReturn(null);
            Mockito.doNothing().when(labelDAO).removeByPK(req.getEntity());

            var labelDeleteResponse = labelService.delete(req);

            Assertions.assertThat(labelDeleteResponse.getPayload()).isEqualTo("Label successfully deleted!");
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
