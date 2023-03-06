package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlbumsByDecadeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Decade of the albums")
    private Integer decade;

    @NotNull
    @Schema(description = "List of albums for the decade")
    private List<AlbumArtistResponse> albums;

    public AlbumsByDecadeResponse(Integer decade, List<AlbumArtistResponse> albums) {
        this.decade = decade;
        this.albums = albums;
    }
}
