package ba.com.zira.sdr.core.validation;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaCreateRequest;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaUpdateRequest;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("connectedMediaRequestValidation")
public class ConnectedMediaRequestValidation {
    private ConnectedMediaDAO connectedMediaDAO;

    public ValidationResponse validateConnectedMediaCreateRequest(final EntityRequest<ConnectedMediaCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(objectTypeValid(request.getEntity().getObjectType()));
        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateConnectedMediaUpdateRequest(final EntityRequest<ConnectedMediaUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(connectedMediaExists(request.getEntity().getId()));
        errors.put(objectTypeValid(request.getEntity().getObjectType()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateConnectedMediaDeleteRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(connectedMediaExists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError connectedMediaExists(Long id) {
        if (!connectedMediaDAO.existsByPK(id)) {
            return ValidationError.of("CONNECTED_MEDIA_NOT_FOUND", "Connected media with id: " + id + " does not exist!");
        }
        return null;
    }

    private ValidationError objectTypeValid(String objectType) {
        if (!EnumUtils.isValidEnum(ObjectType.class, objectType)) {
            return ValidationError.of("INVALID_OBJECT_TYPE", "Object type " + objectType + " does not exist!");
        }
        return null;
    }

}
