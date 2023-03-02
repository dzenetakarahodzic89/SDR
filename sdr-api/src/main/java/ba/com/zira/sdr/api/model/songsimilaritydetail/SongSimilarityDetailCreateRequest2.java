package ba.com.zira.sdr.api.model.songsimilaritydetail;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creating details of a song")

public class SongSimilarityDetailCreateRequest2 implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal grade;

    private String userCode;

    private Long songSimilarity;

}
