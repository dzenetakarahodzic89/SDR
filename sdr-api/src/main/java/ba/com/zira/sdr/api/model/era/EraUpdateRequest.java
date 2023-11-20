package ba.com.zira.sdr.api.model.era;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a era")
public class EraUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the era")
    private Long id;
    @NotBlank
    @Schema(description = "Name of the era")
    private String name;
    @Schema(description = "Information of the era")
    private String information;
    @NotNull
    @Schema(description = "Start date of the era")
    private LocalDateTime startDate;
    @NotNull
    @Schema(description = "End date of the era")
    private LocalDateTime endDate;
    @NotBlank
    @Schema(description = "Scope of the era")
    private String scope;
    @NotBlank
    @Schema(description = "Era outline text")
    private String outlinetext;
    private String coverImageData;
    private String coverImage;
}
