package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.PlaylistService;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.dao.PlaylistDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("playlistRequestValidation")
public class PlaylistRequestValidation {

    private PlaylistDAO playlistDAO;

    /**
     * Validates update Playlist plan from {@link PlaylistService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateUpdatePlaylistRequest(final EntityRequest<PlaylistUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates exists Playlist plan from {@link PlaylistService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     *
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateExistsPlaylistRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!playlistDAO.existsByPK(id)) {
            return ValidationError.of("PLAYLIST_NOT_FOUND", "Playlist with id: " + id + " does not exist!");
        }
        return null;
    }
}
