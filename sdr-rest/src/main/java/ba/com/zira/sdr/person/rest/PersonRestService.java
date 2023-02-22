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
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.PersonService;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
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
    public PayloadResponse<PersonResponse> delete(@Parameter(required = true, description = "ID of the record") @PathVariable final Long id)
            throws ApiException {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return personService.delete(entityRequest);
    }

}
