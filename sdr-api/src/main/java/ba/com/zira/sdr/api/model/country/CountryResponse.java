package ba.com.zira.sdr.api.model.country;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Properties of country response")
public class CountryResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of country record")
    private Long id;

    @Schema(description = "Name of this country")
    private String name;
    @Schema(description = "Unique identifier of country record")
    private String flagAbbriviation;
    @Schema(description = "Region of this country")
    private String region;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created this country")
    private String createdBy;
    private Long numberOfCountries;

    public CountryResponse(Long id, String name, String flagAbbriviation, String region) {
        this.id = id;
        this.name = name;
        this.flagAbbriviation = flagAbbriviation;
        this.region = region;
    }

    public CountryResponse(Long id, String name, Long numberOfActiveCountries) {
        this.id = id;
        this.name = name;
        this.numberOfCountries = numberOfActiveCountries;
    }

    public CountryResponse(Long id) {
        this.id = id;
    }

}
