
package ba.com.zira.sdr.spotify.album.songs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ba.com.zira.sdr.spotify.SpotifyArtistResponse;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "artists", "duration_ms", "id", "name", "preview_url", "track_number" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyAlbumsTrackItem {

    @JsonProperty("artists")
    private List<SpotifyArtistResponse> artists = new ArrayList<>();
    @JsonProperty("duration_ms")
    private Integer durationMs;
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("preview_url")
    private String previewUrl;
    @JsonProperty("track_number")
    private Integer trackNumber;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
