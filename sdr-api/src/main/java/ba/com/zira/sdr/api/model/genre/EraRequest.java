package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for Genres over eras request")
public class EraRequest implements Serializable {
    public EraRequest(List<Long> request) {
        this.era = request;
    }

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private List<Long> era;

}
