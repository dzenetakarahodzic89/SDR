package ba.com.zira.sdr.userrecommendationdetail.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import ba.com.zira.sdr.api.UserRecommendationDetailService;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "user-recommendation-detail", description = "User Recommendation Detail Api")
@RestController
@RequestMapping(value = "user-recommendation-detail")
@AllArgsConstructor
public class UserRecommendationDetailRestService {

	private UserRecommendationDetailService userRecommendationDetailService;

	@Operation(summary = "Find user Recommendation Detail base on filter criteria")
	@GetMapping
	public PagedPayloadResponse<UserRecommendationDetailResponse> find(@RequestParam Map<String, Object> filterCriteria,
			final QueryConditionPage queryCriteria) throws ApiException {
		return userRecommendationDetailService.find(new FilterRequest(filterCriteria, queryCriteria));
	}

	@Operation(summary = "Create user recommendation detail")
	@PostMapping
	public PayloadResponse<UserRecommendationDetailResponse> create(
			@RequestBody final UserRecommendationDetailCreateRequest userRecommendationDetail) throws ApiException {

		return userRecommendationDetailService.create(new EntityRequest<>(userRecommendationDetail));
	}

	@Operation(summary = "Delete user recommendation detail")
	@DeleteMapping(value = "{id}")
	public PayloadResponse<UserRecommendationDetailResponse> delete(
			@Parameter(required = true, description = "ID of the record") @PathVariable final Long id)
			throws ApiException {
		EntityRequest<Long> entityRequest = new EntityRequest<>();
		entityRequest.setEntity(id);
		return userRecommendationDetailService.delete(entityRequest);
	}

}
