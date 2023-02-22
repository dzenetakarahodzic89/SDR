package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.ShazamIntegrationDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("shazamIntegrationRequestValidation")
public class ShazamIntegrationRequestValidation {

	private ShazamIntegrationDAO shazamIntegrationDAO;

	public ValidationResponse validateUpdateShazamIntegrationRequest(
			final EntityRequest<ShazamIntegrationUpdateRequest> request) {
		ValidationErrors errors = new ValidationErrors();
		errors.put(exists(request.getEntity().getId()));

		return ValidationResponse.of(request, errors);
	}

	public ValidationResponse validateExistsShazamIntegrationRequest(final EntityRequest<Long> request) {
		ValidationErrors errors = new ValidationErrors();
		errors.put(exists(request.getEntity()));

		return ValidationResponse.of(request, errors);
	}

	private ValidationError exists(Long id) {
		if (!shazamIntegrationDAO.existsByPK(id)) {
			return ValidationError.of("SHAZAM_INTEGRATION_NOT_FOUND",
					"Shazam integration with id: " + id + " does not exist!");
		}
		return null;
	}
}