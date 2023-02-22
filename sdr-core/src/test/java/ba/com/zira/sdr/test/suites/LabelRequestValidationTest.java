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
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;
import ba.com.zira.sdr.core.validation.LabelRequestValidation;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class LabelRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private LabelDAO labelDAO;
    private LabelRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.labelDAO = Mockito.mock(LabelDAO.class);
        this.validation = new LabelRequestValidation(labelDAO);
    }

    /**
     * Update
     */

    @Test
    public void validateUpdateRequestLabelNotFound() {
        Mockito.when(labelDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<LabelUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new LabelUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateLabelRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Label with id: 1 does not exist!");
        Mockito.verify(labelDAO).existsByPK(1L);
    }
}
