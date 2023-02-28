package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailCreateRequest;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("connectedMediaDetailRequestValidation")
public class ConnectedMediaDetailRequestValidation {
    private ConnectedMediaDAO connectedMediaDAO;

    public ValidationResponse validateConnectedMediaDetailCreateRequest(final EntityRequest<ConnectedMediaDetailCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(connectedMediaExists(request.getEntity().getConnectedMediaId()));
        return ValidationResponse.of(request, errors);
    }

    private ValidationError connectedMediaExists(Long connectedMediaId) {
        if (!connectedMediaDAO.existsByPK(connectedMediaId)) {
            return ValidationError.of("CONNECTED_MEDIA_NOT_FOUND", "Connected media with id: " + connectedMediaId + " does not exist!");
        }
        return null;
    }

}
