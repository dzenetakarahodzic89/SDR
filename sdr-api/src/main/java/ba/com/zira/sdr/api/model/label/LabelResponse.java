package ba.com.zira.sdr.api.model.label;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an Label response")
public class LabelResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the label")
    private Long id;
    @Schema(description = "Created at")
    private LocalDateTime created;
    @Schema(description = "Created by")
    private String createdBy;
    @Schema(description = "Founding date")
    private LocalDateTime foundingDate;
    @Schema(description = "Information about label")
    private String information;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the label")
    private String modifiedBy;
    @Schema(description = "Name of the label")
    private String labelName;
    @Schema(description = "The status of the label", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Founder of the label")
    private Long founderId;
    @Schema(description = "Label outline text")
    private String outlineText;

}
