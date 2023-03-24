
package ba.com.zira.sdr.deezer.tracklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "readable", "title", "title_short", "title_version", "link", "duration", "rank", "explicit_lyrics",
        "explicit_content_lyrics", "explicit_content_cover", "preview", "contributors", "md5_image", "artist", "album", "type" })
@Generated("jsonschema2pojo")
public class Datum {

    @JsonProperty("id")
    private String id;
    @JsonProperty("readable")
    private Boolean readable;
    @JsonProperty("title")
    private String title;
    @JsonProperty("title_short")
    private String titleShort;
    @JsonProperty("title_version")
    private String titleVersion;
    @JsonProperty("link")
    private String link;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("explicit_lyrics")
    private Boolean explicitLyrics;
    @JsonProperty("explicit_content_lyrics")
    private Integer explicitContentLyrics;
    @JsonProperty("explicit_content_cover")
    private Integer explicitContentCover;
    @JsonProperty("preview")
    private String preview;
    @JsonProperty("contributors")
    private List<Contributor> contributors = new ArrayList<>();
    @JsonProperty("md5_image")
    private String md5Image;
    @JsonProperty("artist")
    private Artist artist;
    @JsonProperty("album")
    private Album album;
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

    public Datum withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("readable")
    public Boolean getReadable() {
        return readable;
    }

    @JsonProperty("readable")
    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public Datum withReadable(Boolean readable) {
        this.readable = readable;
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

    public Datum withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("title_short")
    public String getTitleShort() {
        return titleShort;
    }

    @JsonProperty("title_short")
    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public Datum withTitleShort(String titleShort) {
        this.titleShort = titleShort;
        return this;
    }

    @JsonProperty("title_version")
    public String getTitleVersion() {
        return titleVersion;
    }

    @JsonProperty("title_version")
    public void setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
    }

    public Datum withTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
        return this;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    public Datum withLink(String link) {
        this.link = link;
        return this;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Datum withDuration(String duration) {
        this.duration = duration;
        return this;
    }

    @JsonProperty("rank")
    public String getRank() {
        return rank;
    }

    @JsonProperty("rank")
    public void setRank(String rank) {
        this.rank = rank;
    }

    public Datum withRank(String rank) {
        this.rank = rank;
        return this;
    }

    @JsonProperty("explicit_lyrics")
    public Boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    @JsonProperty("explicit_lyrics")
    public void setExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public Datum withExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
        return this;
    }

    @JsonProperty("explicit_content_lyrics")
    public Integer getExplicitContentLyrics() {
        return explicitContentLyrics;
    }

    @JsonProperty("explicit_content_lyrics")
    public void setExplicitContentLyrics(Integer explicitContentLyrics) {
        this.explicitContentLyrics = explicitContentLyrics;
    }

    public Datum withExplicitContentLyrics(Integer explicitContentLyrics) {
        this.explicitContentLyrics = explicitContentLyrics;
        return this;
    }

    @JsonProperty("explicit_content_cover")
    public Integer getExplicitContentCover() {
        return explicitContentCover;
    }

    @JsonProperty("explicit_content_cover")
    public void setExplicitContentCover(Integer explicitContentCover) {
        this.explicitContentCover = explicitContentCover;
    }

    public Datum withExplicitContentCover(Integer explicitContentCover) {
        this.explicitContentCover = explicitContentCover;
        return this;
    }

    @JsonProperty("preview")
    public String getPreview() {
        return preview;
    }

    @JsonProperty("preview")
    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Datum withPreview(String preview) {
        this.preview = preview;
        return this;
    }

    @JsonProperty("contributors")
    public List<Contributor> getContributors() {
        return contributors;
    }

