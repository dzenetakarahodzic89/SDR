package ba.com.zira.sdr.api.model.songsimilaritydetail;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creating details of a song")

public class SongSimilarityDetailModelCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the person who created it")
    private String createdBy;

}
