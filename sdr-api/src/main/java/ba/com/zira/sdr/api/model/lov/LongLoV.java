package ba.com.zira.sdr.api.model.lov;

import java.io.Serializable;

import lombok.Data;

@Data
public class LongLoV implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long value;

    public LongLoV(Long id, Long value) {
        super();
        this.id = id;
        this.value = value;

    }

}