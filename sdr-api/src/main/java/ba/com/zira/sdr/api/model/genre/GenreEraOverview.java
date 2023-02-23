package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;
import java.util.Map;

import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Properties of era percentage overview")
public class GenreEraOverview implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the era")
    private Long eraId;
    @Schema(description = "Name of the era")
    private String eraName;
    @Schema(description = "Percentage distribution of genres accross the era")
    private Map<LoV, Double> genrePercentage;
}
