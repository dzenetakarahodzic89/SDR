package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("personArtistRequestValidation")
public class PersonArtistRequestValidation {

    private PersonArtistDAO personArtistDAO;

    private PersonDAO personDAO;
    private ArtistDAO artistDAO;

    public ValidationResponse validateCreatePersonArtistRequest(final EntityRequest<PersonArtistCreateRequest> entityRequest) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(exists(personDAO, entityRequest.getEntity().getPersonId(), "PERSON_FK_NOT_FOUND", "Person"));
        errors.put(exists(artistDAO, entityRequest.getEntity().getArtistId(), "ARTIST_FK_NOT_FOUND", "Artist"));

        return ValidationResponse.of(entityRequest, errors);
    }

    private ValidationError exists(AbstractDAO<?, Long> dao, Long id, String errorName, String tableName) {
        if (!dao.existsByPK(id)) {
            return ValidationError.of(errorName, tableName + " with id: " + id + " does not exist!");
        }
        return null;
    }

    public ValidationResponse validateDeletePersonArtistRequest(final EntityRequest<Long> entityRequest) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(personArtistDAO, entityRequest.getEntity(), "PERSON_ARTIST_NOT_FOUND", "Person-Artist"));

        return ValidationResponse.of(entityRequest, errors);
    }

}
