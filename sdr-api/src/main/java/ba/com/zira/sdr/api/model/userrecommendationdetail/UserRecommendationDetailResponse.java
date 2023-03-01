package ba.com.zira.sdr.api.model.userrecommendationdetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of user-recommendation-detail response")

public class UserRecommendationDetailResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "Unique identifier of user-recommendation-detail record")
	private Long id;

	@Schema(description = "Id of a song that the artist wrote")
	private Long songId;

	@Schema(description = "Id of a user recommendation ")
	private Long userRecommendationId;

	@Schema(description = "Creation date")
	private LocalDateTime created;

	@Schema(description = "User that created the sample")
	private String createdBy;

	@Schema(description = "Name of user recommendation detail ")
	private String userRecommendationDetailName;

	@Schema(description = "The status of the user recommendation detail", allowableValues = { "Inactive", "Active" })
	private String status;

	@Schema(description = "User score")
	private BigDecimal userScore;

	
	}

