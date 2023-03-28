package ba.com.zira.sdr.api.model.playlistga;

import java.util.List;

public interface Selection {
    List<Rankable> select(Population population, Long numberOfChromosomes);
}
