
package ba.com.zira.sdr.spotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "album_type", "artists", "id", "images", "name", "release_date", "total_tracks" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyAlbumItem {

    @JsonProperty("album_type")
    private String albumType;
    @JsonProperty("artists")
    private List<SpotifyArtistResponse> artists = new ArrayList<>();
    @JsonProperty("id")
    private String id;
    @JsonProperty("images")
    private List<SpotifyImage> images = new ArrayList<>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("total_tracks")
    private Integer totalTracks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
