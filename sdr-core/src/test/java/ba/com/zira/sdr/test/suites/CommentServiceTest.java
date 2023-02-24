package ba.com.zira.sdr.test.suites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.CommentService;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.core.impl.CommentServiceImpl;
import ba.com.zira.sdr.core.mapper.CommentMapper;
import ba.com.zira.sdr.core.validation.CommentRequestValidation;
import ba.com.zira.sdr.dao.CommentDAO;
import ba.com.zira.sdr.dao.model.CommentEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class CommentServiceTest extends BasicTestConfiguration {

    @Autowired
    private CommentMapper commentMapper;

    private CommentDAO commentDAO;
    private RequestValidator requestValidator;
    private CommentRequestValidation commentRequestValidation;
    private CommentService commentService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.commentDAO = Mockito.mock(CommentDAO.class);
        this.commentRequestValidation = Mockito.mock(CommentRequestValidation.class);
        this.commentService = new CommentServiceImpl(commentDAO, commentMapper, commentRequestValidation);
    }

    @Test(enabled = true)
    public void testFindComment() {
        try {

            List<CommentEntity> entities = new ArrayList<>();
            CommentEntity firstCommentEntity = new CommentEntity();
            firstCommentEntity.setContent("Test Comment");
            firstCommentEntity.setId(1L);

            CommentEntity secondCommentEntity = new CommentEntity();
            secondCommentEntity.setContent("Test Comment");
            secondCommentEntity.setId(2L);

            CommentEntity thirdCommentEntity = new CommentEntity();
            thirdCommentEntity.setContent("Test Comment");
            thirdCommentEntity.setId(3L);

            entities.add(firstCommentEntity);
            entities.add(secondCommentEntity);
            entities.add(thirdCommentEntity);

            PagedData<CommentEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<Comment> response = new ArrayList<>();

            Comment firstResponse = new Comment();
            firstResponse.setId(1L);
            firstResponse.setContent("Test Comment");

            Comment secondResponse = new Comment();
            secondResponse.setId(2L);
            secondResponse.setContent("Test Comment");

            Comment thirdResponse = new Comment();
            thirdResponse.setContent("Test Comment");
            thirdResponse.setId(3L);

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<Comment> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(commentDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<Comment> commentFindResponse = commentService.find(filterRequest).getPayload();

            Assertions.assertThat(commentFindResponse).as("Check all elements").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateComment() {
        try {

            EntityRequest<CommentCreateRequest> req = new EntityRequest<>();

            var newCommentRequest = new CommentCreateRequest();
            newCommentRequest.setContent("Test Comment");
            newCommentRequest.setCreatedBy("SOMEONE");
            newCommentRequest.setObjectId(3L);

            req.setEntity(newCommentRequest);

            var newCommentEnt = new CommentEntity();
            newCommentEnt.setContent("Test Comment");
            newCommentEnt.setCreatedBy("SOMEONE");
            newCommentEnt.setObjectId(3L);

            var newComment = new Comment();
            newComment.setContent("Test Comment");
            newComment.setCreatedBy("SOMEONE");
            newComment.setObjectId(3L);
            newComment.setStatus("Active");

            Mockito.when(commentDAO.persist(newCommentEnt)).thenReturn(null);

            PayloadResponse<Comment> commentFindResponse = commentService.create(req);

            Assertions.assertThat(commentFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl").isEqualTo(newComment);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateComment() {
        try {

            EntityRequest<CommentUpdateRequest> request = new EntityRequest<>();

            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setContent("Old Test Name 1");
            commentEntity.setId(22L);

            Comment commentResponse = new Comment();
            commentResponse.setContent("Update Test Name 1");
            commentResponse.setId(22L);

            CommentUpdateRequest updateCommentRequest = new CommentUpdateRequest();
            updateCommentRequest.setContent("Update Test Name 1");
            updateCommentRequest.setId(22L);
            request.setEntity(updateCommentRequest);

            Mockito.when(commentRequestValidation.validateUpdateCommentModelRequest(request)).thenReturn(null);

            Mockito.when(commentDAO.findByPK(request.getEntity().getId())).thenReturn(commentEntity);

            Mockito.doNothing().when(commentDAO).merge(commentEntity);

            var commentUpdateResponse = commentService.update(request);
            Assertions.assertThat(commentUpdateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(commentResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteComment() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(commentDAO).removeByPK(req.getEntity());

            var commentFindResponse = commentService.delete(req);

            Assertions.assertThat(commentFindResponse.getPayload()).isEqualTo("Comment with id 1 is successfully deleted!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
