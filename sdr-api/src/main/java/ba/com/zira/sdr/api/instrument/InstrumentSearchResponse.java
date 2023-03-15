package ba.com.zira.sdr.api.instrument;

import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a instrument search response")
public class InstrumentSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private Long id;
    private String name;
    private LocalDateTime modified;
    private String outlineText;
    private String imageUrl;

    public InstrumentSearchResponse(Long id, String name, LocalDateTime modified, String outlineText) {
        this.id = id;
        this.name = name;
        this.modified = modified;
        this.outlineText = outlineText;

    }

}
