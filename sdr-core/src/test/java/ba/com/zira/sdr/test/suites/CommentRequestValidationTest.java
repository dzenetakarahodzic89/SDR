package ba.com.zira.sdr.test.suites;

import static org.testng.Assert.assertEquals;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.core.validation.CommentRequestValidation;
import ba.com.zira.sdr.dao.CommentDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class CommentRequestValidationTest extends BasicTestConfiguration {

    private static final String TEMPLATE_CODE = "TEST_1";

    private CommentDAO commentDAO;
    private CommentRequestValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.commentDAO = Mockito.mock(CommentDAO.class);
        this.validation = new CommentRequestValidation(commentDAO);
    }

    /**
     * Update
     */

    @Test
    public void validateUpdateRequestCommentNotFound() {
        Mockito.when(commentDAO.existsByPK(1L)).thenReturn(false);
        EntityRequest<CommentUpdateRequest> request = new EntityRequest<>();
        request.setUser(new User("test"));
        var respose = new CommentUpdateRequest();
        respose.setId(1L);
        request.setEntity(respose);
        ValidationResponse validationResponse = validation.validateUpdateCommentModelRequest(request);

        assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
        assertEquals(validationResponse.getDescription(), "Comment with id: 1 does not exist!");
        Mockito.verify(commentDAO).existsByPK(1L);
    }

}
