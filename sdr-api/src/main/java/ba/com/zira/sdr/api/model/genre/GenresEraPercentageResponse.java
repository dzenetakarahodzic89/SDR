package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Properties of era percentage overview")
public class GenresEraPercentageResponse implements Serializable {

    @Schema(description = "Genre Percent for era")
    private Double genrePercentage;
    @Schema(description = "Genre name for era")
    private String genreName;

    public GenresEraPercentageResponse(String genreName, Double percentage) {

        this.genreName = genreName;
        this.genrePercentage = percentage;
    }
}
