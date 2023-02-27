package ba.com.zira.sdr.api.model.label;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import ba.com.zira.sdr.api.artist.ArtistLabelResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a label response")
public class LabelArtistResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "Unique identifier of the label")
    private Long id;
    @Schema(description = "Name of the label")
    private String name;
    @Schema(description = "Label outline text")
    private String outlineText;
    @Schema(description = "Information about label")
    private String information;
    @Schema(description = "Founding date")
    private LocalDateTime foundingDate;
    @Schema(description = "Founder id")
    private Long founderId;
    @Schema(description = "Founder name")
    private String founder;
    @Schema(description = "List of artists")
    private List<ArtistLabelResponse> artists;
    private String imageUrl;

    public LabelArtistResponse(Long id, String labelName, String outlineText, String information, LocalDateTime foundingDate,
            String founder, Long founderId) {
        this.id = id;
        this.name = labelName;
        this.outlineText = outlineText;
        this.information = information;
        this.foundingDate = foundingDate;
        this.founder = founder;
        this.founderId = founderId;
    }
}
