package ba.com.zira.sdr.api.model.moritsintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an morits lyric integration response")
public class MoritsIntegration implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the morits lyric integration")
    private Long id;
    @Schema(description = "Name of the morits lyric integration")
    private String name;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the morits lyric integration")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the morits lyric integration")
    private String modifiedBy;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Request of the morits lyric integration")
    private String request;
    @Schema(description = "Response of the morits lyric integration")
    private String response;
}
