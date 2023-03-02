package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest2;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;
import ba.com.zira.sdr.dao.SongSimilarityDAO;
import ba.com.zira.sdr.dao.SongSimilarityDetailDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("SongSimilarityDetailRequestValidation")
public class SongSimilarityDetailRequestValidation {

    private SongSimilarityDetailDAO songsimilaritydetailDAO;
    private SongSimilarityDAO songSimilarityDAO;

    public ValidationResponse validateUpdateSongSimilartyDetailRequest(final EntityRequest<SongSimilarityDetailUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsSongSimilartyDetailRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!songsimilaritydetailDAO.existsByPK(id)) {
            return ValidationError.of("Song sample detail not found", "Song sample detail with id: " + id + " does not exist!");
        }
        return null;
    }

    public ValidationResponse validateCreateSongSimilarityDetailRequest(
            final EntityRequest<SongSimilarityDetailCreateRequest2> entityRequest) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(exists(entityRequest.getEntity().getSongSimilarity()));

        return ValidationResponse.of(entityRequest, errors);
    }

}
