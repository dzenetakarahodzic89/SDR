package ba.com.zira.sdr.api.model.userrecommendationdetail;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response of top 10 rated songs")
public class ResponseUserRecommendedDetailSongs implements Serializable {
    private static final long serialVersionUID = 1L;


    @Schema(description = "Name of song")
    private String songName;

    @Schema(description = "User score of the song")
    private Double songScore;

    public ResponseUserRecommendedDetailSongs(String songName, Double songScore)
    {
        this.songName=songName;
        this.songScore=songScore;
    }



}
