package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
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
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.LyricDAO;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.LyricEntity;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    SongDAO songDAO;
    LyricDAO lyricDAO;
    ChordProgressionDAO chordProgressionDAO;
    NoteSheetDAO noteSheetDAO;
    SongMapper songMapper;
    SongRequestValidation songRequestValidation;
    SongInstrumentDAO songInstrumentDAO;
    ArtistDAO artistDAO;
    GenreDAO genreDAO;
    LookupService lookupService;
    InstrumentDAO instrumentDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Song> create(final EntityRequest<SongCreateRequest> request) {
        var songCreateRequest = request.getEntity();
        var songEntity = songMapper.dtoToEntity(songCreateRequest);

        if (songCreateRequest.getChordProgressionId() != null) {
            songEntity.setChordProgression(chordProgressionDAO.findByPK(songCreateRequest.getChordProgressionId()));
        } else {
            songEntity.setChordProgression(null);
        }

        if (songCreateRequest.getRemixId() != null) {
            songEntity.setRemix(songDAO.findByPK(songCreateRequest.getRemixId()));
        } else {
            songEntity.setRemix(null);
        }

        if (songCreateRequest.getCoverId() != null) {
            songEntity.setCover(songDAO.findByPK(songCreateRequest.getCoverId()));
        } else {
            songEntity.setCover(null);
        }

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

    private void determineSubgenre(final Long genreId, final SongSingleResponse song) {
        GenreEntity genreEntity = genreDAO.findByPK(genreId);

        if (genreEntity.getMainGenre() == null) {
            song.setSubgenreId(null);
        } else {
            Long mainGenreId = genreEntity.getMainGenre().getId();
            String mainGenreName = genreEntity.getMainGenre().getName();

            song.setSubgenreId(genreId);
            song.setGenreId(mainGenreId);
            song.setGenre(mainGenreName);
        }
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
        song.setInstruments(instrumentDAO.getInstrumentsForSong(request.getEntity()));
        song.setSongInstruments(songInstrumentDAO.getAllBySongId(request.getEntity()));
        determineSubgenre(song.getGenreId(), song);
        return new PayloadResponse<>(request, ResponseCode.OK, song);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Song> update(final EntityRequest<SongUpdateRequest> request) {
        songRequestValidation.validateUpdateSongRequest(request);
        var songUpdateRequest = request.getEntity();

        var songEntity = songDAO.findByPK(request.getEntity().getId());
        songMapper.updateEntity(request.getEntity(), songEntity);

        if (songUpdateRequest.getChordProgressionId() != null) {
            songEntity.setChordProgression(chordProgressionDAO.findByPK(songUpdateRequest.getChordProgressionId()));
        } else {
            songEntity.setChordProgression(null);
        }

        if (songUpdateRequest.getRemixId() != null) {
            songEntity.setRemix(songDAO.findByPK(songUpdateRequest.getRemixId()));
        } else {
            songEntity.setRemix(null);
        }

        if (songUpdateRequest.getCoverId() != null) {
            songEntity.setCover(songDAO.findByPK(songUpdateRequest.getCoverId()));
        } else {
            songEntity.setCover(null);
        }

        songEntity.setGenre(genreDAO.findByPK(songUpdateRequest.getGenreId()));

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
                request.getEntity().getRemixId(), request.getEntity().getCoverId(), request.getEntity().getArtistIds(),
                request.getEntity().getAlbumIds(), request.getEntity().getGenreIds(),
                request.getEntity().getPage() != null ? request.getEntity().getPage() : 1,
                request.getEntity().getPageSize() != null ? request.getEntity().getPageSize() : 10);

        lookupService.lookupCoverImage(songs, SongSearchResponse::getId, ObjectType.SONG.getValue(), SongSearchResponse::setImageUrl,
                SongSearchResponse::getImageUrl);

        return new ListPayloadResponse<>(request, ResponseCode.OK, songs);
    }

    /**
     * Gets the song titles artist names.
     *
     * @param request
     *            the request
     * @return the song titles artist names
     * @throws ApiException
     *             the api exception
     */
    @Override
    public ListPayloadResponse<LoV> getSongTitlesArtistNames(final EmptyRequest request) throws ApiException {
        var songTitlesArtistNames = songDAO.getSongTitlesArtistNames();
        return new ListPayloadResponse<>(request, ResponseCode.OK, songTitlesArtistNames);
    }

}
