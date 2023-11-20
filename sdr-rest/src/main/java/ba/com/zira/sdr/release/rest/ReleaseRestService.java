package ba.com.zira.sdr.release.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.ReleaseService;
import ba.com.zira.sdr.api.model.release.ReleaseSearchRequest;
import ba.com.zira.sdr.api.model.release.ReleaseSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "release", description = "Release API")
@RestController
@RequestMapping(value = "release")
@AllArgsConstructor
public class ReleaseRestService {

    @Autowired
    private ReleaseService releaseService;

    @Operation(summary = "Release search")
    @GetMapping(value = "/search")
    public PagedPayloadResponse<ReleaseSearchResponse> find(@RequestParam(required = false) final String name,
            @RequestParam(required = false) final List<Long> countryIds, final QueryConditionPage queryCriteria) throws ApiException {
        return releaseService.find(new SearchRequest<>(new ReleaseSearchRequest(name, countryIds), new HashMap<>(), queryCriteria));
    }

}
