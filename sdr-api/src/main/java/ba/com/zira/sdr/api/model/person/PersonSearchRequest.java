package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String sortBy;
    private String personGender;
    private int page;
    private int pageSize;

}
