package ba.com.zira.sdr.notesheet.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.NoteSheetService;
import ba.com.zira.sdr.api.model.notesheet.NoteSheet;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetCreateRequest;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetSongResponse;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.api.model.notesheet.SongInstrumentSheetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "notesheet", description = "NoteSheet API")
@RestController
@RequestMapping(value = "notesheet")
@AllArgsConstructor
public class NoteSheetRestService {

    private NoteSheetService noteSheetService;

    @Operation(summary = "Find Note Sheet base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<NoteSheet> find(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return noteSheetService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find notesheet by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<NoteSheetSongResponse> findNoteSheetForSong(
            @Parameter(required = true, description = "ID of the note sheet") @PathVariable final Long id) throws ApiException {
        return noteSheetService.findNoteSheetForSong(new EntityRequest<>(id));
    }

    @GetMapping(value = "{songId}/{instrumentId}")
    public PayloadResponse<NoteSheetSongResponse> findNoteSheetByInstrumentAndSong(@PathVariable Long songId,
            @PathVariable Long instrumentId) throws ApiException {
        EntityRequest<SongInstrumentSheetResponse> request = new EntityRequest<>();
        SongInstrumentSheetResponse entity = new SongInstrumentSheetResponse();
        entity.setSongId(songId);
        entity.setInstrumentId(instrumentId);
        request.setEntity(entity);
        return noteSheetService.findNoteSheetByInstrumentAndSong(request);
    }

    @Operation(summary = "Create note sheet")
    @PostMapping
    public PayloadResponse<NoteSheet> create(@RequestBody final NoteSheetCreateRequest notesheet) throws ApiException {
        return noteSheetService.create(new EntityRequest<>(notesheet));
    }

    @Operation(summary = "Update Note Sheet")
    @PutMapping(value = "{id}")
    public PayloadResponse<NoteSheet> edit(@Parameter(required = true, description = "ID of the note sheet") @PathVariable final Long id,
            @RequestBody final NoteSheetUpdateRequest notesheet) throws ApiException {
        if (notesheet != null) {
            notesheet.setId(id);
        }
        return noteSheetService.update(new EntityRequest<>(notesheet));
    }

    @Operation(summary = "Delete note sheet")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the note sheet") @PathVariable final Long id)
            throws ApiException {
        return noteSheetService.delete(new EntityRequest<>(id));
    }

}