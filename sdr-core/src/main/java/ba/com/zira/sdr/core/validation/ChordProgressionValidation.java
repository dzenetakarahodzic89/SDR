package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("chordProgressionValidation")
public class ChordProgressionValidation {
    ChordProgressionDAO chordProgressionDAO;

    // public ValidationResponse validateUpdateChordProgressionRequest(final
    // EntityRequest<ChordProgressionUpdateRequest> request) {
    // ValidationErrors errors = new ValidationErrors();
    // errors.put(exists(request.getEntity().getId()));
    //
    // return ValidationResponse.of(request, errors);
    // }

    public ValidationResponse validateExistsChordProgressionRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!chordProgressionDAO.existsByPK(id)) {
            return ValidationError.of("CHORD_PROGRESSION_NOT_FOUND", "Chord progression with id: " + id + " does not exist!");
        }
        return null;
    }
}
