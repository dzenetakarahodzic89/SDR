package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.NoteSheetService;
import ba.com.zira.sdr.api.model.notesheet.NoteSheet;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetCreateRequest;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.core.mapper.NoteSheetMapper;
import ba.com.zira.sdr.core.validation.NoteSheetRequestValidation;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteSheetServiceImpl implements NoteSheetService {

    NoteSheetDAO notesheetDAO;
    NoteSheetMapper notesheetMapper;
    NoteSheetRequestValidation notesheetRequestValidation;

    @Override
    public PagedPayloadResponse<NoteSheet> find(final FilterRequest request) {
        PagedData<NoteSheetEntity> noteSheetEntity = notesheetDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, noteSheetEntity, notesheetMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<NoteSheet> create(final EntityRequest<NoteSheetCreateRequest> request) {
        notesheetRequestValidation.validateCreateNoteSheetRequest(request);
        var noteSheetEntity = notesheetMapper.dtoToEntity(request.getEntity());
        noteSheetEntity.setStatus(Status.ACTIVE.value());
        noteSheetEntity.setCreated(LocalDateTime.now());
        noteSheetEntity.setCreatedBy(request.getUserId());
        noteSheetEntity.setModified(LocalDateTime.now());
        noteSheetEntity.setModifiedBy(request.getUserId());

        notesheetDAO.persist(noteSheetEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, notesheetMapper.entityToDto(noteSheetEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<NoteSheet> update(final EntityRequest<NoteSheetUpdateRequest> request) {
        notesheetRequestValidation.validateUpdateNoteSheetRequest(request);

        var noteSheetEntity = notesheetDAO.findByPK(request.getEntity().getId());
        notesheetMapper.updateEntity(request.getEntity(), noteSheetEntity);

        noteSheetEntity.setModified(LocalDateTime.now());
        noteSheetEntity.setModifiedBy(request.getUserId());
        notesheetDAO.merge(noteSheetEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, notesheetMapper.entityToDto(noteSheetEntity));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        notesheetRequestValidation.validateExistsNotesheetRequest(request);
        notesheetDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK,
                String.format("Note Sheet with id %s is successfully deleted!", request.getEntity()));
    }
}
