package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;
import java.util.List;

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
    @Schema(description = "List of genres for ")
    private List<GenresEraPercentageResponse> genreList;

    @Schema(description = "List of labeldata ")
    private List<String> labeldata;
    @Schema(description = "List of labeldata ")
    private List<Double> realdata;

    public GenreEraOverview(List<GenresEraPercentageResponse> list, Long id, String name, List<String> ld, List<Double> listaprocenata) {
        super();
        this.genreList = list;
        this.eraId = id;
        this.eraName = name;
        this.labeldata = ld;
        this.realdata = listaprocenata;
    }

}
