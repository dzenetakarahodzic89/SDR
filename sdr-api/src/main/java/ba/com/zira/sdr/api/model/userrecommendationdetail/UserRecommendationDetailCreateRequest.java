package ba.com.zira.sdr.api.model.userrecommendationdetail;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of user recommendation detail create request")
public class UserRecommendationDetailCreateRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Schema(description = "Id of a song that the artist wrote")
	private Long songId;

	@Schema(description = "Id of a user recommendation")
	private Long userRecommendationId;

	@Schema(description = "Name of user recommendation detail")
	private String userRecommendationDetailName;

	@Schema(description = "User score")
	private BigDecimal userScore;

}
