package ba.com.zira.sdr.country.rest;

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
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.CountryService;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.api.model.country.CountriesSearchRequest;
import ba.com.zira.sdr.api.model.country.CountryArtistSongResponse;
import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryGetByIdsRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Country", description = "Country API")
@RestController
@RequestMapping(value = "country")
@AllArgsConstructor
public class CountryRestService {
    private CountryService countryService;

    private BattleService battleService;

    @Operation(summary = "Find countries base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<CountryResponse> get(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return countryService.get(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create country")
    @PostMapping
    public PayloadResponse<CountryResponse> create(@RequestBody final CountryCreateRequest request) throws ApiException {
        return countryService.create(new EntityRequest<>(request));
    }

    @Operation(summary = "Update country")
    @PutMapping(value = "{id}")
    public PayloadResponse<CountryResponse> update(
            @Parameter(required = true, description = "ID of the country") @PathVariable final Long id,
            @RequestBody final CountryUpdateRequest request) throws ApiException {
        if (request != null) {
            request.setId(id);
        }
        return countryService.update(new EntityRequest<>(request));
    }

    @Operation(summary = "Delete country")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the country") @PathVariable final Long id)
            throws ApiException {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return countryService.delete(entityRequest);
    }

    @Operation(summary = "Get all")
    @GetMapping(value = "all")
    public ListPayloadResponse<CountryResponse> getAll() throws ApiException {
        var request = new EmptyRequest();
        return countryService.getAll(request);

    }

    @Operation(summary = "Find All Countries Except One With The Selected Id")
    @PostMapping(value = "search")
    public ListPayloadResponse<LoV> getAllCountriesExceptOneWithTheSelectedId(@RequestBody final CountriesSearchRequest request)
            throws ApiException {
        return countryService.getAllCountriesExceptOneWithTheSelectedId(new EntityRequest<>(request));
    }

    @Operation(summary = "Get all")
    @GetMapping(value = "all-countries")
    public ListPayloadResponse<LoV> getAllCountries() throws ApiException {
        var request = new EmptyRequest();
        return countryService.getAllCountries(request);

    }

    @Operation(summary = "Artists and songs by countryId")
    @GetMapping("/artists-songs")
    public PayloadResponse<CountryArtistSongResponse> getArtistsAndSongs(@RequestParam Long id) throws ApiException {
        var req = new EntityRequest<>(id);
        return countryService.getArtistsSongs(req);
    }

    @Operation(summary = "Create a battle")
    @PostMapping(value = "/battle-create")
    public PayloadResponse<BattleGenerateResponse> createA(@RequestBody(required = true) BattleGenerateRequest request)
            throws ApiException {
        return battleService.create(new EntityRequest<>(request));
    }

    @Operation(summary = "Find all countries by ids")
    @PostMapping(value = "get-by-ids")
    public ListPayloadResponse<LoV> getAllCountriesByIds(@RequestBody CountryGetByIdsRequest request) throws ApiException {
        return countryService.getAllCountryLoVsByIds(new EntityRequest<>(request));
    }

    @Operation(summary = "Get flags for ids")
    @PostMapping(value = "get-flags")
    public ListPayloadResponse<LoV> getCountryFlags(@RequestBody CountryGetByIdsRequest request) throws ApiException {
        return countryService.getCountryFlags(new EntityRequest<>(request));
    }

}
