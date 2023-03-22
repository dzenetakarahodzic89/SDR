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
import ba.com.zira.sdr.api.UserRecommendationService;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.core.impl.UserRecommendationServiceImpl;
import ba.com.zira.sdr.core.mapper.UserRecommendationMapper;
import ba.com.zira.sdr.core.validation.UserRecommendationRequestValidation;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class UserRecommendationServiceTest extends BasicTestConfiguration {

    @Autowired
    private UserRecommendationMapper userRecommendationMapper;
    private RequestValidator requestValidator;
    private UserRecommendationDAO userRecommendationDAO;
    private UserRecommendationRequestValidation userRecommendationRequestValidation;
    private UserRecommendationService userRecommendationService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {

        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.userRecommendationDAO = Mockito.mock(UserRecommendationDAO.class);
        this.userRecommendationRequestValidation = Mockito.mock(UserRecommendationRequestValidation.class);
        this.userRecommendationService = new UserRecommendationServiceImpl(userRecommendationDAO, userRecommendationMapper,
                userRecommendationRequestValidation, null);

    }

    @Test(enabled = true)
    public void testFindUserRecommendation() {
        try {

            List<UserRecommendationEntity> entities = new ArrayList<>();
            UserRecommendationEntity firstUserRecommendationEntity = new UserRecommendationEntity();
            firstUserRecommendationEntity.setDescription("Test Information");
            firstUserRecommendationEntity.setName("Test Type 1");

            UserRecommendationEntity secondUserRecommendationEntity = new UserRecommendationEntity();
            secondUserRecommendationEntity.setDescription("Test Information");
            secondUserRecommendationEntity.setName("Test Type 2");

            UserRecommendationEntity thirdUserRecommendationEntity = new UserRecommendationEntity();
            thirdUserRecommendationEntity.setDescription("Test Information");
            thirdUserRecommendationEntity.setName("Test Type 3");

            entities.add(firstUserRecommendationEntity);
            entities.add(secondUserRecommendationEntity);
            entities.add(thirdUserRecommendationEntity);

            PagedData<UserRecommendationEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<UserRecommendationResponse> response = new ArrayList<>();

            UserRecommendationResponse firstResponse = new UserRecommendationResponse();
            firstResponse.setDescription("Test Information");
            firstResponse.setName("Test Type 1");

            UserRecommendationResponse secondResponse = new UserRecommendationResponse();
            secondResponse.setDescription("Test Information");
            secondResponse.setName("Test Type 2");

            UserRecommendationResponse thirdResponse = new UserRecommendationResponse();
            thirdResponse.setDescription("Test Information");
            thirdResponse.setName("Test Type 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<UserRecommendationResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(userRecommendationDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<UserRecommendationResponse> userRecommendationFindReponse = userRecommendationService.find(filterRequest).getPayload();

            Assertions.assertThat(userRecommendationFindReponse).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal.").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testFindByIdUserRecommendation() {
        try {
            EntityRequest<Long> req = new EntityRequest<Long>();
            req.setEntity(1L);

            var userRecommendationEntity = new UserRecommendationEntity();
            userRecommendationEntity.setId(1L);

            UserRecommendationResponse resp = new UserRecommendationResponse();
            resp.setId(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.when(userRecommendationDAO.findByPK(req.getEntity())).thenReturn(userRecommendationEntity);
            PayloadResponse<UserRecommendationResponse> userRecommendationFindByIdResponse = userRecommendationService.findById(req);

            Assertions.assertThat(userRecommendationFindByIdResponse.getPayload()).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "description", "name", "status", "userCode").isEqualTo(resp);

        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test(enabled = true)
    public void testDeleteUserRecommendation() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(userRecommendationRequestValidation.validateExistsUserRecommendationRequest(req)).thenReturn(null);
            Mockito.doNothing().when(userRecommendationDAO).removeByPK(req.getEntity());

            var userRecommendationDeleteResponse = userRecommendationService.delete(req);

            Assertions.assertThat(userRecommendationDeleteResponse.getPayload()).isEqualTo("User Recommendation successfully deleted!");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateUserRecommendation() {
        try {
            EntityRequest<UserRecommendationCreateRequest> req = new EntityRequest<>();

            var newUserRecommendationRequest = new UserRecommendationCreateRequest();
            newUserRecommendationRequest.setName("Test User Recommendation");
            newUserRecommendationRequest.setDescription("Test Description");

            req.setEntity(newUserRecommendationRequest);

            var userRecommendationEntity = new UserRecommendationEntity();
            userRecommendationEntity.setName("Test User Recommendation");
            userRecommendationEntity.setDescription("Test Description");

            var newUserRecommendation = new UserRecommendationResponse();
            newUserRecommendation.setName("Test User Recommendation");
            newUserRecommendation.setDescription("Test Description");
            newUserRecommendation.setStatus(Status.ACTIVE.getValue());

            Mockito.when(userRecommendationDAO.persist(userRecommendationEntity)).thenReturn(null);

            PayloadResponse<UserRecommendationResponse> userRecommendationCreateResponse = userRecommendationService.create(req);

            Assertions.assertThat(userRecommendationCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "userCode").isEqualTo(newUserRecommendation);

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
