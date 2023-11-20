package ba.com.zira.sdr.api.model.song;

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
public class SongSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Long remixId;
    private Long coverId;
    private String sortBy;
    private Integer page;
    private Integer pageSize;
    private List<Long> artistIds;
    private List<Long> albumIds;
    private List<Long> genreIds;

}
