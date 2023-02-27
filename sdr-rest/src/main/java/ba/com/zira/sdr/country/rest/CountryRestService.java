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
import ba.com.zira.sdr.api.CountryService;
import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
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
}
