package ba.com.zira.sdr.api.model.country;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of country create request")
public class CountryCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of this country")
    private String name;

    @NotBlank
    @Schema(description = "Flag abbriviation for this country")
    private String flagAbbriviation;

    @Schema(description = "Region of the country")
    private String region;
}
