
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
import ba.com.zira.sdr.spotify.SpotifyImage;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "artists", "genres", "id", "images", "label", "name", "release_date", "total_tracks", "tracks" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyGetAlbumsSongs {

    @JsonProperty("artists")
    private List<SpotifyArtistResponse> artists = new ArrayList<>();
    @JsonProperty("genres")
    private List<String> genres = new ArrayList<>();
    @JsonProperty("id")
    private String id;
    @JsonProperty("images")
    private List<SpotifyImage> images = new ArrayList<>();
    @JsonProperty("label")
    private String label;
    @JsonProperty("name")
    private String name;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("total_tracks")
    private Integer totalTracks;
    @JsonProperty("tracks")
    private SpotifyAlbumsTracks tracks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
