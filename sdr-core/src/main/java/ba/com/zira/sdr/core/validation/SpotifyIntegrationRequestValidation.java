package ba.com.zira.sdr.core.validation;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.SpotifyIntegrationDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("spotifyIntegrationRequestValidation")
public class SpotifyIntegrationRequestValidation {

    private SpotifyIntegrationDAO spotifyIntegrationDAO;

    public ValidationResponse validateCreateSpotifyIntegrationRequest(final EntityRequest<SpotifyIntegrationCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(validObjectType(request.getEntity().getObjectType()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateUpdateSpotifyIntegrationRequest(final EntityRequest<SpotifyIntegrationUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));
        errors.put(validObjectType(request.getEntity().getObjectType()));

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

    private ValidationError validObjectType(String objectType) {
        if (!EnumUtils.isValidEnum(ObjectType.class, objectType)) {
            return ValidationError.of("OBJECT_TYPE_INVALID", "Object type: " + objectType + " is not valid!");
        }
        return null;
    }
}
