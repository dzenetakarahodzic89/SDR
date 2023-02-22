package ba.com.zira.sdr.api.model.personartist;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of person-artist response")
public class PersonArtistResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of song-artist record")
    private Long id;
    @Schema(description = "Id of a person that this record has")
    private Long personId;
    @Schema(description = "Id of an artist that wrote this song")
    private Long artistId;

    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the sample")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the sample")
    private String modifiedBy;

    @Schema(description = "Name of an artist that wrote this song")
    private String artistName;
    @Schema(description = "Name of a person that this song belongs to")
    private String personName;

    @Schema(description = "Start of the relationship made beetween person and artist")
    private LocalDateTime startOfRelaltionship;
    @Schema(description = "End of the relationship made beetween person and artist")
    private LocalDateTime endOfRelationship;

}
