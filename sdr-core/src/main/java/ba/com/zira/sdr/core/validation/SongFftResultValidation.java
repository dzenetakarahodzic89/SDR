package ba.com.zira.sdr.core.validation;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongFftResultDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("songFftResultValidation")
public class SongFftResultValidation {

    private SongFftResultDAO songFftResultDAO;
    private SongDAO songDAO;

    public ValidationResponse validateUpdateSongFftResultRequest(final EntityRequest<SongFftResultUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));
        errors.put(songExists(request.getEntity().getSongID()));
        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateCreateSongFftResultRequest(final EntityRequest<SongFftResultCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(songExists(request.getEntity().getSongId()));
        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsSongFftResultRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!songFftResultDAO.existsByPK(id)) {
            return ValidationError.of("SONGFFTRESULT_NOT_FOUND", "SongFftResult with id: " + id + " does not exist!");
        }
        return null;
    }

    private ValidationError songExists(Long id) {
        if (!songDAO.existsByPK(id)) {
            return ValidationError.of("SONG_NOT_FOUND", "Song with id: " + id + " does not exist!");
        }
        return null;
    }
}
