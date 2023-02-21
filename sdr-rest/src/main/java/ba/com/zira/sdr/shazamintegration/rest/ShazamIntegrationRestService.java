package ba.com.zira.sdr.shazamintegration.rest;

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
import ba.com.zira.sdr.api.ShazamIntegrationService;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationResponse;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "shazamIntegration", description = "Shazam Integration API")
@RestController
@RequestMapping(value = "shazamIntegration")
@AllArgsConstructor
public class ShazamIntegrationRestService {

	private ShazamIntegrationService shazamIntegrationService;

	@Operation(summary = "Find Shazam Integration base on filter criteria")
	@GetMapping
	public PagedPayloadResponse<ShazamIntegrationResponse> find(@RequestParam Map<String, Object> filterCriteria,
			final QueryConditionPage queryCriteria) throws ApiException {
		return shazamIntegrationService.find(new FilterRequest(filterCriteria, queryCriteria));
	}

	@Operation(summary = "Create Shazam Integration")
	@PostMapping
	public PayloadResponse<ShazamIntegrationResponse> create(@RequestBody final ShazamIntegrationCreateRequest sample)
			throws ApiException {
		return shazamIntegrationService.create(new EntityRequest<>(sample));
	}

	@Operation(summary = "Update Shazam Integration")
	@PutMapping(value = "{id}")
	public PayloadResponse<ShazamIntegrationResponse> edit(
			@Parameter(required = true, description = "ID of the Shazam Integration") @PathVariable final Long id,
			@RequestBody final ShazamIntegrationUpdateRequest sample) throws ApiException {
		if (sample != null) {
			sample.setId(id);
		}
		return shazamIntegrationService.update(new EntityRequest<>(sample));
	}

	@Operation(summary = "Delete Shazam integration")
	@DeleteMapping(value = "{id}/delete")
	public PayloadResponse<String> delete(
			@Parameter(required = true, description = "ID of the Shazam integration") @PathVariable final Long id)
			throws ApiException {
		return shazamIntegrationService.delete(new EntityRequest<>(id));
	}
}