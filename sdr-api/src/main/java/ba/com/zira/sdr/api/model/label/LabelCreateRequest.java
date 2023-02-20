package ba.com.zira.sdr.api.model.label;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a label")
public class LabelCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Information about label")
    private String information;
    @NotBlank
    @Schema(description = "Name of the label")
    private String labelName;
    @Schema(description = "Label founding date")
    private LocalDateTime foundingDate;
    @Schema(description = "Label founder")
    private Long founderId;

}
