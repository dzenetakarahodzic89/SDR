
package ba.com.zira.sdr.deezer.tracklist;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "cover", "cover_small", "cover_medium", "cover_big", "cover_xl", "md5_image", "tracklist", "type" })
@Generated("jsonschema2pojo")
public class Album {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("cover")
    private String cover;
    @JsonProperty("cover_small")
    private String coverSmall;
    @JsonProperty("cover_medium")
    private String coverMedium;
    @JsonProperty("cover_big")
    private String coverBig;
    @JsonProperty("cover_xl")
    private String coverXl;
    @JsonProperty("md5_image")
    private String md5Image;
    @JsonProperty("tracklist")
    private String tracklist;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Album withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Album withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("cover")
    public String getCover() {
        return cover;
    }

    @JsonProperty("cover")
    public void setCover(String cover) {
        this.cover = cover;
    }

    public Album withCover(String cover) {
        this.cover = cover;
        return this;
    }

    @JsonProperty("cover_small")
    public String getCoverSmall() {
        return coverSmall;
    }

    @JsonProperty("cover_small")
    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public Album withCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
        return this;
    }

    @JsonProperty("cover_medium")
    public String getCoverMedium() {
        return coverMedium;
    }

    @JsonProperty("cover_medium")
    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public Album withCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
        return this;
    }

    @JsonProperty("cover_big")
    public String getCoverBig() {
        return coverBig;
    }

    @JsonProperty("cover_big")
    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public Album withCoverBig(String coverBig) {
        this.coverBig = coverBig;
        return this;
    }

    @JsonProperty("cover_xl")
    public String getCoverXl() {
        return coverXl;
    }

    @JsonProperty("cover_xl")
    public void setCoverXl(String coverXl) {
        this.coverXl = coverXl;
    }

    public Album withCoverXl(String coverXl) {
        this.coverXl = coverXl;
        return this;
    }

    @JsonProperty("md5_image")
    public String getMd5Image() {
        return md5Image;
    }

    @JsonProperty("md5_image")
    public void setMd5Image(String md5Image) {
        this.md5Image = md5Image;
    }

    public Album withMd5Image(String md5Image) {
        this.md5Image = md5Image;
        return this;
    }

    @JsonProperty("tracklist")
    public String getTracklist() {
        return tracklist;
    }

    @JsonProperty("tracklist")
    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public Album withTracklist(String tracklist) {
        this.tracklist = tracklist;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Album withType(String type) {
        this.type = type;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Album withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        String nullValue = "<null>";
        StringBuilder sb = new StringBuilder();
        sb.append(Album.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? nullValue : this.id));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null) ? nullValue : this.title));
        sb.append(',');
        sb.append("cover");
        sb.append('=');
        sb.append(((this.cover == null) ? nullValue : this.cover));
        sb.append(',');
        sb.append("coverSmall");
        sb.append('=');
        sb.append(((this.coverSmall == null) ? nullValue : this.coverSmall));
        sb.append(',');
        sb.append("coverMedium");
        sb.append('=');
        sb.append(((this.coverMedium == null) ? nullValue : this.coverMedium));
        sb.append(',');
        sb.append("coverBig");
        sb.append('=');
        sb.append(((this.coverBig == null) ? nullValue : this.coverBig));
        sb.append(',');
        sb.append("coverXl");
        sb.append('=');
        sb.append(((this.coverXl == null) ? nullValue : this.coverXl));
        sb.append(',');
        sb.append("md5Image");
        sb.append('=');
        sb.append(((this.md5Image == null) ? nullValue : this.md5Image));
        sb.append(',');
        sb.append("tracklist");
        sb.append('=');
        sb.append(((this.tracklist == null) ? nullValue : this.tracklist));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null) ? nullValue : this.type));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? nullValue : this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.cover == null) ? 0 : this.cover.hashCode()));
        result = ((result * 31) + ((this.coverSmall == null) ? 0 : this.coverSmall.hashCode()));
        result = ((result * 31) + ((this.coverBig == null) ? 0 : this.coverBig.hashCode()));
        result = ((result * 31) + ((this.tracklist == null) ? 0 : this.tracklist.hashCode()));
        result = ((result * 31) + ((this.coverMedium == null) ? 0 : this.coverMedium.hashCode()));
        result = ((result * 31) + ((this.coverXl == null) ? 0 : this.coverXl.hashCode()));
        result = ((result * 31) + ((this.md5Image == null) ? 0 : this.md5Image.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((result * 31) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Album)) {
            return false;
        }
        Album rhs = ((Album) other);
        return ((((((((((((this.cover == rhs.cover) || ((this.cover != null) && this.cover.equals(rhs.cover)))
                && ((this.coverSmall == rhs.coverSmall) || ((this.coverSmall != null) && this.coverSmall.equals(rhs.coverSmall))))
                && ((this.coverBig == rhs.coverBig) || ((this.coverBig != null) && this.coverBig.equals(rhs.coverBig))))
                && ((this.tracklist == rhs.tracklist) || ((this.tracklist != null) && this.tracklist.equals(rhs.tracklist))))
                && ((this.coverMedium == rhs.coverMedium) || ((this.coverMedium != null) && this.coverMedium.equals(rhs.coverMedium))))
                && ((this.coverXl == rhs.coverXl) || ((this.coverXl != null) && this.coverXl.equals(rhs.coverXl))))
                && ((this.md5Image == rhs.md5Image) || ((this.md5Image != null) && this.md5Image.equals(rhs.md5Image))))
                && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id))))
                && ((this.additionalProperties == rhs.additionalProperties)
                        || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.title == rhs.title) || ((this.title != null) && this.title.equals(rhs.title))))
                && ((this.type == rhs.type) || ((this.type != null) && this.type.equals(rhs.type))));
    }

}
