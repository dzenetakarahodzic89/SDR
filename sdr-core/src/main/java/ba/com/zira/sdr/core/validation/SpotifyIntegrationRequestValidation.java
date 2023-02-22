package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.SpotifyIntegrationDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("spotifyIntegrationRequestValidation")
public class SpotifyIntegrationRequestValidation {

    private SpotifyIntegrationDAO spotifyIntegrationDAO;

    public ValidationResponse validateUpdateSpotifyIntegrationRequest(final EntityRequest<SpotifyIntegrationUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsSpotifyIntegrationRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!spotifyIntegrationDAO.existsByPK(id)) {
            return ValidationError.of("SPOTIFY_INTEGRATION_NOT_FOUND", "Spotify integration with id: " + id + " does not exist!");
        }
        return null;
    }
}
