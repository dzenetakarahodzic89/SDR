package ba.com.zira.sdr.api.model.multisearchhistory;

import java.io.Serializable;

import lombok.Data;

@Data
public class MultiSearchHistoryDataStructure implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long songs;
    private Long albums;
    private Long persons;
}
