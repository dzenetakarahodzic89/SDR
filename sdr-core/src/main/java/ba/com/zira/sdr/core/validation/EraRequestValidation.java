package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.era.EraUpdateRequest;
import ba.com.zira.sdr.dao.EraDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("eraRequestValidation")
public class EraRequestValidation {

    private EraDAO eraDAO;

    public ValidationResponse validateUpdateEraRequest(final EntityRequest<EraUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsEraRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!eraDAO.existsByPK(id)) {
            return ValidationError.of("ERA_NOT_FOUND", "Era with id: " + id + " does not exist!");
        }
        return null;
    }
}
