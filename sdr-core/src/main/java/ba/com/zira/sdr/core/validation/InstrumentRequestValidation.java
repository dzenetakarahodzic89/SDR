package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.dao.InstrumentDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("instrumentRequestValidation")
public class InstrumentRequestValidation {

    private InstrumentDAO instrumentDAO;

    public ValidationResponse validateUpdateInstrumentRequest(final EntityRequest<InstrumentUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateFilterInstrumentRequest(final EntityRequest<InstrumentResponse> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsInstrumentRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!instrumentDAO.existsByPK(id)) {
            return ValidationError.of("INSTRUMENT_NOT_FOUND", "Instrument with id: " + id + " does not exist!");
        }
        return null;
    }

}
