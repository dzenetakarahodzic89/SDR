package ba.com.zira.sdr.countryrelation.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.request.ListRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.CountryRelationService;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "CountryRelation", description = "Country Relation API")
@RestController
@RequestMapping(value = "country-relation")
@AllArgsConstructor
public class CountryRelationRestService {
    private CountryRelationService countryRelationService;

    @Operation(summary = "Create country relations")
    @PostMapping(value = "add")
    public PayloadResponse<String> createCountriesRelation(@RequestBody final ListRequest<CountryRelationCreateRequest> request)
            throws ApiException {
        return countryRelationService.createCountriesRelation(request);
    }

    @Operation(summary = "Get country relation for country")
    @GetMapping(value = "{id}")
    public ListPayloadResponse<LoV> getRelationsForCountry(
            @Parameter(required = true, description = "ID of the country") @PathVariable final Long id) throws ApiException {
        return countryRelationService.getCountryrelationsForCountry(new EntityRequest<>(id));
    }

}
