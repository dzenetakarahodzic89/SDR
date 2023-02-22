package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("userRecommendationRequestValidation")
public class UserRecommendationRequestValidation {

    private UserRecommendationDAO userRecommendationDAO;

    public ValidationResponse validateExistsUserRecommendationRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!userRecommendationDAO.existsByPK(id)) {
            return ValidationError.of("UserRecommendation_NOT_FOUND", "User recommendation with id: " + id + " does not exist!");
        }
        return null;
    }

}
