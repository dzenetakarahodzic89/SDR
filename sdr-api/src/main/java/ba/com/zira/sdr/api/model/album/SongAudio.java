package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of SongAudio")
public class SongAudio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;
    private String title;
    private String cover;

    public SongAudio(final String audioUrl, final String name, final String coverUrl) {
        this.url = audioUrl;
        this.title = name;
        this.cover = coverUrl;
    }

}
