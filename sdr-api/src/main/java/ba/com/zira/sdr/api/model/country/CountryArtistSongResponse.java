package ba.com.zira.sdr.api.model.country;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Properties of country response")
public class CountryArtistSongResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of country record")
    private Long id;

    @Schema(description = "Name of this country")
    private String name;

    @Schema(description = "Number of artists")
    private Long artists;

    @Schema(description = "Number of songs")
    private Long songs;

}