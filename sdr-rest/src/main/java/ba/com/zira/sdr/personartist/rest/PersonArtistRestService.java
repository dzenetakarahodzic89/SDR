package ba.com.zira.sdr.personartist.rest;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.PersonArtistService;
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.api.model.personartist.PersonArtistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Person artist", description = "Person artist rest service")
@RestController
@RequestMapping(value = "person-artist")
@AllArgsConstructor
public class PersonArtistRestService {

    private PersonArtistService personArtistService;

    @Operation(summary = "Find person-artist records based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<PersonArtistResponse> get(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return personArtistService.get(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create person artist")
    @PostMapping
    public PayloadResponse<PersonArtistResponse> create(@RequestBody final PersonArtistCreateRequest songArtist) throws ApiException {
        return personArtistService.create(new EntityRequest<>(songArtist));
    }

    @Operation(summary = "Delete person artist")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the record") @PathVariable final Long id) {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return personArtistService.delete(entityRequest);
    }

}
