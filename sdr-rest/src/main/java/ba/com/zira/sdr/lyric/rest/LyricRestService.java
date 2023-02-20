package ba.com.zira.sdr.lyric.rest;

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
import ba.com.zira.sdr.api.LyricService;
import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "lyric", description = "Lyric API")
@RestController
@RequestMapping(value = "lyric")
@AllArgsConstructor
public class LyricRestService {

    private LyricService lyricService;

    @Operation(summary = "Find Lyrics base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<Lyric> find(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return lyricService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create lyric")
    @PostMapping
    public PayloadResponse<Lyric> create(@RequestBody final LyricCreateRequest lyric) throws ApiException {
        return lyricService.create(new EntityRequest<>(lyric));
    }

    @Operation(summary = "Update Lyric")
    @PutMapping(value = "{id}")
    public PayloadResponse<Lyric> edit(@Parameter(required = true, description = "ID of the lyric") @PathVariable final Long id,
            @RequestBody final LyricUpdateRequest lyric) throws ApiException {
        if (lyric != null) {
            lyric.setId(id);
        }
        return lyricService.update(new EntityRequest<>(lyric));
    }

    @Operation(summary = "Delete lyric")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<Lyric> delete(@Parameter(required = true, description = "ID of the lyric") @PathVariable final Long id)
            throws ApiException {
        return lyricService.delete(new EntityRequest<>(id));
    }

}
