package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a notesheet")
public class NoteSheetCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Content of notesheet")
    private NoteSheetContentResponse sheetContent;
    @Schema(description = " Id of the instrument")
    private Long instrumentId;
    @Schema(description = " Id of the song")
    private Long songId;

    public String getSheetContent() {
        N2bObjectMapper mapper = new N2bObjectMapper();
        try {
            return mapper.writeValueAsString(sheetContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public NoteSheetContentResponse getSheetContent(String content) {
        N2bObjectMapper mapper = new N2bObjectMapper();
        try {
            return mapper.readValue(content, NoteSheetContentResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSheetContent(NoteSheetContentResponse sheetContent) {
        this.sheetContent = sheetContent;
    }
}