package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.MoritsIntegrationDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("moritsIntegrationRequestValidation")
public class MoritsIntegrationRequestValidation {

    private MoritsIntegrationDAO moritsIntegrationDAO;

    public ValidationResponse validateUpdateMoritsIntegrationRequest(final EntityRequest<MoritsIntegrationUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsMoritsIntegrationRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!moritsIntegrationDAO.existsByPK(id)) {
            return ValidationError.of("MORITS_INTEGRATION_NOT_FOUND", "Morits lyric integration with id: " + id + " does not exist!");
        }
        return null;
    }
}
