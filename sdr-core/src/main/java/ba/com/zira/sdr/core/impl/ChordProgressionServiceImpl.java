package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ChordProgressionService;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.core.mapper.ChordProgressionMapper;
import ba.com.zira.sdr.core.validation.ChordProgressionValidation;
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChordProgressionServiceImpl implements ChordProgressionService {

    ChordProgressionDAO chordProgressionDAO;
    ChordProgressionMapper chordProgressionMapper;
    ChordProgressionValidation chordProgressionValidator;

    @Override
    public ListPayloadResponse<ChordProgressionResponse> getAll(EmptyRequest req) throws ApiException {
        List<ChordProgressionResponse> chordProgressionList = chordProgressionDAO.getAllChordProgressions();
        return new ListPayloadResponse<>(req, ResponseCode.OK, chordProgressionList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ChordProgressionResponse> create(EntityRequest<ChordProgressionCreateRequest> request) throws ApiException {
        var chordProgressEntity = chordProgressionMapper.dtoToEntity(request.getEntity());
        chordProgressEntity.setStatus(Status.ACTIVE.value());
        chordProgressEntity.setCreated(LocalDateTime.now());
        chordProgressEntity.setCreatedBy(request.getUserId());
        chordProgressEntity.setModified(LocalDateTime.now());
        chordProgressEntity.setModifiedBy(request.getUserId());

        chordProgressionDAO.persist(chordProgressEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, chordProgressionMapper.entityToDto(chordProgressEntity));
    }

    @Override
    public PayloadResponse<ChordProgressionResponse> delete(EntityRequest<Long> request) throws ApiException {
        chordProgressionValidator.validateExistsChordProgressionRequest(request);
        var chordDeleteEntity = chordProgressionDAO.findByPK(request.getEntity());
        chordProgressionDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, chordProgressionMapper.entityToDto(chordDeleteEntity));
    }

}
