package ba.com.zira.sdr.songsimilaritydetail.rest;

import java.util.Map;

import org.springframework.stereotype.Service;
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
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.SongSimilarityDetailService;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateReq;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailResponse;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Service
@Tag(name = "SongSimilarityDetail", description = "SongSimilarityDetail API")
@RestController
@RequestMapping(value = "song-similarity-detail")
@AllArgsConstructor

public class SongSimilarityDetailRestService {

    private SongSimilarityDetailService songSimilarityDetailService;

    @Operation(summary = "Get all")
    @GetMapping(value = "all")
    public ListPayloadResponse<SongSimilarityDetailResponse> getAll(@RequestParam Long id) throws ApiException {
        return songSimilarityDetailService.getAll(new EntityRequest<>(id));
    }

    @Operation(summary = "Find song sample details based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<SongSimilarityDetail> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return songSimilarityDetailService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create")
    @PostMapping
    public PayloadResponse<SongSimilarityDetail> create(@RequestBody final SongSimilarityDetailCreateReq songSimilarityCreateRequest) {
        return songSimilarityDetailService.createSongSimilarityDetail(new EntityRequest<>(songSimilarityCreateRequest));
    }

    @Operation(summary = "Update song sample detail")
    @PutMapping(value = "{id}")
    public PayloadResponse<SongSimilarityDetail> edit(
            @Parameter(required = true, description = "ID of the sample detail") @PathVariable final Long id,
            @RequestBody final SongSimilarityDetailUpdateRequest song) throws ApiException {
        if (song != null) {
            song.setId(id);
        }

        return songSimilarityDetailService.update(new EntityRequest<>(song));
    }

    @Operation(summary = "Delete song sample detail")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<SongSimilarityDetail> delete(
            @Parameter(required = true, description = "ID of the song sample detail") @PathVariable final Long id) throws ApiException {
        return songSimilarityDetailService.delete(new EntityRequest<>(id));
    }

}
