package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.dao.CountryDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("countryRequestValidation")
public class CountryRequestValidation {
    private CountryDAO countryDAO;

    public ValidationResponse validateUpdateCountryRequest(final EntityRequest<CountryUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateDeleteCountryRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!countryDAO.existsByPK(id)) {
            return ValidationError.of("COUNTRY_NOT_FOUND", "Country with id: " + id + " does not exist!");
        }
        return null;
    }
}
