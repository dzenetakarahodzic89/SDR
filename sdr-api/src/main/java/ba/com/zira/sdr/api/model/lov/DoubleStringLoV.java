package ba.com.zira.sdr.api.model.lov;

import java.io.Serializable;

import lombok.Data;

@Data
public class DoubleStringLoV implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Double value;

    public DoubleStringLoV(String name, Double value) {
        super();
        this.name = name;
        this.value = value;
    }

}
