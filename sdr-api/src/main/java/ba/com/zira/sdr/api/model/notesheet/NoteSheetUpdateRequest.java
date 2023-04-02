package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a comment")
public class NoteSheetUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the notesheet")
    private Long id;

    @Schema(description = "Content of notesheet")
    private NoteSheetContentResponse sheetContent;
    @Schema(description = " Id of the instrument")
    private Long instrumentId;
    @Schema(description = " Id of the song")
    private Long songId;

    public String getSheetContent() {
        N2bObjectMapper mapper = new N2bObjectMapper();
        String sheetContentString = null;
        try {
            sheetContentString = mapper.writeValueAsString(sheetContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sheetContentString;
    }
}