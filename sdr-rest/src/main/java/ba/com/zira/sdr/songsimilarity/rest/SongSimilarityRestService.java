package ba.com.zira.sdr.songsimilarity.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.SongSimilarityService;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarity;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityCreateRequest;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Service
@Tag(name = "SongSimilarity", description = "SongSimilarity API")
@RestController
@RequestMapping(value = "song-similarity")
@AllArgsConstructor

public class SongSimilarityRestService {

    private SongSimilarityService songSimilarityService;

    @Operation(summary = "Create")
    @PostMapping
    public PayloadResponse<SongSimilarity> create(@RequestBody final SongSimilarityCreateRequest songSimilarityCreateRequest)
            throws ApiException {
        return songSimilarityService.create(new EntityRequest<>(songSimilarityCreateRequest));
    }

    @Operation(summary = "Get all")
    @GetMapping(value = "all")
    public ListPayloadResponse<SongSimilarityResponse> getAll() throws ApiException {
        return songSimilarityService.getAll(new EmptyRequest());
    }

}
