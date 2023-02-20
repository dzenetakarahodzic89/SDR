package ba.com.zira.sdr.api.model.lov;

import java.io.Serializable;

import lombok.Data;

@Data
public class DoubleLoV implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long name;
    private Double value;

    public DoubleLoV(Long name, Double value) {
        super();
        this.name = name;
        this.value = value;
    }
}
