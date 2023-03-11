package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

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
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.notesheet.NoteSheet;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetCreateRequest;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetSongResponse;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.api.model.notesheet.SongInstrumentSheetResponse;
import ba.com.zira.sdr.core.mapper.NoteSheetMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.NoteSheetRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteSheetServiceImpl implements NoteSheetService {

    NoteSheetDAO notesheetDAO;
    NoteSheetMapper notesheetMapper;
    NoteSheetRequestValidation notesheetRequestValidation;
    LookupService lookupService;
    CountryDAO countryDAO;
    ArtistDAO artistDAO;
    SongDAO songDAO;
    InstrumentDAO instrumentDAO;

    @Override
    public PagedPayloadResponse<NoteSheet> find(final FilterRequest request) {
        PagedData<NoteSheetEntity> noteSheetEntity = notesheetDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, noteSheetEntity, notesheetMapper::entitiesToDtos);
    }

    @Override
    public PayloadResponse<NoteSheetSongResponse> findNoteSheetForSong(final EntityRequest<Long> request) {

        NoteSheetSongResponse noteSheet = notesheetDAO.getNoteSheetById(request.getEntity());

        lookupService.lookupCoverImage(Arrays.asList(noteSheet), NoteSheetSongResponse::getSongId, ObjectType.SONG.getValue(),
                NoteSheetSongResponse::setImageUrl, NoteSheetSongResponse::getImageUrl);
        lookupService.lookupAudio(Arrays.asList(noteSheet), NoteSheetSongResponse::getSongId, NoteSheetSongResponse::setAudioUrl);

        return new PayloadResponse<>(request, ResponseCode.OK, noteSheet);
    }

    @Override
    public PayloadResponse<NoteSheetSongResponse> findNoteSheetByInstrumentAndSong(
            final EntityRequest<SongInstrumentSheetResponse> request) {
        NoteSheetEntity noteSheetEntity = notesheetDAO.getNoteSheetByInstrumentAndSong(request.getEntity().getSongId(),
                request.getEntity().getInstrumentId());

        String sheetContentResponse = noteSheetEntity.getSheetContent();

        NoteSheetSongResponse noteSheetSongResponse = new NoteSheetSongResponse(noteSheetEntity.getId(),
                noteSheetEntity.getInstrument().getId(), noteSheetEntity.getSong().getId(), sheetContentResponse

        );

        noteSheetSongResponse.setArtists(artistDAO.getArt(noteSheetEntity.getId()));

        lookupService.lookupCoverImage(Arrays.asList(noteSheetSongResponse), NoteSheetSongResponse::getSongId, ObjectType.SONG.getValue(),
                NoteSheetSongResponse::setImageUrl, NoteSheetSongResponse::getImageUrl);
        lookupService.lookupAudio(Arrays.asList(noteSheetSongResponse), NoteSheetSongResponse::getSongId,
                NoteSheetSongResponse::setAudioUrl);
        lookupService.lookupSongNames(Arrays.asList(noteSheetSongResponse), NoteSheetSongResponse::getSongId,
                NoteSheetSongResponse::setSongName);

        lookupService.lookupSongDateOfRelease(Arrays.asList(noteSheetSongResponse), NoteSheetSongResponse::getSongId,
                NoteSheetSongResponse::setDateOfRelease);

        lookupService.lookupInstrumentNames(Arrays.asList(noteSheetSongResponse), NoteSheetSongResponse::getInstrumentId,
                NoteSheetSongResponse::setInstrumentName);

        return new PayloadResponse<>(request, ResponseCode.OK, noteSheetSongResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<NoteSheet> create(final EntityRequest<NoteSheetCreateRequest> request) {
        NoteSheetCreateRequest createRequest = request.getEntity();
        String sheetContent = createRequest.getSheetContent();

        NoteSheetEntity noteSheetEntity = notesheetMapper.dtoToEntity(createRequest);
        noteSheetEntity.setStatus(Status.ACTIVE.value());
        noteSheetEntity.setCreated(LocalDateTime.now());
        noteSheetEntity.setCreatedBy(request.getUserId());
        noteSheetEntity.setModified(LocalDateTime.now());
        noteSheetEntity.setModifiedBy(request.getUserId());
        noteSheetEntity.setSheetContent(sheetContent);

        notesheetDAO.persist(noteSheetEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, notesheetMapper.entityToDto(noteSheetEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<NoteSheet> update(final EntityRequest<NoteSheetUpdateRequest> request) {
        notesheetRequestValidation.validateUpdateNoteSheetRequest(request);

        var NoteSheetEntity = notesheetDAO.findByPK(request.getEntity().getId());
        notesheetMapper.updateEntity(request.getEntity(), NoteSheetEntity);

        NoteSheetEntity.setModified(LocalDateTime.now());
        NoteSheetEntity.setModifiedBy(request.getUserId());
        notesheetDAO.merge(NoteSheetEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, notesheetMapper.entityToDto(NoteSheetEntity));
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
