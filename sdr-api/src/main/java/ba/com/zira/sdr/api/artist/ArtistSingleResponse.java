package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import ba.com.zira.sdr.api.model.album.AlbumArtistSingleResponse;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.api.model.person.PersonArtistSingleResponse;
import ba.com.zira.sdr.api.model.song.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ArtistSingleResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    private Long id;
    @Schema(description = "Full stage name")
    private String name;
    @Schema(description = "Full stage name")
    private String surname;
    @Schema(description = "Full name")
    private String fullName;
    @Schema(description = "Information")
    private String information;
    @Schema(description = "image")
    private String imageUrl;
    @Schema(description = "Outline text")
    private String outlineText;
    @Schema(description = "Albums")
    private List<AlbumArtistSingleResponse> albums;
    @Schema(description = "Recent songs")
    private List<Song> recentsSong;
    @Schema(description = "Number of songs")
    private Long numberOfSongs;
    @Schema(description = "Labels")
    private List<LabelResponse> labels;
    @Schema(description = "Persons")
    private List<PersonArtistSingleResponse> persons;
    @Schema(description = "Date of Birth")
    private LocalDateTime dateOfBirth;
    @Schema(description = "Instrument name")
    private String instrument;
    @Schema(description = "Label Name")
    private String labelName;
    @Schema(description = "Type")
    private String type;

    private String spotifyId;
}
