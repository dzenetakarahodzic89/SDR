package ba.com.zira.sdr.api.playlistga;

import java.util.List;

public interface Selection {
    List<Chromosome> select(Population population);
}
