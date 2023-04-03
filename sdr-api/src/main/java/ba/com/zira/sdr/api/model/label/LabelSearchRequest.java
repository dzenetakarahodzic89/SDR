package ba.com.zira.sdr.api.model.label;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class LabelSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public LabelSearchRequest(List<Long> founders, String name)

    {

        this.founders = founders;
        this.name = name;

    }

    private List<Long> founders;
    private String name;

}
