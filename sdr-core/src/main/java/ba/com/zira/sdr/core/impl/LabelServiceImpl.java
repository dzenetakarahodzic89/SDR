package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.LabelService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.image.ImageCreateRequest;
import ba.com.zira.sdr.api.model.label.LabelArtistResponse;
import ba.com.zira.sdr.api.model.label.LabelCreateRequest;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.core.mapper.LabelMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.LabelRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.LabelEntity;

@Service

public class LabelServiceImpl implements LabelService {

    private LabelDAO labelDAO;
    private LabelMapper labelMapper;
    private PersonDAO personDAO;
    private LabelRequestValidation labelReqVal;
    @Autowired
    private ArtistDAO artistDAO;
    @Autowired
    LookupService lookupservice;
    @Autowired
    MediaService mediaService;

    ImageCreateRequest imageCreateRequest;

    @Autowired
    public LabelServiceImpl(LabelDAO labelDAO, LabelMapper labelMapper, PersonDAO personDAO, LabelRequestValidation labelReqVal) {
        super();
        this.labelDAO = labelDAO;
        this.labelMapper = labelMapper;
        this.personDAO = personDAO;
        this.labelReqVal = labelReqVal;
    }

    @Override
    public PagedPayloadResponse<LabelResponse> find(final FilterRequest request) {
        PagedData<LabelEntity> labelEntities = labelDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, labelEntities, labelMapper::entitiesToDtos);
    }

    @Override
    public PayloadResponse<LabelArtistResponse> findById(final EntityRequest<Long> request) {
        labelReqVal.validateExistsLabelRequest(request);

        LabelArtistResponse labelEntity = labelDAO.getById(request.getEntity());
        lookupservice.lookupCoverImage(Arrays.asList(labelEntity), LabelArtistResponse::getId, ObjectType.LABEL.getValue(),
                LabelArtistResponse::setImageUrl, LabelArtistResponse::getImageUrl);
        labelEntity.setArtists(artistDAO.getId(request.getEntity()));

        return new PayloadResponse<>(request, ResponseCode.OK, labelEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<LabelResponse> create(final EntityRequest<LabelCreateRequest> request) throws ApiException {

        var labelEntity = labelMapper.dtoToEntity(request.getEntity());
        var personEntity = personDAO.findByPK(request.getEntity().getFounderId());
        labelEntity.setStatus(Status.ACTIVE.value());
        labelEntity.setCreated(LocalDateTime.now());
        labelEntity.setCreatedBy(request.getUserId());
        labelEntity.setFounder(personEntity);

        labelDAO.persist(labelEntity);

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {

            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.LABEL.getValue());
            mediaRequest.setObjectId(labelEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");

            mediaService.save(new EntityRequest<>(mediaRequest, request));

        }

        return new PayloadResponse<>(request, ResponseCode.OK, labelMapper.entityToDto(labelEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<LabelResponse> update(final EntityRequest<LabelUpdateRequest> request) throws ApiException {
        labelReqVal.validateUpdateLabelRequest(request);

        var labelEntity = labelDAO.findByPK(request.getEntity().getId());
        var personEntity = personDAO.findByPK(request.getEntity().getFounderId());
        labelEntity.setModified(LocalDateTime.now());
        labelEntity.setModifiedBy(request.getUserId());
        labelEntity.setFounder(personEntity);

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {

            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.LABEL.getValue());
            mediaRequest.setObjectId(labelEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));

        }

        labelMapper.updateEntity(request.getEntity(), labelEntity);
        labelDAO.merge(labelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, labelMapper.entityToDto(labelEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        labelReqVal.validateExistsLabelRequest(request);

        var labelEntity = labelDAO.findByPK(request.getEntity());
        labelDAO.remove(labelEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, "Label successfully deleted!");

    }

}
