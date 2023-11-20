package ba.com.zira.sdr.api.model.label;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LabelSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    Long id;
    LocalDateTime foundingDate;
    String name;
    String information;
    Long founderId;
    LocalDateTime modified;
    Long numberOfArtists;
    String outlineText;

    String imageUrl;

    public LabelSearchResponse(Long id, LocalDateTime foundingDate, String name, String information, Long founderId, LocalDateTime modified,
            Long numberOfArtists, String outlineText) {
        super();
        this.id = id;
        this.foundingDate = foundingDate;
        this.name = name;
        this.information = information;
        this.founderId = founderId;
        this.modified = modified;
        this.numberOfArtists = numberOfArtists;
        this.outlineText = outlineText;
    }

}