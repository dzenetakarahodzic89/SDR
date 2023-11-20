
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

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "href", "items", "limit", "next", "offset", "previous", "total" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyAlbumsTracks {

    @JsonProperty("href")
    private String href;
    @JsonProperty("items")
    private List<SpotifyAlbumsTrackItem> items = new ArrayList<>();
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("next")
    private Object next;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("previous")
    private Object previous;
    @JsonProperty("total")
    private Integer total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
