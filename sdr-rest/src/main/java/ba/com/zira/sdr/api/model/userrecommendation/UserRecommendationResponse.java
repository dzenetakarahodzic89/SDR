package ba.com.zira.sdr.api.model.userrecommendation;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an User recommendation response")
public class UserRecommendationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the User recommendation")
    private Long id;

    @Schema(description = "Created at")
    private LocalDateTime created;

    @Schema(description = "Created by")
    private String createdBy;

    @Schema(description = "Description of the User recommendation")
    private String description;

    @Schema(description = "Name of the User recommendation")
    private String name;

    @Schema(description = "The status of the User recommendation", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "User code")
    private String userCode;

    @Schema(description = "Genre name")
    private String genreName;

    @Schema(description = "User Score")
    private Double userScore;

    @Schema(description = "Song name")
    private String songName;

    public UserRecommendationResponse(Long id, String name, String userCode, Double userScore, String songName) {
        super();
        this.id = id;
        this.name = name;
        this.userCode = userCode;
        this.userScore = userScore;
        this.songName = songName;

    }

    public UserRecommendationResponse(Long id, String userCode) {
        super();
        this.id = id;
        this.userCode = userCode;
    }

}
