package ba.com.zira.sdr.api.model.userrecommendation;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "User Score Comparison Response Properties")
public class UserScoreResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the User recommendation")
    private Long id;

    @Schema(description = "Name of the User recommendation")
    private String name;

    @Schema(description = "The status of the User recommendation", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "User code")
    private String userCode;

    @Schema(description = "Average score")
    private Double averageScore;

    @Schema(description = "Song name")
    private String songName;

    @Schema(description = "Genre name")
    private String genreName;

    public UserScoreResponse(Long id, String name, String userCode, Double averageScore, String songName, String genreName) {
        super();
        this.id = id;
        this.name = name;
        this.userCode = userCode;
        this.averageScore = averageScore;
        this.songName = songName;
        this.genreName = genreName;
    }

    public UserScoreResponse(Long id, String name, String userCode) {
        super();
        this.id = id;
        this.name = name;
        this.userCode = userCode;
    }
}