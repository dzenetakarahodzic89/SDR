package ba.com.zira.sdr.core.impl;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongService;
import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.api.model.song.SongCreateRequest;
import ba.com.zira.sdr.api.model.song.SongUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.core.validation.SongRequestValidation;
import ba.com.zira.sdr.dao.LyricDAO;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.LyricEntity;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    SongDAO songDAO;
    LyricDAO lyricDAO;
    NoteSheetDAO noteSheetDAO;
    SongMapper songMapper;
    SongRequestValidation songRequestValidation;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Song> create(final EntityRequest<SongCreateRequest> request) {
        var songCreateRequest = request.getEntity();
        var songEntity = songMapper.dtoToEntity(songCreateRequest);
        songEntity.setStatus(Status.ACTIVE.value());
        songEntity.setCreated(LocalDateTime.now());
        songEntity.setCreatedBy(request.getUserId());

        List<LyricEntity> lyrics = lyricDAO.getLyricsByIds(songCreateRequest.getLyricIds());
        songEntity.setLyrics(lyrics);

        List<NoteSheetEntity> noteSheets = noteSheetDAO.getNoteSheetsByIds(songCreateRequest.getNoteSheetIds());
        songEntity.setNoteSheets(noteSheets);

        songDAO.persist(songEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, songMapper.entityToDto(songEntity));
    }

    @Override
    public PagedPayloadResponse<Song> retrieveAll(final FilterRequest request) {
        PagedData<SongEntity> songEntities = songDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songEntities, songMapper::entitiesToDtos);
    }

    @Override
    public PayloadResponse<Song> retrieveById(final EntityRequest<Long> request) {
        songRequestValidation.validateExistsSongRequest(request);

        var songEntity = songDAO.findByPK(request.getEntity());

        return new PayloadResponse<>(request, ResponseCode.OK, songMapper.entityToDto(songEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Song> update(final EntityRequest<SongUpdateRequest> request) {
        songRequestValidation.validateUpdateSongRequest(request);
        var songUpdateRequest = request.getEntity();

        var songEntity = songDAO.findByPK(request.getEntity().getId());
        songMapper.updateEntity(request.getEntity(), songEntity);

        songEntity.setModified(LocalDateTime.now());
        songEntity.setModifiedBy(request.getUserId());

        List<LyricEntity> lyrics = lyricDAO.getLyricsByIds(songUpdateRequest.getLyricIds());
        songEntity.setLyrics(lyrics);

        List<NoteSheetEntity> noteSheets = noteSheetDAO.getNoteSheetsByIds(songUpdateRequest.getNoteSheetIds());
        songEntity.setNoteSheets(noteSheets);

        songDAO.merge(songEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songMapper.entityToDto(songEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        songRequestValidation.validateExistsSongRequest(request);

        var songEntity = songDAO.findByPK(request.getEntity());

        songDAO.remove(songEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, "Song has been successfully deleted!");
    }

}
