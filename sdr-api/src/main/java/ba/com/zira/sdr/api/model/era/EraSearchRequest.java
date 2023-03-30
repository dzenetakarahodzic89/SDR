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

    private String name;
    private String sortBy;
    private int page;
    private int pageSize;
    private List<Long> albumIds;
    private List<Long> artistIds;
    private List<Long> genreIds;

}
