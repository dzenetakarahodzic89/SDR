package ba.com.zira.sdr.api.model.multisearchhistory;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiSearchHistoryResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    private String id;
    @Schema(description = "Refresh time of multi search table")
    private LocalDateTime refreshTime;
    @Schema(description = "Rows before refresh")
    private Long rowsBefore;
    @Schema(description = "Rows after refresh")
    private Long rowsAfter;
    @Schema(description = "Data structure after refresh")
    private MultiSearchHistoryDataStructure dataStructure;
}
