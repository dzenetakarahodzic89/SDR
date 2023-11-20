package ba.com.zira.sdr.core.validation;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.enums.ConnectedMediaConnectionSource;
import ba.com.zira.sdr.api.enums.ConnectedMediaConnectionType;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailCreateRequest;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("connectedMediaDetailRequestValidation")
public class ConnectedMediaDetailRequestValidation {
    private ConnectedMediaDAO connectedMediaDAO;

    public ValidationResponse validateConnectedMediaDetailCreateRequest(final EntityRequest<ConnectedMediaDetailCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(connectionTypeValid(request.getEntity().getConnectionType()));
        errors.put(connectionSourceValid(request.getEntity().getConnectionSource()));
        return ValidationResponse.of(request, errors);
    }

    private ValidationError connectionTypeValid(String connectionType) {
        if (!EnumUtils.isValidEnum(ConnectedMediaConnectionType.class, connectionType)) {
            return ValidationError.of("INVALID_CONNECTED_MEDIA_CONNECTION_TYPE", "Connection type " + connectionType + " does not exist!");
        }
        return null;
    }

    private ValidationError connectionSourceValid(String connectionSource) {
        if (!EnumUtils.isValidEnum(ConnectedMediaConnectionSource.class, connectionSource)) {
            return ValidationError.of("INVALID_CONNECTED_MEDIA_CONNECTION_SOURCE",
                    "Connection source " + connectionSource + " does not exist!");
        }
        return null;
    }

}
