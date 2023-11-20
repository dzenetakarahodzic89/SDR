package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.SampleService;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;
import ba.com.zira.sdr.dao.SampleDAO;
import lombok.AllArgsConstructor;

/**
 * SampleRequestValidation is used for validation of {@link SampleService}
 * requests.<br>
 * e.g. database validation needed
 *
 * @author zira
 *
 */
@AllArgsConstructor
@Component("sampleRequestValidation")
public class SampleRequestValidation {

    private SampleDAO sampleDAO;

    /**
     * Validates update SampleModel plan from {@link SampleService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateUpdateSampleModelRequest(final EntityRequest<SampleModelUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates exists SampleModel plan from {@link SampleService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateExistsSampleModelRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!sampleDAO.existsByPK(id)) {
            return ValidationError.of("SAMPLE_NOT_FOUND", "Sample with id: " + id + " does not exist!");
        }
        return null;
    }

}
