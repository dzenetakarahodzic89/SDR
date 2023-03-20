package ba.com.zira.sdr.api.model.playlistga;

import java.util.List;

public interface WeightGenerator {
    List<Double> generate(Integer numberOfPriorities);
}
