package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityCreateRequest;
import ba.com.zira.sdr.dao.SongSimilarityDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("SongSimilarityRequestValidation")
public class SongSimilarityRequestValidation {

    private SongSimilarityDAO songSimilarityDAO;

    public ValidationResponse validateSongSimilarityRequest(EntityRequest<SongSimilarityCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(validation(request.getEntity().getSongA(), request.getEntity().getSongB()));
        return ValidationResponse.of(request, errors);
    }

    private ValidationError validation(Long songA, Long songB) {
        if (songA == songB) {
            return ValidationError.of("IDENTICAL_SONGS", "Cannot set song similarity to itself!");
        }

        if (songSimilarityDAO.existsSimilarity(songA, songB)) {
            return ValidationError.of("SONG_SIMILARITY_EXISTS",
                    "Songs with ids: " + songA.toString() + " and " + songB.toString() + " are already linked!");
        }

        return null;
    }

}
