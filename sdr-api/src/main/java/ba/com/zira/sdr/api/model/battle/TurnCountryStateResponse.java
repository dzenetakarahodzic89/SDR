package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TurnCountryStateResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    Long id;
    String name;
    List<Long> eligableArtists;
}
