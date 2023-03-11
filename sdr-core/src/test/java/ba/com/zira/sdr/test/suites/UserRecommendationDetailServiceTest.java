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
import ba.com.zira.sdr.api.UserRecommendationDetailService;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailResponse;
import ba.com.zira.sdr.core.impl.UserRecommendationDetailServiceImpl;
import ba.com.zira.sdr.core.mapper.UserRecommendationDetailMapper;
import ba.com.zira.sdr.core.validation.UserRecommendationDetailRequestValidation;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.UserRecommendationDetailDAO;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.UserRecommendationDetailEntity;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class UserRecommendationDetailServiceTest extends BasicTestConfiguration {

    @Autowired
    private UserRecommendationDetailMapper userRecommendationDetailMapper;

    private UserRecommendationDAO userRecommendationDAO;
    private UserRecommendationDetailDAO userRecommendationDetailDAO;
    private SongDAO songDAO;
    private UserRecommendationDetailRequestValidation userRecommendationDetailRequestValidation;
    private RequestValidator requestValidator;
    private UserRecommendationDetailService userRecommendationDetailService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.userRecommendationDAO = Mockito.mock(UserRecommendationDAO.class);
        this.userRecommendationDetailDAO = Mockito.mock(UserRecommendationDetailDAO.class);
        this.songDAO = Mockito.mock(SongDAO.class);
        this.userRecommendationDetailRequestValidation = Mockito.mock(UserRecommendationDetailRequestValidation.class);
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.userRecommendationDetailService = new UserRecommendationDetailServiceImpl(userRecommendationDetailDAO, userRecommendationDAO,
                songDAO, userRecommendationDetailMapper, userRecommendationDetailRequestValidation);
    }

    @Test(enabled = true)
    public void testFindUserRecommendationDetail() {
        try {

            List<UserRecommendationDetailEntity> entities = new ArrayList<>();
            UserRecommendationDetailEntity firstUserRecommendationDetailEntity = new UserRecommendationDetailEntity();
            firstUserRecommendationDetailEntity.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            firstUserRecommendationDetailEntity.setCreatedBy("1");
            firstUserRecommendationDetailEntity.setStatus(Status.ACTIVE.getValue());

            UserRecommendationDetailEntity secondUserRecommendationDetailEntity = new UserRecommendationDetailEntity();
            secondUserRecommendationDetailEntity.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            secondUserRecommendationDetailEntity.setCreatedBy("2");
            secondUserRecommendationDetailEntity.setStatus(Status.INACTIVE.getValue());

            UserRecommendationDetailEntity thirdUserRecommendationDetailEntity = new UserRecommendationDetailEntity();
            thirdUserRecommendationDetailEntity.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            thirdUserRecommendationDetailEntity.setCreatedBy("3");
            thirdUserRecommendationDetailEntity.setStatus(Status.INACTIVE.getValue());

            entities.add(firstUserRecommendationDetailEntity);
            entities.add(secondUserRecommendationDetailEntity);
            entities.add(thirdUserRecommendationDetailEntity);

            PagedData<UserRecommendationDetailEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<UserRecommendationDetailResponse> response = new ArrayList<>();

            UserRecommendationDetailResponse firstResponse = new UserRecommendationDetailResponse();
            firstResponse.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            firstResponse.setCreatedBy("1");
            firstResponse.setStatus(Status.ACTIVE.getValue());

            UserRecommendationDetailResponse secondResponse = new UserRecommendationDetailResponse();
            secondResponse.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            secondResponse.setCreatedBy("2");
            secondResponse.setStatus(Status.INACTIVE.getValue());

            UserRecommendationDetailResponse thirdResponse = new UserRecommendationDetailResponse();
            thirdResponse.setCreated(LocalDateTime.parse("2023-02-22T11:04:25.896"));
            thirdResponse.setCreatedBy("3");
            thirdResponse.setStatus(Status.INACTIVE.getValue());

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<UserRecommendationDetailResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(userRecommendationDetailDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<UserRecommendationDetailResponse> userRecommendationDetailFindResponse = userRecommendationDetailService
                    .find(filterRequest).getPayload();

            Assertions.assertThat(userRecommendationDetailFindResponse).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal.").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateUserRecommendationDetail() {
        try {

            EntityRequest<UserRecommendationDetailCreateRequest> req = new EntityRequest<>();

            var newUserRecommendationDetailRequest = new UserRecommendationDetailCreateRequest();
            newUserRecommendationDetailRequest.setUserRecommendationId(1L);
            newUserRecommendationDetailRequest.setSongId(1L);

            req.setEntity(newUserRecommendationDetailRequest);

            var userRecommendationDetailEntity = new UserRecommendationDetailEntity();
            userRecommendationDetailEntity
                    .setUserRecommendation(new UserRecommendationEntity(1L, null, null, null, null, null, null, null));
            userRecommendationDetailEntity.setSong(new SongEntity(1L, null, null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));

            var newUserRecommendationDetail = new UserRecommendationDetailResponse();
            newUserRecommendationDetail.setUserRecommendationId(1L);
            newUserRecommendationDetail.setSongId(1L);
            Mockito.when(userRecommendationDAO.findByPK(1L))
                    .thenReturn(new UserRecommendationEntity(1L, null, null, null, null, null, null, null));
            Mockito.when(songDAO.findByPK(1L))
                    .thenReturn(new SongEntity(1L, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
            Mockito.when(userRecommendationDetailDAO.persist(userRecommendationDetailEntity)).thenReturn(null);

            PayloadResponse<UserRecommendationDetailResponse> userRecommendationDetailCreateResponse = userRecommendationDetailService
                    .create(req);
            System.out.println(userRecommendationDetailCreateResponse.getPayload());
            System.out.println(newUserRecommendationDetail);
            Assertions.assertThat(userRecommendationDetailCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "status", "user score")
                    .isEqualTo(newUserRecommendationDetail);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteUserRecommendationDetail() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(userRecommendationDetailDAO).removeByPK(req.getEntity());

            var userRecommendationDetailDeleteResponse = userRecommendationDetailService.delete(req);

            Assertions.assertThat(userRecommendationDetailDeleteResponse.getPayload()).isEqualTo("Deleted record successfully!");

        } catch (Exception e) {
            Assert.fail();
        }
    }
}
