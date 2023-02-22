package ba.com.zira.sdr.api.model.personartist;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of person-artist create request")
public class PersonArtistCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Id of associated artist")
    private Long artistId;

    @NotNull
    @Schema(description = "Id of associated person")
    private Long personId;

}
