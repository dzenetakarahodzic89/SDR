package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a notesheet response")
public class NoteSheet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the notesheet")
    private Long id;
    @Schema(description = "Content of notesheet")
    private String sheetContent;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the notesheet")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the notesheet") 
    private String modifiedBy;
    @Schema(description = "Type of the notation")
    private String notationType;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = " Id of the instrument")
    private Long instrumentId;
    @Schema(description = " Id of the song")
    private Long songId;
    
}
