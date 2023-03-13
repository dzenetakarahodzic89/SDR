package ba.com.zira.sdr.api.artist;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArtistSearchResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String outlineText;
    private String imageUrl;

    public ArtistSearchResponse(Long id, String fullName, String outlineText) {
        this.id = id;
        this.name = fullName;
        this.outlineText = outlineText;
    }

}
