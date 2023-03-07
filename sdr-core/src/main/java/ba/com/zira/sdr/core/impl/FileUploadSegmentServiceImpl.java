package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.FileUploadSegmentService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.enums.FileUploadSegmentStatus;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentCreateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.core.mapper.FileUploadSegmentMapper;
import ba.com.zira.sdr.dao.FileUploadSegmentDAO;
import ba.com.zira.sdr.dao.MediaDAO;
import ba.com.zira.sdr.dao.model.MediaEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileUploadSegmentServiceImpl implements FileUploadSegmentService {
    MediaDAO mediaDAO;
    FileUploadSegmentMapper fileUploadSegmentMapper;
    FileUploadSegmentDAO fileUploadSegmentDAO;
    MediaService mediaService;
    private static final int SCHEDULE_REPEAT_COUNT = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadSegmentServiceImpl.class);

    @Override
    public PayloadResponse<String> create(EntityRequest<FileUploadSegmentCreateRequest> request) throws ApiException {
        var requestEntity = request.getEntity();
        if (requestEntity.getFileSegment() > requestEntity.getFileSegmentTotal()) {
            throw ApiException.createFrom(request, ResponseCode.REQUEST_INVALID);
        }
        var media = mediaDAO.findByTypeAndId(requestEntity.getType(), requestEntity.getMediaObjectId());
        if (media == null) {
            var createMedia = new MediaEntity();
            createMedia.setObjectType(requestEntity.getType());
            createMedia.setObjectId(requestEntity.getMediaObjectId());
            createMedia.setCreated(LocalDateTime.now());
            createMedia.setCreatedBy(request.getUserId());
            media = mediaDAO.persist(createMedia);
        }
        var saveFileSegmentEntity = fileUploadSegmentMapper.dtoToEntity(request.getEntity());
        saveFileSegmentEntity.setId(UUID.randomUUID().toString());
        saveFileSegmentEntity.setCreated(LocalDateTime.now());
        saveFileSegmentEntity.setCreatedBy(request.getUserId());
        saveFileSegmentEntity.setStatus(FileUploadSegmentStatus.UPLOADING.getValue());
        saveFileSegmentEntity.setMedia(media);
        fileUploadSegmentDAO.persist(saveFileSegmentEntity);
        if (fileUploadSegmentDAO.countAllFieldsByMedia(media.getId()).equals(saveFileSegmentEntity.getFileSegmentTotal())) {
            fileUploadSegmentDAO.updateStatus(FileUploadSegmentStatus.READY_TO_MERGE.getValue(), saveFileSegmentEntity.getMedia().getId());
        }
        return new PayloadResponse<>(request, ResponseCode.OK, "ok");
    }

    @Override
    public PayloadResponse<String> getMediaStatus(EntityRequest<LoV> request) throws ApiException {
        var media = mediaDAO.findByTypeAndId(request.getEntity().getName(), request.getEntity().getId());
        if (media == null) {
            return new PayloadResponse<>(request, ResponseCode.OK, "Not uploaded yet");
        }
        var status = "";
        if (fileUploadSegmentDAO.countAllFieldsByMedia(media.getId()) == 0) {
            status = "Upload not started yet";
        } else {
            status = fileUploadSegmentDAO.checkStatusOfMediaId(media.getId());
        }
        return new PayloadResponse<>(request, ResponseCode.OK, status);
    }

    @Scheduled(fixedDelay = 600000L) // 10 mins
    public void uploadFileSegmentsToServer() {
        for (int i = 0; i < SCHEDULE_REPEAT_COUNT; i++) {
            StringBuilder connectedBase64String = new StringBuilder();
            try {
                var readyToMergeMediaId = fileUploadSegmentDAO.getReadyToMergeMediaId();
                if (readyToMergeMediaId != null) {
                    var fileSegmentUploadList = fileUploadSegmentDAO.getSegmentsByMediaId(readyToMergeMediaId);
                    for (var file : fileSegmentUploadList) {
                        connectedBase64String.append(file.getFileSegmentContent());
                    }
                    var mediaRequest = new MediaCreateRequest();
                    mediaRequest.setObjectType(ObjectType.SONG.getValue());
                    mediaRequest.setObjectId(fileSegmentUploadList.get(0).getMediaId());
                    mediaRequest.setMediaObjectData(connectedBase64String.toString());
                    mediaRequest.setMediaObjectName(fileSegmentUploadList.get(0).getFileName());
                    mediaRequest.setMediaStoreType("AUDIO");
                    mediaRequest.setMediaObjectType("SONG");
                    mediaService.save(new EntityRequest<>(mediaRequest));
                    fileUploadSegmentDAO.updateStatus(FileUploadSegmentStatus.SAVED.getValue(), fileSegmentUploadList.get(0).getMediaId());
                }
            } catch (Exception ex) {
                LOGGER.debug("No entity found for upload!");
                return;
            }
        }

    }
}
