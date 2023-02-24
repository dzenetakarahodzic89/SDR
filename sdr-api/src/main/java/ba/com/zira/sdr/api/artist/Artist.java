package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.util.List;

import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Artist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "Unique identifier")
    private Long id;
    @Schema(description = "Name of the artist")
    private String name;

    private List<LoV> persons;
}
