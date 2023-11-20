package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.NoteSheetService;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetCreateRequest;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.SongDAO;
import lombok.AllArgsConstructor;

/**
 * NoteSheetRequestValidation is used for validation of {@link NoteSheetService}
 * requests.<br>
 * e.g. database validation needed
 *
 * @author zira
 */
@AllArgsConstructor
@Component("NoteSheetRequestValidation")
public class NoteSheetRequestValidation {

    private NoteSheetDAO notesheetDAO;
    private SongDAO songDAO;
    private InstrumentDAO instrumentDAO;
    private static final String DOES_NOT_EXIST = " does not exist!";

    /**
     * Validates update NoteSheet plan from {@link NoteSheetService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateUpdateNoteSheetRequest(final EntityRequest<NoteSheetUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));

        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates exists NoteSheet plan from {@link NoteSheetService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateExistsNotesheetRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));
        return ValidationResponse.of(request, errors);
    }

    /**
     * Validates createRequest NoteSheet plan from {@link NoteSheetService}.
     *
     * @param request
     *            the {@link EntityRequest} to validate.
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validateCreateNoteSheetRequest(final EntityRequest<NoteSheetCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(songExists(request.getEntity().getSongId()));
        errors.put(instrumentExists(request.getEntity().getInstrumentId()));
        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!notesheetDAO.existsByPK(id)) {
            return ValidationError.of("NOTE_SHEET_NOT_FOUND", "Note Sheet with id: " + id + DOES_NOT_EXIST);
        }
        return null;

    }

    private ValidationError songExists(Long id) {
        if (!songDAO.existsByPK(id)) {
            return ValidationError.of("SONG_NOT_FOUND", "Song with id: " + id + DOES_NOT_EXIST);
        }
        return null;

    }

    private ValidationError instrumentExists(Long id) {
        if (!instrumentDAO.existsByPK(id)) {
            return ValidationError.of("INSTRUMENT_NOT_FOUND", "Instrument with id: " + id + DOES_NOT_EXIST);
        }
        return null;

    }

}
