package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonCountResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<PersonCountryResponse> resp;
}
