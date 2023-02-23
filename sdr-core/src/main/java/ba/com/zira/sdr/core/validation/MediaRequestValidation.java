package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.dao.MediaDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("mediaRequestValidation")
public class MediaRequestValidation {

    private MediaDAO mediaDAO;

    public ValidationResponse validateCreateMediaRequest(final EntityRequest<MediaCreateRequest> request) {
        var errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getObjectId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsMediaRequest(final EntityRequest<Long> request) {
        var errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!mediaDAO.existsByPK(id)) {
            return ValidationError.of("MEDIA_NOT_FOUND", "Media with id: " + id + " does not exist!");
        }
        return null;
    }

    public ValidationResponse validateImageName(EntityRequest<MediaCreateRequest> request) {
        if (request.getEntity().getMediaObjectName().length() < 60) {
            return null;
        }
        var errors = new ValidationErrors();
        errors.put(ValidationError.of("NAME_TOO_LONG", "Image name too long"));

        return ValidationResponse.of(request, errors);
    }

}
