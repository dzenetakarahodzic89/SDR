package ba.com.zira.sdr.api.model.label;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Search filters for labels")
public class LabelSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of the label")
    private String name;

    @Schema(description = "Id of the founder")
    private Long founder;

    @Schema(description = "Sorting method", allowableValues = { "NoOfArtists", "Alphabetical", "LastEdit" })
    private String sortBy;

}