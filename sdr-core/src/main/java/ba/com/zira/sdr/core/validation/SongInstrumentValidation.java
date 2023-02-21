package ba.com.zira.sdr.core.validation;



import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.SongInstrumentService;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentUpdateRequest;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import lombok.AllArgsConstructor;

/**
 * SongInstrumentRequestValidation is used for validation of {@link SongInstrumentService}
 * requests.<br>
 * e.g. database validation needed
 *
 * @author zira
 *
 */
@AllArgsConstructor
@Component("songInstrumentValidation")
public class SongInstrumentValidation {

    private SongInstrumentDAO songInstrumentDAO;

    public ValidationResponse validateUpdateSongInstrumentRequest(final EntityRequest<SongInstrumentUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates exists SongInstrument plan from {@link SongInstrumentService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateExistsSongInstrumentRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));
        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!songInstrumentDAO.existsByPK(id)) {
            return ValidationError.of("SONGINSTRUMENT_NOT_FOUND", "SongInstrument with id: " + id + " does not exist!");
        }
        return null;

    }

    private ValidationError isEmpty(String content) {
        if (content == null || content.isEmpty()) {
            return ValidationError.of("CONTENT_EMPTY", "Content cannot be empty!");
        }
        return null;

    }
}

