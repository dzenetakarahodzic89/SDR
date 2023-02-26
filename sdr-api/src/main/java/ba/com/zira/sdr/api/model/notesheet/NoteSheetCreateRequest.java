package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data; 

@Data
@Schema(description = "Properties for creation of a notesheet")
public class NoteSheetCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Content of notesheet")
    private String sheetContent;
    @Schema(description = "notation type")
    private String notationType;
    @Schema(description = " Id of the instrument")
    private Long instrumentId;
    @Schema(description = " Id of the song")
    private Long songId;
}