package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.sdr.dao.SongSimilarityDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("SongSimilarityRequestValidation")
public class SongSimilarityRequestValidation {

    private SongSimilarityDAO songsimilarityDAO;

    @SuppressWarnings("unused")
    private ValidationError exists(Long id) {
        if (!songsimilarityDAO.existsByPK(id)) {
            return ValidationError.of("Song similarity not found", "Song with id: " + id + " does not exist!");
        }
        return null;
    }

}
