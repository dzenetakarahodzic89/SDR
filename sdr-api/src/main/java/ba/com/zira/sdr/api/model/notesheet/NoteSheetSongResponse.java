package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.sdr.api.artist.ArtistSheetResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of response for note sheets for song")
public class NoteSheetSongResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "Unique identifier of the note sheet")
    private Long id;
    @Schema(description = "Instrument name")
    private String instrumentName;
    @Schema(description = "Instrument id")
    private Long instrumentId;
    @Schema(description = "Date of release")
    private LocalDateTime dateOfRelease;
    @Schema(description = "Song name")
    private String songName;
    @Schema(description = "songId")
    private Long songId;
    @Schema(description = "Sheet content")
    private NoteSheetContentResponse sheetContent;
    private ArtistSheetResponse artists;
    private String audioUrl;
    private String imageUrl;

    public NoteSheetSongResponse(Long id, String instrument, Long instrumentId, LocalDateTime dateOfRelease, Long songId, String songName,
            String sheetContent) {
        super();
        this.id = id;
        this.instrumentId = instrumentId;
        this.instrumentName = instrument;
        this.dateOfRelease = dateOfRelease;
        this.songId = songId;
        this.songName = songName;
        this.sheetContent = convertToSheetContent(sheetContent);

    }

    public NoteSheetSongResponse(Long id, Long instrumentId, Long songId, String sheetContentResponse) {
        this.id = id;
        this.instrumentId = instrumentId;
        this.songId = songId;
        this.sheetContent = convertToSheetContent(sheetContentResponse);
    }

    public NoteSheetContentResponse convertToSheetContent(String content) {
        N2bObjectMapper mapper = new N2bObjectMapper();
        try {
            return mapper.readValue(content, NoteSheetContentResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
