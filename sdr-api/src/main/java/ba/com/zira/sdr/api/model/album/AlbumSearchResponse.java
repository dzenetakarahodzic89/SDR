package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;

import lombok.Data;

@Data
public class AlbumSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    String imageUrl;
    String outlineText;
    Long id;
    int playtime;

}
