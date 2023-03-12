package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.EraService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.era.EraCreateRequest;
import ba.com.zira.sdr.api.model.era.EraResponse;
import ba.com.zira.sdr.api.model.era.EraSearchRequest;
import ba.com.zira.sdr.api.model.era.EraSearchResponse;
import ba.com.zira.sdr.api.model.era.EraUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.core.mapper.EraMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.EraRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.GenreDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EraServiceImpl implements EraService {

    EraDAO eraDAO;
    ArtistDAO artistDAO;
    GenreDAO genreDAO;
    LookupService lookupService;
    EraMapper eraMapper;
    EraRequestValidation eraRequestValidation;
    @Autowired
    MediaService mediaService;

    @Override
    public ListPayloadResponse<LoV> getEraLoVs(EmptyRequest req) throws ApiException {
        List<LoV> eras = eraDAO.getAllErasLoV();
        return new ListPayloadResponse<>(req, ResponseCode.OK, eras);
    }

    @Override
    public ListPayloadResponse<EraSearchResponse> find(final EntityRequest<EraSearchRequest> request) {
        // EraRequestValidation.validateExistsEraRequest(request);

        List<EraSearchResponse> eras = eraDAO.find(request.getEntity().getName(), request.getEntity().getSortBy(),

                request.getEntity().getPage(), request.getEntity().getPageSize(), request.getEntity().getAlbumIds(),
                request.getEntity().getArtistIds(), request.getEntity().getGenreIds());

        lookupService.lookupCoverImage(eras, EraSearchResponse::getId, ObjectType.ERA.getValue(), EraSearchResponse::setImageUrl,
                EraSearchResponse::getImageUrl);

        return new ListPayloadResponse<>(request, ResponseCode.OK, eras);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<EraResponse> create(final EntityRequest<EraCreateRequest> request) throws ApiException {
        var eraEntity = eraMapper.dtoToEntity(request.getEntity());

        eraEntity.setStatus(Status.ACTIVE.value());
        eraEntity.setCreated(LocalDateTime.now());
        eraEntity.setCreatedBy(request.getUserId());

        eraDAO.persist(eraEntity);

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {

            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.ERA.getValue());
            mediaRequest.setObjectId(eraEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));

        }

        return new PayloadResponse<>(request, ResponseCode.OK, eraMapper.entityToDto(eraEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<EraResponse> update(final EntityRequest<EraUpdateRequest> request) throws ApiException {
        eraRequestValidation.validateUpdateEraRequest(request);

        var eraEntity = eraDAO.findByPK(request.getEntity().getId());
        eraMapper.updateEntity(request.getEntity(), eraEntity);

        eraEntity.setModified(LocalDateTime.now());
        eraEntity.setModifiedBy(request.getUserId());

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {

            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.ERA.getValue());
            mediaRequest.setObjectId(eraEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));

        }

        eraDAO.merge(eraEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, eraMapper.entityToDto(eraEntity));
    }

    @Override
    public PayloadResponse<EraResponse> findById(final EntityRequest<Long> request) throws ApiException {
        eraRequestValidation.validateExistsEraRequest(request);

        var eraEntity = eraDAO.findByPK(request.getEntity());
        var era = eraMapper.entityToDto(eraEntity);

        lookupService.lookupCoverImage(Arrays.asList(era), EraResponse::getId, ObjectType.ERA.getValue(), EraResponse::setImageUrl,
                EraResponse::getImageUrl);

        return new PayloadResponse<>(request, ResponseCode.OK, era);
    }

}
