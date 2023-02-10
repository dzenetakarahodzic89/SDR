package ba.com.zira.sdr.api.model.wiki;

import java.io.Serializable;

import lombok.Data;

@Data
public class WikiResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;

    private Long countType;

    public WikiResponse(long countType, String type) {
        super();

        this.countType = countType;
        this.type = type;
    }

}