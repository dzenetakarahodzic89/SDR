package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a comment")
public class NoteSheetUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the notesheet")
    private Long id;
    @NotBlank
    @Schema(description = "Content of notesheet")
    private String sheetContent;
    @Schema(description = "Id of the object")
    private String notationType;
    @Schema(description = " Id of the instrument")
    private Long instrumentId;
    @Schema(description = " Id of the song")
    private Long songId;
}