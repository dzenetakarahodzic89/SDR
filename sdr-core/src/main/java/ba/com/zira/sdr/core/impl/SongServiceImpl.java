package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.api.model.song.SongCreateRequest;
import ba.com.zira.sdr.api.model.song.SongSearchRequest;
import ba.com.zira.sdr.api.model.song.SongSearchResponse;
import ba.com.zira.sdr.api.model.song.SongSingleResponse;
import ba.com.zira.sdr.api.model.song.SongUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.validation.SongRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.dao.LyricDAO;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.LyricEntity;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    SongDAO songDAO;
    LyricDAO lyricDAO;
    NoteSheetDAO noteSheetDAO;
    SongMapper songMapper;
    SongRequestValidation songRequestValidation;
    ArtistDAO artistDAO;
    GenreDAO genreDAO;
    LookupService lookupService;

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
        PagedData<Song> songPD = new PagedData<>();
        List<Song> songs = songMapper.entitiesToDtos(songEntities.getRecords());
        lookupService.lookupCoverImage(songs, Song::getId, ObjectType.SONG.getValue(), Song::setImageUrl, Song::getImageUrl);
        lookupService.lookupAudio(songs, Song::getId, Song::setAudioUrl);
        songPD.setRecords(songs);
        PagedDataMetadataMapper.remapMetadata(songEntities, songPD);
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songPD);
    }

    @Override
    public PayloadResponse<SongSingleResponse> retrieveById(final EntityRequest<Long> request) {
        songRequestValidation.validateExistsSongRequest(request);

        SongSingleResponse song = songDAO.getById(request.getEntity());
        lookupService.lookupCoverImage(Arrays.asList(song), SongSingleResponse::getId, ObjectType.SONG.getValue(),
                SongSingleResponse::setImageUrl, SongSingleResponse::getImageUrl);
        lookupService.lookupAudio(Arrays.asList(song), SongSingleResponse::getId, SongSingleResponse::setAudioUrl);
        song.setPlaylistCount(songDAO.countAllPlaylistsWhereSongExists(request.getEntity()).intValue());
        song.setArtists(artistDAO.getBySongId(request.getEntity()));
        song.setSubgenres(genreDAO.subGenresByMainGenre(song.getGenreId()));
        return new PayloadResponse<>(request, ResponseCode.OK, song);
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

    @Override
    public ListPayloadResponse<LoV> retrieveNotInAlbum(final EntityRequest<Long> request) throws ApiException {
        var songs = songDAO.getSongsNotInAlbum(request.getEntity());

        return new ListPayloadResponse<>(request, ResponseCode.OK, songs);
    }

    @Override
    public ListPayloadResponse<SongSearchResponse> find(final EntityRequest<SongSearchRequest> request) {

        List<SongSearchResponse> songs = songDAO.find(request.getEntity().getName(), request.getEntity().getSortBy(),
                request.getEntity().getRemixId(), request.getEntity().getCoverId(), request.getEntity().getAlbumIds(),
                request.getEntity().getGenreIds(), request.getEntity().getArtistIds(), request.getEntity().getPage(),
                request.getEntity().getPageSize());

        lookupService.lookupCoverImage(songs, SongSearchResponse::getId, ObjectType.SONG.getValue(), SongSearchResponse::setImageUrl,
                SongSearchResponse::getImageUrl);

        return new ListPayloadResponse<>(request, ResponseCode.OK, songs);
    }

}