    @JsonProperty("contributors")
    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    public Datum withContributors(List<Contributor> contributors) {
        this.contributors = contributors;
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

    public Datum withMd5Image(String md5Image) {
        this.md5Image = md5Image;
        return this;
    }

    @JsonProperty("artist")
    public Artist getArtist() {
        return artist;
    }

    @JsonProperty("artist")
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Datum withArtist(Artist artist) {
        this.artist = artist;
        return this;
    }

    @JsonProperty("album")
    public Album getAlbum() {
        return album;
    }

    @JsonProperty("album")
    public void setAlbum(Album album) {
        this.album = album;
    }

    public Datum withAlbum(Album album) {
        this.album = album;
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

    public Datum withType(String type) {
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

    public Datum withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {

        String nullValue = "<null>";
        StringBuilder sb = new StringBuilder();
        sb.append(Datum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? nullValue : this.id));
        sb.append(',');
        sb.append("readable");
        sb.append('=');
        sb.append(((this.readable == null) ? nullValue : this.readable));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null) ? nullValue : this.title));
        sb.append(',');
        sb.append("titleShort");
        sb.append('=');
        sb.append(((this.titleShort == null) ? nullValue : this.titleShort));
        sb.append(',');
        sb.append("titleVersion");
        sb.append('=');
        sb.append(((this.titleVersion == null) ? nullValue : this.titleVersion));
        sb.append(',');
        sb.append("link");
        sb.append('=');
        sb.append(((this.link == null) ? nullValue : this.link));
        sb.append(',');
        sb.append("duration");
        sb.append('=');
        sb.append(((this.duration == null) ? nullValue : this.duration));
        sb.append(',');
        sb.append("rank");
        sb.append('=');
        sb.append(((this.rank == null) ? nullValue : this.rank));
        sb.append(',');
        sb.append("explicitLyrics");
        sb.append('=');
        sb.append(((this.explicitLyrics == null) ? nullValue : this.explicitLyrics));
        sb.append(',');
        sb.append("explicitContentLyrics");
        sb.append('=');
        sb.append(((this.explicitContentLyrics == null) ? nullValue : this.explicitContentLyrics));
        sb.append(',');
        sb.append("explicitContentCover");
        sb.append('=');
        sb.append(((this.explicitContentCover == null) ? nullValue : this.explicitContentCover));
        sb.append(',');
        sb.append("preview");
        sb.append('=');
        sb.append(((this.preview == null) ? nullValue : this.preview));
        sb.append(',');
        sb.append("contributors");
        sb.append('=');
        sb.append(((this.contributors == null) ? nullValue : this.contributors));
        sb.append(',');
        sb.append("md5Image");
        sb.append('=');
        sb.append(((this.md5Image == null) ? nullValue : this.md5Image));
        sb.append(',');
        sb.append("artist");
        sb.append('=');
        sb.append(((this.artist == null) ? nullValue : this.artist));
        sb.append(',');
        sb.append("album");
        sb.append('=');
        sb.append(((this.album == null) ? nullValue : this.album));
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
        result = ((result * 31) + ((this.readable == null) ? 0 : this.readable.hashCode()));
        result = ((result * 31) + ((this.preview == null) ? 0 : this.preview.hashCode()));
        result = ((result * 31) + ((this.artist == null) ? 0 : this.artist.hashCode()));
        result = ((result * 31) + ((this.album == null) ? 0 : this.album.hashCode()));
        result = ((result * 31) + ((this.explicitContentLyrics == null) ? 0 : this.explicitContentLyrics.hashCode()));
        result = ((result * 31) + ((this.link == null) ? 0 : this.link.hashCode()));
        result = ((result * 31) + ((this.md5Image == null) ? 0 : this.md5Image.hashCode()));
        result = ((result * 31) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((result * 31) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((result * 31) + ((this.duration == null) ? 0 : this.duration.hashCode()));
        result = ((result * 31) + ((this.titleVersion == null) ? 0 : this.titleVersion.hashCode()));
        result = ((result * 31) + ((this.rank == null) ? 0 : this.rank.hashCode()));
        result = ((result * 31) + ((this.titleShort == null) ? 0 : this.titleShort.hashCode()));
        result = ((result * 31) + ((this.explicitLyrics == null) ? 0 : this.explicitLyrics.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.contributors == null) ? 0 : this.contributors.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.explicitContentCover == null) ? 0 : this.explicitContentCover.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Datum)) {
            return false;
        }
        Datum rhs = ((Datum) other);
        return (((((((((((((((((((this.readable == rhs.readable) || ((this.readable != null) && this.readable.equals(rhs.readable)))
                && ((this.preview == rhs.preview) || ((this.preview != null) && this.preview.equals(rhs.preview))))
                && ((this.artist == rhs.artist) || ((this.artist != null) && this.artist.equals(rhs.artist))))
                && ((this.album == rhs.album) || ((this.album != null) && this.album.equals(rhs.album))))
                && ((this.explicitContentLyrics == rhs.explicitContentLyrics)
                        || ((this.explicitContentLyrics != null) && this.explicitContentLyrics.equals(rhs.explicitContentLyrics))))
                && ((this.link == rhs.link) || ((this.link != null) && this.link.equals(rhs.link))))
                && ((this.md5Image == rhs.md5Image) || ((this.md5Image != null) && this.md5Image.equals(rhs.md5Image))))
                && ((this.title == rhs.title) || ((this.title != null) && this.title.equals(rhs.title))))
                && ((this.type == rhs.type) || ((this.type != null) && this.type.equals(rhs.type))))
                && ((this.duration == rhs.duration) || ((this.duration != null) && this.duration.equals(rhs.duration))))
                && ((this.titleVersion == rhs.titleVersion) || ((this.titleVersion != null) && this.titleVersion.equals(rhs.titleVersion))))
                && ((this.rank == rhs.rank) || ((this.rank != null) && this.rank.equals(rhs.rank))))
                && ((this.titleShort == rhs.titleShort) || ((this.titleShort != null) && this.titleShort.equals(rhs.titleShort))))
                && ((this.explicitLyrics == rhs.explicitLyrics)
                        || ((this.explicitLyrics != null) && this.explicitLyrics.equals(rhs.explicitLyrics))))
                && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id))))
                && ((this.contributors == rhs.contributors) || ((this.contributors != null) && this.contributors.equals(rhs.contributors))))
                && ((this.additionalProperties == rhs.additionalProperties)
                        || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.explicitContentCover == rhs.explicitContentCover)
                        || ((this.explicitContentCover != null) && this.explicitContentCover.equals(rhs.explicitContentCover))));
    }

}
