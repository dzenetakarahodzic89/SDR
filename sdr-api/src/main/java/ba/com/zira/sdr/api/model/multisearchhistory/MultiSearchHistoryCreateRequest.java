package ba.com.zira.sdr.api.model.multisearchhistory;

import java.io.Serializable;

import lombok.Data;

@Data
public class MultiSearchHistoryCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long rowsBefore;
    private Long rowsAfter;
    private MultiSearchHistoryDataStructure dataStructure;
}
