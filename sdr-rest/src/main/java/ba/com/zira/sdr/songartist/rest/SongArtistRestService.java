package ba.com.zira.sdr.songartist.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Song artist", description = "Song artist rest service")
@RestController
@RequestMapping(value = "song-artist")
@AllArgsConstructor
public class SongArtistRestService {
    private SongArtistService songArtistService;

    @Operation(summary = "Return all song artist records")
    @GetMapping
    public ListPayloadResponse<SongArtistResponse> getAll() throws ApiException {
        EmptyRequest request = new EmptyRequest();
        return songArtistService.getAll(request);
    }
}
