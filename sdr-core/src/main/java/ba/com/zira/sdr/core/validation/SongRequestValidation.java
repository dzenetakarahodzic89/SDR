package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.SongService;
import ba.com.zira.sdr.api.model.generateplaylist.SavePlaylistRequest;
import ba.com.zira.sdr.api.model.song.SongUpdateRequest;
import ba.com.zira.sdr.dao.SongDAO;
import lombok.AllArgsConstructor;

/**
 * SampleRequestValidation is used for validation of {@link SongService}
 * requests.<br>
 * e.g. database validation needed
 *
 * @author Faris
 *
 */
@AllArgsConstructor
@Component("songRequestValidation")
public class SongRequestValidation {

    private SongDAO songDAO;

    /**
     * Validates update SongModel plan from {@link SongService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */

    public ValidationResponse validateUpdateSongRequest(final EntityRequest<SongUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates exists SampleModel plan from {@link SongService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateExistsSongRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsSongsInRequest(final EntityRequest<SavePlaylistRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        for (Long songId : request.getEntity().getSongIds()) {
            errors.put(exists(songId));
        }
        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(final Long id) {
        if (!songDAO.existsByPK(id)) {
            return ValidationError.of("SONG_NOT_FOUND", "Song with id: " + id + " does not exist!");
        }
        return null;
    }

}
