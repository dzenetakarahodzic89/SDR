package ba.com.zira.sdr.api.model.era;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EraSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;
    public String sortBy;
    public int page;
    public int pageSize;
    public List<Long> albumIds;
    public List<Long> artistIds;
    public List<Long> genreIds;

}
