package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.dao.GenreDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("genreRequestValidation")
public class GenreRequestValidation {
    private GenreDAO genreDAO;

    public ValidationResponse validateGenreCreateRequest(final EntityRequest<GenreCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(nameExists(request.getEntity().getName()));

        if (request.getEntity().getMainGenreID() != null) {
            errors.put(genreExists(request.getEntity().getMainGenreID()));
        }

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateGenreUpdateRequest(final EntityRequest<GenreUpdateRequest> request) {
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

    public ValidationResponse validateExistsGenreRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(genreExists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateGenreDeleteRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(genreExists(request.getEntity()));
        errors.put(subGenresExist(request.getEntity()));
        errors.put(songsExist(request.getEntity()));

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

    private ValidationError subGenresExist(Long id) {
        if (genreDAO.subGenresExist(id)) {
            return ValidationError.of("SUBGENRES_EXIST", "Genres with subgenres attached to them are not allowed to be deleted.");
        }
        return null;
    }

    private ValidationError songsExist(Long id) {
        if (genreDAO.songsExist(id)) {
            return ValidationError.of("SONGS_EXIST", "Genres with songs attached to them are not allowed to be deleted.");
        }

        return null;
    }

}
