package ba.com.zira.sdr.api.artist;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArtistSearchResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long songCount;
    private String fullName;
    private String outlineText;
    private String imageUrl;

    public ArtistSearchResponse(Long songCount, String fullName, String outlineText) {
        this.songCount = songCount;
        this.fullName = fullName;
        this.outlineText = outlineText;
    }

    public ArtistSearchResponse(String fullName, String outlineText) {
        this.fullName = fullName;
        this.outlineText = outlineText;
    }

}
