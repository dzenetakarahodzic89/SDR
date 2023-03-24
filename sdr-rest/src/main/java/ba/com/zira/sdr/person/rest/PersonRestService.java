package ba.com.zira.sdr.person.rest;

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
import ba.com.zira.sdr.api.PersonService;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.person.PersonCountResponse;
import ba.com.zira.sdr.api.model.person.PersonCountryRequest;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonOverviewResponse;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonSearchRequest;
import ba.com.zira.sdr.api.model.person.PersonSearchResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "person", description = "Person Api")
@RestController
@RequestMapping(value = "person")
@AllArgsConstructor
public class PersonRestService {

    private PersonService personService;

    @Operation(summary = "Find person base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<PersonResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return personService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find person by id - Overview")
    @GetMapping(value = "/overview/{id}")
    public PayloadResponse<PersonOverviewResponse> retrieveById(
            @Parameter(required = true, description = "ID of the song") @PathVariable final Long id) throws ApiException {
        return personService.retrieveById(new EntityRequest<>(id));
    }

    @Operation(summary = "Find person base on filter criteria")
    @GetMapping(value = "/{id}")
    public PayloadResponse<PersonResponse> findById(@PathVariable Long id) {
        return personService.findById(new EntityRequest<>(id));
    }

    @Operation(summary = "Create person")
    @PostMapping
    public PayloadResponse<PersonResponse> create(@RequestBody final PersonCreateRequest person) throws ApiException {
        return personService.create(new EntityRequest<>(person));
    }

    @Operation(summary = "Update Person")
    @PutMapping(value = "{id}")
    public PayloadResponse<PersonResponse> edit(@Parameter(required = true, description = "ID of the Person") @PathVariable final Long id,
            @RequestBody final PersonUpdateRequest person) throws ApiException {
        if (person != null) {
            person.setId(id);
        }
        return personService.update(new EntityRequest<>(person));
    }

    @Operation(summary = "Delete person")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the Person") @PathVariable final Long id) {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return personService.delete(entityRequest);
    }

    @Operation(summary = "Update Person")
    @PutMapping(value = "change-flag")
    public PayloadResponse<PersonResponse> editPersonCountry(@RequestBody final PersonCountryRequest request) {

        return personService.updatePersonCountry(new EntityRequest<>(request));
    }

    @Operation(summary = "Get all Person - LoV")
    @GetMapping(value = "/lov")
    public ListPayloadResponse<LoV> getPersonsLoV() throws ApiException {
        var req = new EmptyRequest();
        return personService.getPersonLoVs(req);
    }

    @Operation(summary = "Person search")
    @PostMapping(value = "/search")
    public ListPayloadResponse<PersonSearchResponse> find(@RequestBody final PersonSearchRequest request) throws ApiException {
        return personService.search(new EntityRequest<>(request));
    }

    @Operation(summary = "Get all Person - Country")
    @GetMapping(value = "/person-statistics")
    public PayloadResponse<PersonCountResponse> getPersonsByCountry() throws ApiException {
        var req = new EmptyRequest();
        return personService.getPersonsByCountry(req);
    }

}
