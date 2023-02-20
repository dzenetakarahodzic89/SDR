package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.EraDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("albumRequestValidation")
public class AlbumRequestValidation {
    private EraDAO eraDAO;
    private AlbumDAO albumDAO;

    public ValidationResponse validateCreateAlbumRequest(final EntityRequest<AlbumCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(eraDAO, request.getEntity().getEraId(), "ERA_NOT_EXIST", "ERA"));
        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateUpdateAlbumRequest(final EntityRequest<AlbumUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(albumDAO, request.getEntity().getId(), "ALBUM_NOT_FOUND", "ALBUM"));
        errors.put(exists(eraDAO, request.getEntity().getEraId(), "ERA_NOT_FOUND", "ERA"));
        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateDeleteAlbumRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(albumDAO, request.getEntity(), "ALBUM_NOT_FOUND", "ALBUM"));
        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(final AbstractDAO<?, Long> dao, final Long id, final String msg, final String entity) {
        if (!dao.existsByPK(id)) {
            return ValidationError.of(msg, entity + " with id: " + id + " does not exist!");
        }
        return null;
    }
}
