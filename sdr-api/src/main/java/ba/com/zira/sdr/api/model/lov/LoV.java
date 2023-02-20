package ba.com.zira.sdr.api.model.lov;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoV implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;

    public LoV() {
        super();
    }

    public LoV(Long id) {
        super();
        this.id = id;
        this.name = "John Doe";
    }

    public LoV(Long id, String name) {
        super();
        this.id = id;
        this.name = name;

    }

}