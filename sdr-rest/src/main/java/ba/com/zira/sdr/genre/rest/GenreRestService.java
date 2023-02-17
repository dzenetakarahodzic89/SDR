package ba.com.zira.sdr.genre.rest;

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
import ba.com.zira.sdr.api.GenreService;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "genre", description = "Genre API")
@RestController
@RequestMapping(value = "genre")
@AllArgsConstructor
public class GenreRestService {

    private GenreService genreService;

    @Operation(summary = "Find genres based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<Genre> find(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return genreService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create a genre")
    @PostMapping
    public PayloadResponse<Genre> create(@RequestBody final GenreCreateRequest genre) throws ApiException {
        return genreService.create(new EntityRequest<>(genre));
    }

    @Operation(summary = "Update a genre")
    @PutMapping(value = "{id}")
    public PayloadResponse<Genre> edit(@Parameter(required = true, description = "ID of the genre") @PathVariable final Long id,
            @RequestBody final GenreUpdateRequest genre) throws ApiException {
        if (genre != null) {
            genre.setId(id);
        }
        return genreService.update(new EntityRequest<>(genre));
    }

    @Operation(summary = "Activate genre")
    @PutMapping(value = "{id}/activate")
    public PayloadResponse<Genre> activate(@Parameter(required = true, description = "ID of the genre") @PathVariable final Long id)
            throws ApiException {
        return genreService.activate(new EntityRequest<>(id));
    }

    @Operation(summary = "Delete a genre")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<Genre> delete(@Parameter(required = true, description = "ID of the genre") @PathVariable final Long id)
            throws ApiException {
        return genreService.delete(new EntityRequest<>(id));
    }

}
