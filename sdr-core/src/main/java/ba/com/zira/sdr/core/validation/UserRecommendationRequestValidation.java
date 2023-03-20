package ba.com.zira.sdr.core.validation;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
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

    public ValidationResponse validateAverageScorePerCountryRequest(final EntityRequest<Pair<String, String>> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(existsUserCode(request.getEntity().getKey()));
        errors.put(validateRecommendationServiceName(request.getEntity().getValue()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!userRecommendationDAO.existsByPK(id)) {
            return ValidationError.of("UserRecommendation_NOT_FOUND", "User recommendation with id: " + id + " does not exist!");
        }
        return null;
    }

    private ValidationError existsUserCode(String userCode) {
        if (Boolean.FALSE.equals(userRecommendationDAO.existsUserCode(userCode))) {
            return ValidationError.of("UserCode_NOT_FOUND", "User with user code: " + userCode + " does not exist!");
        }
        return null;
    }

    private ValidationError validateRecommendationServiceName(String recommendationService) {
        List<String> listOfServices = Arrays.asList("SDR", "Spotify", "Deezer", "Tidal", "Youtube Music", "iTunes", "Google Play");
        if (!listOfServices.contains(recommendationService)) {
            return ValidationError.of("RecommendationService_NOT_FOUND",
                    "Recommendation service: " + recommendationService + " does not exist!");
        }
        return null;
    }

}
