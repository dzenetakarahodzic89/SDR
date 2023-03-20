package ba.com.zira.sdr.api.artist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a artist")
public class ArtistCreateRequest extends ArtistRequest {
    private static final long serialVersionUID = 1L;

}