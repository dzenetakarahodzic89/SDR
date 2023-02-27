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
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.core.validation.PersonArtistRequestValidation;
import ba.com.zira.sdr.core.validation.UserRecommendationDetailRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.UserRecommendationDetailDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class UserRecommendationDetailRequestValidationTest  extends BasicTestConfiguration  {

	    private static final String TEMPLATE_CODE = "TEST_1";

	    private UserRecommendationDAO userRecommendationDAO;
	    private SongDAO songDAO;
	    private UserRecommendationDetailDAO userRecommendationDetailDAO;
	    private UserRecommendationDetailRequestValidation validation;

	    @BeforeMethod
	    public void beforeMethod() throws ApiException {
	        this.userRecommendationDAO = Mockito.mock(UserRecommendationDAO.class);
	        this.songDAO = Mockito.mock(SongDAO.class);
	        this.userRecommendationDetailDAO = Mockito.mock(UserRecommendationDetailDAO.class);
	        this.validation = new UserRecommendationDetailRequestValidation(userRecommendationDetailDAO, userRecommendationDAO, songDAO);
	    }

	    @Test
	    public void validateCreateUserRecommendationDetailRequestUserRecommendationNotValid() {
	        Mockito.when(userRecommendationDAO.existsByPK(1L)).thenReturn(false);
	        Mockito.when(songDAO.existsByPK(1L)).thenReturn(true);
	        EntityRequest<UserRecommendationDetailCreateRequest> request = new EntityRequest<>();
	        request.setUser(new User("test"));
	        var response = new UserRecommendationDetailCreateRequest();
	        response.setUserRecommendationId(1L);
	        response.setSongId(1L);
	        request.setEntity(response);
	        ValidationResponse validationResponse = validation.validateCreateUserRecommendationDetailRequest(request);
	        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
	        assertEquals(validationResponse.getDescription(), "User-recommendation with id: 1 does not exist!");
	        Mockito.verify(userRecommendationDAO).existsByPK(1L);
	    }

	    @Test
	    public void validateCreateUserRecommendationDetailRequestSongNotValid() {
	        Mockito.when(userRecommendationDAO.existsByPK(1L)).thenReturn(true);
	        Mockito.when(songDAO.existsByPK(1L)).thenReturn(false);
	        EntityRequest<UserRecommendationDetailCreateRequest> request = new EntityRequest<>();
	        request.setUser(new User("test"));
	        var response = new UserRecommendationDetailCreateRequest();
	        response.setUserRecommendationId(1L);
	        response.setSongId(1L);
	        request.setEntity(response);
	        ValidationResponse validationResponse = validation.validateCreateUserRecommendationDetailRequest(request);
	        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
	        assertEquals(validationResponse.getDescription(), "Song with id: 1 does not exist!");
	        Mockito.verify(songDAO).existsByPK(1L);
	    }

	    @Test
	    public void validateDeleteUserRecommendationDetailRequestNotValid() {
	        Mockito.when(userRecommendationDAO.existsByPK(1L)).thenReturn(false);
	        EntityRequest<Long> request = new EntityRequest<Long>(1L);
	        request.setUser(new User("test"));
	        ValidationResponse validationResponse = validation.validateDeleteUserRecommendationDetailRequest(request);
	        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
	        assertEquals(validationResponse.getDescription(), "User-recommendation-detail with id: 1 does not exist!");
	        Mockito.verify(userRecommendationDetailDAO).existsByPK(1L);
	    }
	}
	    
