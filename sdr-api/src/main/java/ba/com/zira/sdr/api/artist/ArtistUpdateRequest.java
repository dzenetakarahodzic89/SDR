package ba.com.zira.sdr.api.artist;

import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a artist")
public class ArtistUpdateRequest extends ArtistRequestSuperclass {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the artist")
    private long id;

}