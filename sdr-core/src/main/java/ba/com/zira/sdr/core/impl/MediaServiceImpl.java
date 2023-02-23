package ba.com.zira.sdr.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.FileUploadService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.model.image.ImageUploadRequest;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectResponse;
import ba.com.zira.sdr.api.model.media.MediaResponse;
import ba.com.zira.sdr.api.model.media.MediaStoreResponse;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.MediaMapper;
import ba.com.zira.sdr.core.mapper.MediaStoreMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.MediaRequestValidation;
import ba.com.zira.sdr.dao.MediaDAO;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import ba.com.zira.sdr.dao.model.MediaEntity;
import ba.com.zira.sdr.dao.model.MediaStoreEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {

    MediaRequestValidation mediaRequestValidation;
    MediaStoreDAO mediaStoreDAO;
    MediaDAO mediaDAO;
    MediaMapper mediaMapper;
    MediaStoreMapper mediaStoreMapper;
    FileUploadService fileUploadService;
    LookupService lookupService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Override
    public PayloadResponse<String> save(final EntityRequest<MediaCreateRequest> request) throws ApiException {
        if ("IMAGE".equalsIgnoreCase(request.getEntity().getMediaObjectType())) {
            mediaRequestValidation.validateImageName(request);
            LOGGER.debug("Entered image saving!");
            var imageUploadRequest = new ImageUploadRequest();
            imageUploadRequest.setImageData(request.getEntity().getMediaObjectData());
            imageUploadRequest.setImageName(request.getEntity().getMediaObjectName());
            imageUploadRequest.setFileType(request.getEntity().getMediaFileType());
            EntityRequest<ImageUploadRequest> uploadRequest = new EntityRequest<>();
            uploadRequest.setEntity(imageUploadRequest);
            Map<String, String> imageInfo = fileUploadService.uploadImage(uploadRequest);
            LOGGER.debug("Image info is {}", imageInfo);
            if (imageInfo != null) {
                var existingMediaEntity = mediaDAO.findByTypeAndId(request.getEntity().getObjectType(), request.getEntity().getObjectId());
                if (existingMediaEntity == null) {
                    LOGGER.debug("A new media entity has been created for {} + {}", request.getEntity().getObjectId(),
                            request.getEntity().getObjectType());
                    existingMediaEntity = new MediaEntity();
                    existingMediaEntity.setCreated(LocalDateTime.now());
                    existingMediaEntity.setCreatedBy(request.getUserId());
                    existingMediaEntity.setObjectId(request.getEntity().getObjectId());
                    existingMediaEntity.setObjectType(request.getEntity().getObjectType());
                    existingMediaEntity = mediaDAO.persist(existingMediaEntity);
                }
                var mediaStoreEntity = new MediaStoreEntity();
                mediaStoreEntity.setId(UUID.randomUUID().toString());
                mediaStoreEntity.setCreated(LocalDateTime.now());
                mediaStoreEntity.setCreatedBy(request.getUserId());
                mediaStoreEntity.setName(imageInfo.get("baseName"));
                mediaStoreEntity.setExtension(imageInfo.get("extension"));
                mediaStoreEntity.setType(request.getEntity().getMediaStoreType());
                mediaStoreEntity.setUrl(imageInfo.get("url"));
                mediaStoreEntity.setMedia(existingMediaEntity);
                LOGGER.debug("New media store entity for {} has been created", mediaStoreEntity.getUrl());
                mediaStoreDAO.persist(mediaStoreEntity);
            }
        }
        return new PayloadResponse<>(request, ResponseCode.OK, "Media Saved!");

    }

    @Override
    public PagedPayloadResponse<MediaResponse> find(final FilterRequest request) throws ApiException {

        PagedData<MediaEntity> mediaEntities = mediaDAO.findAll(request.getFilter());
        List<MediaResponse> mediaResp = mediaMapper.entitiesToDtos(mediaEntities.getRecords());
        PagedData<MediaResponse> mediaResponses = new PagedData<>();
        mediaResponses.setRecords(mediaResp);
        PagedDataMetadataMapper.remapMetadata(mediaEntities, mediaResponses);

        return new PagedPayloadResponse<>(request, ResponseCode.OK, mediaResponses);

    }

    @Override
    public PayloadResponse<MediaObjectResponse> findByIdAndObjectType(final EntityRequest<MediaObjectRequest> request) throws ApiException {

        var mediaEntity = mediaDAO.findByTypeAndId(request.getEntity().getObjectType(), request.getEntity().getObjectId());

        if (mediaEntity == null) {
            var media = new MediaObjectResponse();
            media.setObjectType(request.getEntity().getObjectType());
            media.setObjectId(request.getEntity().getObjectId());
            lookupService.lookupObjectNamesByIdAndType(media.getObjectType(), Arrays.asList(media), MediaObjectResponse::getObjectId,
                    MediaObjectResponse::setObjectName);
            return new PayloadResponse<>(request, ResponseCode.OK, media);
        }

        MediaObjectResponse media = mediaMapper.entitiesToDto(mediaEntity);

        lookupService.lookupObjectNamesByIdAndType(media.getObjectType(), Arrays.asList(media), MediaObjectResponse::getObjectId,
                MediaObjectResponse::setObjectName);

        List<MediaStoreResponse> mediaStores = media.getMediaStores();

        media.setMediaStores(mediaStores);

        return new PayloadResponse<>(request, ResponseCode.OK, media);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayloadResponse<String> create(final EntityRequest<MediaCreateRequest> request) throws ApiException {
        mediaRequestValidation.validateCreateMediaRequest(request);

        var mediaEntity = new MediaEntity();
        mediaEntity.setCreated(LocalDateTime.now());
        mediaEntity.setCreatedBy(request.getUserId());
        mediaEntity.setObjectId(request.getEntity().getObjectId());
        mediaEntity.setObjectType(request.getEntity().getObjectType());
        mediaDAO.persist(mediaEntity);

        return new PayloadResponse<>(request, ResponseCode.OK,
                String.format("Media %s has been created!", mediaMapper.entityToDto(mediaEntity)));
    }

}
