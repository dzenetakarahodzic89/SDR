package ba.com.zira.sdr.test.suites;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.core.validation.UserRecommendationDetailRequestValidation;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.UserRecommendationDetailDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class UserRecommendationDetailRequestValidationTest  extends BasicTestConfiguration  {

	 private static final String TEMPLATE_CODE = "TEST_1";
	 private UserRecommendationDetailDAO userRecommendationDetailDAO;
	    private UserRecommendationDAO userRecommendationDAO;
	    private SongDAO songDAO;
	    private UserRecommendationDetailRequestValidation validation;

	    @BeforeMethod
	    public void beforeMethod() throws ApiException {
	        this.userRecommendationDetailDAO = Mockito.mock(UserRecommendationDetailDAO.class);
	        this.userRecommendationDAO = Mockito.mock(UserRecommendationDAO.class);
	        this.songDAO = Mockito.mock(SongDAO.class);
	        

	        this.validation = new UserRecommendationDetailRequestValidation(userRecommendationDetailDAO, userRecommendationDAO, songDAO );
	    }

	    @Test
	    public void validateCreateRequestIdsNotFound() {
	        ArrayList<String> errorList = new ArrayList<>();
	        errorList.add("UserRecommendation with id: 1 does not exist!");
	        errorList.add("Song with id: 1 does not exist!");
	    

	        Mockito.when(userRecommendationDAO.existsByPK(1L)).thenReturn(false);
	        Mockito.when(songDAO.existsByPK(1L)).thenReturn(false);

	        var createRequestEntity = new UserRecommendationDetailCreateRequest();
	        createRequestEntity.setUserRecommendationId(1L);
	        createRequestEntity.setSongId(1L);


	        EntityRequest<UserRecommendationDetailCreateRequest> request = new EntityRequest<>();
	        request.setUser(new User("test"));
	        request.setEntity(createRequestEntity);

	        ValidationResponse validationResponse = validation.validateCreateUserRecommendationDetailRequest(request);

	        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
	        errorList.forEach(error -> assertTrue(validationResponse.getDescription().contains(error)));

	        Mockito.verify(userRecommendationDAO).existsByPK(1L);
	        Mockito.verify(songDAO).existsByPK(1L);
	    }

	    @Test
	    public void validateCreateRequestAllIdsFound() {
	    	Mockito.when(userRecommendationDAO.existsByPK(1L)).thenReturn(true);
	    	Mockito.when(songDAO.existsByPK(1L)).thenReturn(true);
	      
	        var createRequestEntity = new UserRecommendationDetailCreateRequest();
	        createRequestEntity.setUserRecommendationId(1L);
	        createRequestEntity.setSongId(1L);


	        EntityRequest<UserRecommendationDetailCreateRequest> request = new EntityRequest<>();
	        request.setUser(new User("test"));
	        request.setEntity(createRequestEntity);

	        ValidationResponse validationResponse = validation.validateCreateUserRecommendationDetailRequest(request);

	        assertEquals(validationResponse.getCode(), ResponseCode.OK);
	        assertEquals(validationResponse.getDescription(), null);

	        Mockito.verify(userRecommendationDAO).existsByPK(1L);
	        Mockito.verify(songDAO).existsByPK(1L);
	       
	    }

	    @Test
	    public void validateDeleteRequestIdNotFound() {
	        Mockito.when(userRecommendationDetailDAO.existsByPK(1L)).thenReturn(false);

	        EntityRequest<Long> request = new EntityRequest<>();
	        request.setUser(new User("test"));
	        request.setEntity(1L);

	        ValidationResponse validationResponse = validation.validateDeleteUserRecommendationDetailRequest(request);

	        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
	        assertEquals(validationResponse.getDescription(), "User-recommendation-detail with id: 1 does not exist!");

	        Mockito.verify(userRecommendationDetailDAO).existsByPK(1L);
	    }

	    @Test
	    public void validateDeleteRequestIdFound() {
	        Mockito.when(userRecommendationDetailDAO.existsByPK(1L)).thenReturn(true);

	        EntityRequest<Long> request = new EntityRequest<>();
	        request.setUser(new User("test"));
	        request.setEntity(1L);

	        ValidationResponse validationResponse = validation.validateDeleteUserRecommendationDetailRequest(request);

	        assertEquals(validationResponse.getCode(), ResponseCode.OK);
	        assertEquals(validationResponse.getDescription(), null);

	        Mockito.verify(userRecommendationDetailDAO).existsByPK(1L);
	    }
	    
}