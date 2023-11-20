package ba.com.zira.sdr.test.suites;

import static org.testng.AssertJUnit.assertEquals;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationUpdateRequest;
import ba.com.zira.sdr.core.validation.ShazamIntegrationRequestValidation;
import ba.com.zira.sdr.dao.ShazamIntegrationDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class ShazamIntegrationRequestValidationTest extends BasicTestConfiguration {
	private static final String TEMPLATE_CODE = "TEST_1";
	private ShazamIntegrationDAO shazamIntegrationDAO;
	private ShazamIntegrationRequestValidation validation;

	@BeforeMethod
	public void beforeMethod() throws ApiException {
		this.shazamIntegrationDAO = Mockito.mock(ShazamIntegrationDAO.class);
		this.validation = new ShazamIntegrationRequestValidation(shazamIntegrationDAO);
	}

	@Test
	public void validateUpdateRequestShazamIntegrationNotFound() {
		Mockito.when(shazamIntegrationDAO.existsByPK(1L)).thenReturn(false);
		EntityRequest<ShazamIntegrationUpdateRequest> request = new EntityRequest<>();
		request.setUser(new User("test"));
		var response = new ShazamIntegrationUpdateRequest();
		response.setId(1L);
		request.setEntity(response);
		ValidationResponse validationResponse = validation.validateUpdateShazamIntegrationRequest(request);
		assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
		assertEquals(validationResponse.getDescription(), "Shazam integration with id: 1 does not exist!");
		Mockito.verify(shazamIntegrationDAO).existsByPK(1L);
	}
}