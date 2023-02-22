package ba.com.zira.sdr.api.model.personartist;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of person-artist create request")
public class PersonArtistUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of record for update")
    private Long id;

    @Schema(description = "Id of associated song")
    private Long personId;

    @Schema(description = "Id of associated artist")
    private Long artistId;
}
