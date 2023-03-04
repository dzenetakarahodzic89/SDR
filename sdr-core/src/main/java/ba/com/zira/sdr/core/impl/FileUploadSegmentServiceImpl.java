package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.FileUploadSegmentService;
import ba.com.zira.sdr.api.enums.FileUploadSegmentStatus;
import ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentCreateRequest;
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

    @Override
    public PayloadResponse<String> create(EntityRequest<FileUploadSegmentCreateRequest> request) throws ApiException {
        var requestEntity = request.getEntity();
        var media = mediaDAO.findByTypeAndId(requestEntity.getType(), requestEntity.getId());
        if (media == null) {
            var createMedia = new MediaEntity();
            createMedia.setObjectType(requestEntity.getType());
            createMedia.setObjectId(requestEntity.getId());
            createMedia.setCreated(LocalDateTime.now());
            createMedia.setCreatedBy(request.getUserId());
            media = mediaDAO.persist(createMedia);
        }
        var saveFileSegmentEntity = fileUploadSegmentMapper.dtoToEntity(request.getEntity());
        saveFileSegmentEntity.setId(UUID.randomUUID().toString());
        saveFileSegmentEntity.setCreated(LocalDateTime.now());
        saveFileSegmentEntity.setCreatedBy(request.getUserId());
        saveFileSegmentEntity.setStatus(FileUploadSegmentStatus.UPLOADING.getValue());
        fileUploadSegmentDAO.persist(saveFileSegmentEntity);
        if (saveFileSegmentEntity.getFileSegment().equals(saveFileSegmentEntity.getFileSegmentTotal())) {
            fileUploadSegmentDAO.updateStatus(FileUploadSegmentStatus.READY_TO_MERGE.getValue(), saveFileSegmentEntity.getMedia().getId());
        }
        return new PayloadResponse<>(request, ResponseCode.OK, "ok");
    }

}
