package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.UserRecommendationDetailDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("userRecommendationDetailRequestValidation")
public class UserRecommendationDetailRequestValidation {
	private UserRecommendationDetailDAO userRecommendationDetailDAO;
	private UserRecommendationDAO userRecommendationDAO;
	private SongDAO songDAO;

	public ValidationResponse validateCreateUserRecommendationDetailRequest(
			final EntityRequest<UserRecommendationDetailCreateRequest> entityRequest) {
		ValidationErrors errors = new ValidationErrors();

		errors.put(exists(userRecommendationDAO, entityRequest.getEntity().getUserRecommendationId(),
				"USER_RECOMMENDATION_FK_NOT_FOUND", "User-recommendation"));

		errors.put(exists(songDAO, entityRequest.getEntity().getSongId(), "SONG_FK_NOT_FOUND", "Song"));

		return ValidationResponse.of(entityRequest, errors);
	}

	public ValidationResponse validateDeleteUserRecommendationDetailRequest(final EntityRequest<Long> entityRequest) {
		ValidationErrors errors = new ValidationErrors();
		errors.put(exists(userRecommendatioDetailDAO, entityRequest.getEntity(), "USER_RECOMMENDATIO_DETAIL_NOT_FOUND",
				"User-recommendation-detail"));

		return ValidationResponse.of(entityRequest, errors);
	}

	private ValidationError exists(AbstractDAO<?, Long> dao, Long id, String errorName, String tableName) {
		if (!dao.existsByPK(id)) {
			return ValidationError.of(errorName, tableName + " with id: " + id + " does not exist!");
		}
		return null;
	}

}
