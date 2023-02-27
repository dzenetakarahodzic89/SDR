package ba.com.zira.sdr.media.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectResponse;
import ba.com.zira.sdr.api.model.media.MediaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "media", description = "Media API")
@RestController
@RequestMapping(value = "media")
@AllArgsConstructor
public class MediaRestService {

    private MediaService mediaService;

    @Operation(summary = "Find Media base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<MediaResponse> find(@RequestParam final Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return mediaService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find Media based on ObjectId and ObjectType")
    @GetMapping(value = "/object")
    public PayloadResponse<MediaObjectResponse> findByIdAndObjectType(@RequestParam final String objectType,
            @RequestParam final Long objectId) throws ApiException {
        var mediaObjectRequest = new MediaObjectRequest(objectId, objectType);
        mediaObjectRequest.setObjectId(objectId);
        mediaObjectRequest.setObjectType(objectType);
        return mediaService.findByIdAndObjectType(new EntityRequest<>(mediaObjectRequest));
    }

    @Operation(summary = "Create media")
    @PostMapping
    public PayloadResponse<String> create(@RequestBody EntityRequest<MediaCreateRequest> media) throws ApiException {
        return mediaService.create(media);
    }

    @Operation(summary = "Create media")
    @PostMapping(value = "add-file")
    public PayloadResponse<String> addNewFile(@RequestBody final EntityRequest<MediaCreateRequest> media) throws ApiException {
        return mediaService.save(media);
    }

}
