package ba.com.zira.sdr.api.model.userrecommendation;

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
public class ScoreCompareRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Long> userIds;
}
