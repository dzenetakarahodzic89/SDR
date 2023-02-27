package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import ba.com.zira.sdr.api.artist.ArtistSongResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a song response")
public class SongSingleResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String imageUrl;
    private String outlineText;
    @Schema(description = "Album name")
    private String album;
    private String information;
    private int playlistCount;
    private LocalDateTime dateOfRelease;
    @Schema(description = "Label name")
    private String label;
    @Schema(description = "Chord progression name")
    private String chordProgression;
    @Schema(description = "Genre Id")
    private Long genreId;
    @Schema(description = "Genre name")
    private String genre;
    @Schema(description = "Subgenres - format 1-rock")
    private Map<Long,String> subgenres;
    @Schema(description = "List of artists")
    private List<ArtistSongResponse> artists;
    public SongSingleResponse(Long id,String songName,String outlineText,String information,LocalDateTime dateOfRelease,String chordName,String genreName,Long genreId) {
    	this.id=id;
    	this.name=songName;
    	this.outlineText=outlineText;
    	this.information=information;
    	this.dateOfRelease=dateOfRelease;
    	this.chordProgression=chordName;
    	this.genre=genreName;
    	this.genreId=genreId;
    }
}
