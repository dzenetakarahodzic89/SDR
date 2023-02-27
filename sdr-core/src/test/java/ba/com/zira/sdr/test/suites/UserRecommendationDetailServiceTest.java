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
import ba.com.zira.sdr.api.UserRecommendationDetailService;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailResponse;
import ba.com.zira.sdr.core.impl.UserRecommendationDetailServiceImpl;
import ba.com.zira.sdr.core.mapper.UserRecommendationDetailMapper;
import ba.com.zira.sdr.core.validation.UserRecommendationDetailRequestValidation;
import ba.com.zira.sdr.core.validation.UserRecommendationRequestValidation;
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
	
	private UserRecommendationDetailDAO userRecommendationDetailDAO;
	private SongDAO songDAO;
	private UserRecommendationDAO userRecommendationDAO;
	
	private UserRecommendationDetailRequestValidation userRecommendationDetailRequestValidation;
	private UserRecommendationDetailService userRecommendationDetailService;
	
	private List<SongEntity> song = new ArrayList<>();
	private List<UserRecommendationEntity> userRecommendation = new ArrayList<>();

	
	
	private void setUpFkEntities() {
		
		 song.add(new SongEntity(1L, null, null, null, null, null, null, "song test 1", null, null, null, null, null, null, null, null, null,
	                null, null, null, null, null, null, null, null,null));
	     song.add(new SongEntity(2L, null, null, null, null, null, null, "song test 2", null, null, null, null, null, null, null, null, null,
	                null, null, null, null, null, null, null, null,null));
	     song.add(new SongEntity(3L, null, null, null, null, null, null, "song test 3", null, null, null, null, null, null, null, null, null,
	                null, null, null, null, null, null, null, null,null));
	     
	     userRecommendation.add(new UserRecommendationEntity(1L, null, null, null, "userRecommendation test 1", null, null, null));
	     userRecommendation.add(new UserRecommendationEntity(2L, null, null, null, "userRecommendation test 2", null, null, null));
	     userRecommendation.add(new UserRecommendationEntity(3L, null, null, null, "userRecommendation test 3", null, null, null));
	}
	
	 @BeforeMethod
	    public void beforeMethod() throws ApiException {
	        this.userRecommendationDetailDAO = Mockito.mock(UserRecommendationDetailDAO.class);
	        this.songDAO = Mockito.mock(SongDAO.class);
	        this.userRecommendationDAO = Mockito.mock(UserRecommendationDAO.class);
	        

	        this.userRecommendationDetailRequestValidation = Mockito.mock(UserRecommendationDetailRequestValidation.class);
	        this.userRecommendationDetailService = new UserRecommendationDetailServiceImpl(userRecommendationDetailDAO, userRecommendationDAO, songDAO,userRecommendationDetailMapper,
	        		userRecommendationDetailRequestValidation);

	        this.setUpFkEntities();
	    }
	
	 @Test(enabled = true)
	    public void testFindUserRecommendationDetail() {
	        try {

	            List<UserRecommendationDetailEntity> entities = new ArrayList<>();

	            UserRecommendationDetailEntity firstUserRecommendationDetailEntity = new UserRecommendationDetailEntity();
	            firstUserRecommendationDetailEntity.setSong(song.get(0));
	            firstUserRecommendationDetailEntity.setUserRecommendation(userRecommendation.get(0));
	          

	            UserRecommendationDetailEntity secondUserRecommendationDetailEntity = new UserRecommendationDetailEntity();
	            secondUserRecommendationDetailEntity.setSong(song.get(1));
	            secondUserRecommendationDetailEntity.setUserRecommendation(userRecommendation.get(1));
	 

	            UserRecommendationDetailEntity thirdUserRecommendationDetailEntity = new  UserRecommendationDetailEntity();
	            thirdUserRecommendationDetailEntity.setSong(song.get(2));
	            thirdUserRecommendationDetailEntity.setUserRecommendation(userRecommendation.get(2));
	            
	            entities.add(firstUserRecommendationDetailEntity);
	            entities.add(secondUserRecommendationDetailEntity);
	            entities.add(thirdUserRecommendationDetailEntity);

	            PagedData<UserRecommendationDetailEntity> pagedEntites = new PagedData<>();
	            pagedEntites.setRecords(entities);

	            List<UserRecommendationDetailResponse> response = new ArrayList<>();

	            UserRecommendationDetailResponse firstResponse = new UserRecommendationDetailResponse();
	            firstResponse.setUserRecommendationId(1L);
	            firstResponse.setSongId(1L);
	            
	            UserRecommendationDetailResponse secondResponse = new UserRecommendationDetailResponse();
	            secondResponse.setUserRecommendationId(2L);
	            secondResponse.setSongId(2L);
	          
	          
	            UserRecommendationDetailResponse thirdResponse = new UserRecommendationDetailResponse();
	            thirdResponse.setUserRecommendationId(3L);
	            thirdResponse.setSongId(3L);
	           
	         

	            response.add(firstResponse);
	            response.add(secondResponse);
	            response.add(thirdResponse);

	            PagedData<UserRecommendationDetailResponse> pagedResponse = new PagedData<>();
	            pagedResponse.setRecords(response);

	            Map<String, Object> filterCriteria = new HashMap<String, Object>();
	            QueryConditionPage queryConditionPage = new QueryConditionPage();

	            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);
	            Mockito.when(userRecommendationDetailDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

	            List<UserRecommendationDetailResponse> UserRecommendationDetailFindResponse = userRecommendationDetailService.find(filterRequest).getPayload();

	            Assertions.assertThat(UserRecommendationDetailFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
	                    .hasSameElementsAs(response);
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

	            UserRecommendationDetailEntity newUserRecommendationDetailEntity = new UserRecommendationDetailEntity();
	            newUserRecommendationDetailEntity.setUserRecommendation(userRecommendation.get(0));
	            newUserRecommendationDetailEntity.setSong(song.get(0));

	            var newUserRecommendationDetailResponse = new UserRecommendationDetailResponse();
	            newUserRecommendationDetailResponse.setUserRecommendationId(1L);
	          
	            newUserRecommendationDetailResponse.setSongId(1L);

	            newUserRecommendationDetailResponse.setStatus(Status.ACTIVE.getValue());

	            Mockito.when(userRecommendationDetailDAO.persist(newUserRecommendationDetailEntity)).thenReturn(null);
	            Mockito.when(userRecommendationDetailRequestValidation.validateCreateUserRecommendationDetailRequest(req)).thenReturn(null);

	            Mockito.when(userRecommendationDAO.findByPK(1L)).thenReturn(userRecommendation.get(0));
	            Mockito.when(songDAO.findByPK(1L)).thenReturn(song.get(0));

	            PayloadResponse<UserRecommendationDetailResponse> userRecommendationDetailCreateResponse = userRecommendationDetailService.create(req);

	            Assertions.assertThat(userRecommendationDetailCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
	                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newUserRecommendationDetailResponse);

	        } catch (Exception e) {
	            Assert.fail();
	        }
	    }

	    @Test(enabled = true)
	    public void testDeleteUserRecommendationDetail() {
	        try {
	            var req = new EntityRequest<Long>();

	            req.setEntity(1L);

	            Mockito.when(userRecommendationDetailRequestValidation.validateDeleteUserRecommendationDetailRequest(req)).thenReturn(null);
	            Mockito.doNothing().when(userRecommendationDetailDAO).removeByPK(req.getEntity());

	            var userRecommendationDetailDeleteResponse = userRecommendationDetailService.delete(req);

	            Assertions.assertThat(userRecommendationDetailDeleteResponse.getPayload()).isEqualTo("Deleted record successfully!");
	        } catch (Exception e) {
	            Assert.fail();
	        }
	    }
	}
	
	


