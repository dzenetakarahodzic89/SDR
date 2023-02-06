package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleModelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String docname;
    private String status;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime modified;
    private String modifiedBy;
    
}