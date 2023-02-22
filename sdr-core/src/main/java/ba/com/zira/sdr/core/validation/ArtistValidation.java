package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.dao.ArtistDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("ArtistValidation")
public class ArtistValidation {

    private ArtistDAO artistDAO;

    public ValidationResponse validateUpdateArtistRequest(final EntityRequest<ArtistUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsArtistRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!artistDAO.existsByPK(id)) {
            return ValidationError.of("SAMPLE_NOT_FOUND", "Artist with id: " + id + " does not exist!");
        }
        return null;
    }

}
