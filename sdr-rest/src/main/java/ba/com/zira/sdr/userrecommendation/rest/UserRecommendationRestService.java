package ba.com.zira.sdr.userrecommendation.rest;

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
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.UserRecommendationService;
import ba.com.zira.sdr.api.model.userrecommendation.AverageScorePerCountry;
import ba.com.zira.sdr.api.model.userrecommendation.ScoreCompareRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.api.model.userrecommendation.UserScoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "User recommendation", description = "User recommendation API")
@RestController
@RequestMapping(value = "user-recommendation")
@AllArgsConstructor
public class UserRecommendationRestService {

    private UserRecommendationService userRecommendationService;

    @Operation(summary = "Find user recommendation base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<UserRecommendationResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return userRecommendationService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find user recommendation base on id")
    @GetMapping(value = "{id}")
    public PayloadResponse<UserRecommendationResponse> findById(
            @Parameter(required = true, description = "ID of the user recommendation") @PathVariable final Long id) throws ApiException {

        return userRecommendationService.findById(new EntityRequest<>(id));
    }

    @Operation(summary = "Create user recommendation")
    @PostMapping
    public PayloadResponse<UserRecommendationResponse> create(@RequestBody final UserRecommendationCreateRequest comment)
            throws ApiException {
        return userRecommendationService.create(new EntityRequest<>(comment));
    }

    @Operation(summary = "Delete user recommendation")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(
            @Parameter(required = true, description = "ID of the user recommendation") @PathVariable final Long id) throws ApiException {
        return userRecommendationService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "find all users")
    @GetMapping(value = "/all")
    public ListPayloadResponse<UserScoreResponse> findAllUsers() throws ApiException {
        var req = new EmptyRequest();
        return userRecommendationService.findAllUsers(req);
    }

    @Operation(summary = "Compare user score")
    @PostMapping(value = "/compare")
    public ListPayloadResponse<UserScoreResponse> find(@RequestBody final ScoreCompareRequest request) throws ApiException {
        return userRecommendationService.scoreCompare(new EntityRequest<>(request));
    }

    @Operation(summary = "Get average user recommendation score per country from a specified recommendation service")
    @GetMapping(value = "/avg-per-country")
    public ListPayloadResponse<AverageScorePerCountry> getAverageScorePerCountryForUser(
            @Parameter(required = true, description = "Recommendation service") @RequestParam String service) throws ApiException {

        var req = new EntityRequest<String>(service);
        return userRecommendationService.getAverageScorePerCountry(req);
    }

}