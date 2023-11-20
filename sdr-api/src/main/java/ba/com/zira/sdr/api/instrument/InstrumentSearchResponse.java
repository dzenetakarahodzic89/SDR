package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

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
    private LocalDateTime created;
    private String outlineText;
    private String imageUrl;

    public InstrumentSearchResponse(Long id, String name, String outlineText, LocalDateTime modified) {
        this.id = id;
        this.name = name;
        this.outlineText = outlineText;
        this.modified = modified;

    }

    public InstrumentSearchResponse(Long id, String name, String outlineText, LocalDateTime modified, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.outlineText = outlineText;
        this.modified = modified;
        this.created = created;

    }
}
