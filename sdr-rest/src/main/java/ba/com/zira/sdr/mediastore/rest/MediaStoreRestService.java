package ba.com.zira.sdr.mediastore.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.MediaStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "mediaStore", description = "MediaStore API")
@RestController
@RequestMapping(value = "media-store")
@AllArgsConstructor
public class MediaStoreRestService {

    private MediaStoreService mediaStoreService;

    @Operation(summary = "Delete power")
    @DeleteMapping(value = "/{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of mediaStore") @PathVariable final String id)
            throws ApiException {
        return mediaStoreService.delete(new EntityRequest<>(id));
    }

}
