package ba.com.zira.sdr.api.model.chordprogression;

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
public class ChordProgressionSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String sortBy;
    private int page;
    private int pageSize;
    private List<Long> eraIds;
    private List<Long> genreIds;

}
