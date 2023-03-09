package ba.com.zira.sdr.api.model.songsimilaritydetail;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Properties of an SongSimilarityDetail response")
public class SongSimilarityDetailResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userCode;
    private BigDecimal grade;
    private BigDecimal totalSimilarityScore;

}
