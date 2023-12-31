package ba.com.zira.sdr.api.model.label;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for updating label")
public class LabelUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @NotNull
    @Schema(description = "Unique identifier of the label")
    private long id;

    @Schema(description = "Information about label")
    private String information;

    @NotBlank
    @Schema(description = "Name of the label")
    private String labelName;

    @Schema(description = "Label founding date")
    private LocalDateTime foundingDate;

    @Schema(description = "Label founder")
    private Long founderId;

    @Schema(description = "Label outline text")
    private String outlineText;

    private String coverImage;

    private String coverImageData;

}
