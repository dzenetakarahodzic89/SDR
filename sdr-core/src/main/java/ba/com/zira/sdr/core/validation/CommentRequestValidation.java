package ba.com.zira.sdr.core.validation;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.CommentService;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.dao.CommentDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * CommentRequestValidation is used for validation of {@link CommentService} requests.<br> e.g. database validation needed
 *
 * @author zira
 */
@AllArgsConstructor
@Component("commentRequestValidation")
public class CommentRequestValidation {

    private CommentDAO commentDAO;

    /**
     * Validates update CommentModel plan from {@link CommentService}.
     *
     * @param request
     *         the {@link EntityRequest} to validate.
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateUpdateCommentModelRequest(final EntityRequest<CommentUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates exists CommentModel plan from {@link CommentService}.
     *
     * @param request
     *         the {@link EntityRequest} to validate.
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateExistsCommentModelRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));
        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!commentDAO.existsByPK(id)) {
            return ValidationError.of("COMMENT_NOT_FOUND", "Comment with id: " + id + " does not exist!");
        }
        return null;

    }

}
