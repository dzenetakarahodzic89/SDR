package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;

import lombok.Data;

@Data

public class PersonCountryResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Long count;
    private Double ratio;

    public PersonCountryResponse() {
        super();
    }

    public PersonCountryResponse(String name, Long count) {
        super();
        this.name = name;
        this.count = count;
    }

    public PersonCountryResponse(String name, Double ratio) {
        super();
        this.name = name;
        this.ratio = ratio;
    }

}
