package ba.com.zira.sdr.test.suites;

import static org.testng.Assert.assertEquals;

import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.core.validation.UserRecommendationRequestValidation;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class UserRecommendationRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private UserRecommendationDAO userRecommendationDAO;
    private UserRecommendationRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.userRecommendationDAO = Mockito.mock(UserRecommendationDAO.class);
        this.validation = new UserRecommendationRequestValidation(userRecommendationDAO);

    }

    @Test(enabled = true)
    public void validateExistsUserRecommendationRequest() {
        Mockito.when(userRecommendationDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<Long> request = new EntityRequest<>();
        request.setUser(new User("test"));
        request.setEntity(1L);
        ValidationResponse validationResponse = validation.validateExistsUserRecommendationRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "User recommendation with id: 1 does not exist!");
        Mockito.verify(userRecommendationDAO).existsByPK(1L);

    }
}
