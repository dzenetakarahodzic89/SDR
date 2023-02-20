package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.dao.PersonDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("personRequestValidation")
public class PersonRequestValidation {

	private PersonDAO personDAO;

	public ValidationResponse validateUpdatePersonRequest(final EntityRequest<PersonUpdateRequest> request) {
		ValidationErrors errors = new ValidationErrors();
		errors.put(exists(request.getEntity().getId()));

		return ValidationResponse.of(request, errors);
	}

	public ValidationResponse validateExistsPersonRequest(final EntityRequest<Long> request) {
		ValidationErrors errors = new ValidationErrors();
		errors.put(exists(request.getEntity()));

		return ValidationResponse.of(request, errors);
	}

	private ValidationError exists(Long id) {
		if (!personDAO.existsByPK(id)) {
			return ValidationError.of("PERSON_NOT_FOUND", "Person with id: " + id + " does not exist!");
		}
		return null;
	}

}
