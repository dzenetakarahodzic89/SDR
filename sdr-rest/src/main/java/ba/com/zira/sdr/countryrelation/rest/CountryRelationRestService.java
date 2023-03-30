package ba.com.zira.sdr.countryrelation.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.ListRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.CountryRelationService;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
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

}
