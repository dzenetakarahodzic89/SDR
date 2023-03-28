package ba.com.zira.sdr.api.model.userrecommendation;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Average user recommendation score per country origin of songs")
public class AverageScorePerCountry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Country name")
    private String country;

    @Schema(description = "Average user recommendation score")
    private Double score;

}
