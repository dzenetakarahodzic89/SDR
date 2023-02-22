package ba.com.zira.sdr.core.impl;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ChordProgressionService;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.ChordProgressionMapper;
import ba.com.zira.sdr.core.validation.ChordProgressionValidation;
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChordProgressionServiceImpl implements ChordProgressionService {

    ChordProgressionDAO chordProgressionDAO;
    ChordProgressionMapper chordProgressionMapper;
    ChordProgressionValidation chordProgressionValidator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ChordProgressionResponse> create(EntityRequest<ChordProgressionCreateRequest> request) {
        var chordProgressEntity = chordProgressionMapper.dtoToEntity(request.getEntity());
        chordProgressEntity.setStatus(Status.ACTIVE.value());
        chordProgressEntity.setCreated(LocalDateTime.now());
        chordProgressEntity.setCreatedBy(request.getUserId());

        chordProgressionDAO.persist(chordProgressEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, chordProgressionMapper.entityToDto(chordProgressEntity));
    }

    @Override
    public PayloadResponse<String> delete(EntityRequest<Long> request) {
        chordProgressionValidator.validateExistsChordProgressionRequest(request);
        chordProgressionDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, "successfully deleted record.");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ChordProgressionResponse> update(EntityRequest<ChordProgressionUpdateRequest> request) {
        chordProgressionValidator.validateUpdateChordProgressionRequest(request);

        var chordProgressionEntity = chordProgressionDAO.findByPK(request.getEntity().getId());
        chordProgressionMapper.updateEntity(request.getEntity(), chordProgressionEntity);

        chordProgressionEntity.setModified(LocalDateTime.now());
        chordProgressionEntity.setModifiedBy(request.getUserId());
        chordProgressionDAO.merge(chordProgressionEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, chordProgressionMapper.entityToDto(chordProgressionEntity));
    }

    @Override
    public PagedPayloadResponse<ChordProgressionResponse> find(final FilterRequest request) {
        PagedData<ChordProgressionEntity> chordProgressionEntities = chordProgressionDAO.findAll(request.getFilter());
        PagedData<ChordProgressionResponse> chordProgressions = new PagedData<>();
        chordProgressions.setRecords(chordProgressionMapper.entitiesToDtos(chordProgressionEntities.getRecords()));
        PagedDataMetadataMapper.remapMetadata(chordProgressionEntities, chordProgressions);
        chordProgressions.getRecords().forEach(chord -> chord.setSongNames(chordProgressionDAO.songsByChordProgression(chord.getId())));
        return new PagedPayloadResponse<>(request, ResponseCode.OK, chordProgressions);
    }

}
