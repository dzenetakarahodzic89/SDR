package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.LyricService;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;
import ba.com.zira.sdr.dao.LyricDAO;
import lombok.AllArgsConstructor;

/**
 * LyricRequestValidation is used for validation of {@link LyricService}
 * requests.<br>
 * e.g. database validation needed
 *
 * @author zira
 *
 */
@AllArgsConstructor
@Component("lyricRequestValidation")
public class LyricRequestValidation {

    private LyricDAO lyricDAO;

    public ValidationResponse validateCreateLyricRequest(final EntityRequest<LyricCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateUpdateLyricRequest(final EntityRequest<LyricUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsLyricRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!lyricDAO.existsByPK(id)) {
            return ValidationError.of("LYRIC_NOT_FOUND", "Lyric with id: " + id + " does not exist!");
        }
        return null;
    }

}
