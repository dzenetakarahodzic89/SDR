package ba.com.zira.sdr.comment.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.CommentService;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.api.model.comment.CommentsFetchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "comment", description = "Comment API")
@RestController
@RequestMapping(value = "comment")
@AllArgsConstructor
public class CommentRestService {

    private CommentService commentService;

    @Operation(summary = "Find Comment base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<Comment> find(@RequestParam final Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return commentService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create comment")
    @PostMapping
    public PayloadResponse<Comment> create(@RequestBody final CommentCreateRequest comment) throws ApiException {
        return commentService.create(new EntityRequest<>(comment));
    }

    @Operation(summary = "Update Comment")
    @PutMapping(value = "{id}")
    public PayloadResponse<Comment> edit(@Parameter(required = true, description = "ID of the comment") @PathVariable final Long id,
            @RequestBody final CommentUpdateRequest comment) throws ApiException {
        if (comment != null) {
            comment.setId(id);
        }
        return commentService.update(new EntityRequest<>(comment));
    }

    @Operation(summary = "Delete comment")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the sample") @PathVariable final Long id)
            throws ApiException {
        return commentService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "Fetch comments")
    @PostMapping(value = "/fetch")
    public ListPayloadResponse<Comment> fetchComments(@RequestBody final CommentsFetchRequest request) throws ApiException {
        return commentService.fetchComments(new EntityRequest<>(request));
    }

}