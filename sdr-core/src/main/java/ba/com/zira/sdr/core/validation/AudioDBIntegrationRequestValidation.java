package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.AudioDBIntegrationDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("audioDBIntegrationRequestValidation")
public class AudioDBIntegrationRequestValidation {

    private AudioDBIntegrationDAO audioDBIntegrationDAO;

    public ValidationResponse validateUpdateAudioDBIntegrationRequest(final EntityRequest<AudioDBIntegrationUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsAudioDBIntegrationRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!audioDBIntegrationDAO.existsByPK(id)) {
            return ValidationError.of("SAMPLE_NOT_FOUND", "Sample with id: " + id + " does not exist!");
        }
        return null;
    }

}
