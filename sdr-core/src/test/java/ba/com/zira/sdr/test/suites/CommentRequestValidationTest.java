package ba.com.zira.sdr.test.suites;

import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class CommentRequestValidationTest extends BasicTestConfiguration {

    /*
     * private static final String TEMPLATE_CODE = "TEST_1";
     * 
     * private CommentDAO commentDAO; private CommentRequestValidation
     * validation;
     * 
     * @BeforeMethod public void beforeMethod() throws ApiException {
     * this.commentDAO = Mockito.mock(CommentDAO.class); this.validation = new
     * CommentRequestValidation(commentDAO); }
     * 
     * 
     * 
     * @Test public void validateUpdateRequestCommentNotFound() {
     * Mockito.when(commentDAO.existsByPK(1L)).thenReturn(false);
     * EntityRequest<CommentUpdateRequest> request = new EntityRequest<>();
     * request.setUser(new User("test")); var respose = new
     * CommentUpdateRequest(); respose.setId(1L); request.setEntity(respose);
     * ValidationResponse validationResponse =
     * validation.validateUpdateCommentModelRequest(request);
     * 
     * assertEquals(validationResponse.getCode(), ResponseCode.REQUEST_INVALID);
     * assertEquals(validationResponse.getDescription(),
     * "Comment with id: 1 does not exist!");
     * Mockito.verify(commentDAO).existsByPK(1L); }
     * 
     */
}
