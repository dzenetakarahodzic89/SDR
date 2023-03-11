package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.DeezerIntegrationDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("deezerIntegrationRequestValidation")
public class DeezerIntegrationRequestValidation {

    private DeezerIntegrationDAO deezerIntegrationDAO;

    public ValidationResponse validateUpdateDeezerIntegrationRequest(final EntityRequest<DeezerIntegrationUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsDeezerIntegrationRequest(final EntityRequest<String> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(String id) {
        if (!deezerIntegrationDAO.existsByPK(id)) {
            return ValidationError.of("DEEZER_INTEGRATION_NOT_FOUND", "Deezer integration with id: " + id + " does not exist!");
        }
        return null;
    }

}
