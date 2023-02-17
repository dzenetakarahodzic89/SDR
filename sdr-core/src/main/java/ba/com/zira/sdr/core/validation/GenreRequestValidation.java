package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.genre.GenreModelCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreModelUpdateRequest;
import ba.com.zira.sdr.dao.GenreDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("genreRequestValidation")
public class GenreRequestValidation {
    private GenreDAO genreDAO;

    public ValidationResponse validateGenreModelCreateRequest(final EntityRequest<GenreModelCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(nameExists(request.getEntity().getName()));

        if (request.getEntity().getMainGenreID() != null) {
            errors.put(genreExists(request.getEntity().getMainGenreID()));
        }

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateGenreModelUpdateRequest(final EntityRequest<GenreModelUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(genreExists(request.getEntity().getId()));

        if (request.getEntity().getName() != null) {
            errors.put(nameExists(request.getEntity().getName(), request.getEntity().getId()));
        }

        if (request.getEntity().getMainGenreID() != null) {
            errors.put(genreExists(request.getEntity().getMainGenreID()));
        }

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsGenreModelRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(genreExists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError nameExists(String name) {

        if (genreDAO.existsByName(name)) {
            return ValidationError.of("GENRE_EXISTS", "Genre with name: " + name + " already exists!");
        }

        return null;
    }

    private ValidationError nameExists(String name, Long id) {

        if (genreDAO.existsByName(name, id)) {
            return ValidationError.of("GENRE_EXISTS", "Genre with name: " + name + " already exists!");
        }

        return null;
    }

    private ValidationError genreExists(Long id) {
        if (!genreDAO.existsByPK(id)) {
            return ValidationError.of("GENRE_NOT_FOUND", "Genre with id: " + id + " does not exist!");
        }
        return null;
    }

}
