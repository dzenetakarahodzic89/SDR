package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("mediaStoreRequestValidation")
public class MediaStoreRequestValidation {

    private MediaStoreDAO mediaStoreDAO;

    public ValidationResponse validateExistsMediaStoreRequest(final EntityRequest<String> request) {
        var errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(String id) {
        if (!mediaStoreDAO.existsByPK(id)) {
            return ValidationError.of("MEDIASTORE_NOT_FOUND", "Media store with id: " + id + " does not exist!");
        }
        return null;
    }

}
