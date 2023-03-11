
package ba.com.zira.sdr.deezer;

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
@JsonPropertyOrder({ "data", "total" })
@Generated("jsonschema2pojo")
public class ArtistSearch {

    @JsonProperty("data")
    private List<Datum> data = new ArrayList<Datum>();
    @JsonProperty("total")
    private Integer total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("data")
    public List<Datum> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Datum> data) {
        this.data = data;
    }

    public ArtistSearch withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    public ArtistSearch withTotal(Integer total) {
        this.total = total;
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

    public ArtistSearch withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ArtistSearch.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null) ? "<null>" : this.data));
        sb.append(',');
        sb.append("total");
        sb.append('=');
        sb.append(((this.total == null) ? "<null>" : this.total));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? "<null>" : this.additionalProperties));
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
        result = ((result * 31) + ((this.data == null) ? 0 : this.data.hashCode()));
        result = ((result * 31) + ((this.total == null) ? 0 : this.total.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ArtistSearch) == false) {
            return false;
        }
        ArtistSearch rhs = ((ArtistSearch) other);
        return ((((this.data == rhs.data) || ((this.data != null) && this.data.equals(rhs.data)))
                && ((this.total == rhs.total) || ((this.total != null) && this.total.equals(rhs.total))))
                && ((this.additionalProperties == rhs.additionalProperties)
                        || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
