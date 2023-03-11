package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Search filters for instrument")
public class InstrumentSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of the instrument")
    private String name;

    @Schema(description = "Name of the person playing instrument")
    private Long personId;

    @Schema(description = "Sorting method", allowableValues = { "NoOfPersons", "Alphabetical", "LastEdit" })
    private String sortBy;

}