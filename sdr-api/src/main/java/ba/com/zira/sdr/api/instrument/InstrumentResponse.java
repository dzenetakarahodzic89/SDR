package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an Sample response")
public class InstrumentResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the sample")

    private Long id;

    @Schema(description = "Creation date")
    private LocalDateTime created;

    @Schema(description = "User that created the instrument")
    private String createdBy;

    @Schema(description = "Instrument name")
    private String instrumentName;
    @Schema(description = "Instrument information")
    private String instrumentInformation;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the instrument")
    private String modifiedBy;

    @Schema(description = "Instrument status", allowableValues = { "Inactive", "Active" })
    private String instrumentStatus;

    @Schema(description = "Instrument type")
    private String instrumentType;

    @Schema(description = "Instrument outline text")
    private String instrumentOutlineText;

}
