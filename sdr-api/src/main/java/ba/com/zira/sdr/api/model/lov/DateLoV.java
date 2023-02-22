package ba.com.zira.sdr.api.model.lov;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DateLoV implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private LocalDateTime date;

    public DateLoV(long id, LocalDateTime date) {
        super();
        this.id = id;
        this.date = date;
    }
}
