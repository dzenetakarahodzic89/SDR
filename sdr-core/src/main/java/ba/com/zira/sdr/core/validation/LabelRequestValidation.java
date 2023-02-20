package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.LabelService;
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;
import ba.com.zira.sdr.dao.LabelDAO;
import lombok.AllArgsConstructor;

/**
 * LabelRequestValidation is used for validation of {@link LabelService}
 * requests.<br>
 * e.g. database validation needed
 *
 * @author zira
 *
 */

@AllArgsConstructor
@Component("labelRequestValidation")
public class LabelRequestValidation {

    private LabelDAO labelDAO;

    /**
     * Validates update SampleModel plan from {@link LabelService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateUpdateLabelRequest(final EntityRequest<LabelUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates exists SampleModel plan from {@link LabelService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateExistsLabelRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!labelDAO.existsByPK(id)) {
            return ValidationError.of("LABEL_NOT_FOUND", "Label with id: " + id + " does not exist!");
        }
        return null;
    }
}
