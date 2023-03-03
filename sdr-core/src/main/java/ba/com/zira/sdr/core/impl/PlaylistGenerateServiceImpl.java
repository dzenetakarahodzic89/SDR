package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.FilterExpression;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.PlaylistGenerateService;
import ba.com.zira.sdr.api.model.generateplaylist.GeneratedPlaylistSong;
import ba.com.zira.sdr.api.model.generateplaylist.GeneratedPlaylistSongDbResponse;
import ba.com.zira.sdr.api.model.generateplaylist.PlaylistGenerateRequest;
import ba.com.zira.sdr.api.model.generateplaylist.SavePlaylistRequest;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.core.mapper.PlaylistMapper;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.core.utils.PlayTimeHelper;
import ba.com.zira.sdr.core.validation.SongRequestValidation;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongPlaylistDAO;
import ba.com.zira.sdr.dao.model.PlaylistEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlaylistGenerateServiceImpl implements PlaylistGenerateService {
    SongDAO songDAO;
    SongPlaylistDAO songPlaylistDAO;
    SongMapper songMapper;
    PlaylistDAO playlistDAO;
    PlaylistMapper playlistMapper;
    SongRequestValidation songRequestValidation;
    GeneratePlaylistServiceHelper generatePlaylistServiceHelper;

    @Override
    public PagedPayloadResponse<GeneratedPlaylistSong> generatePlaylist(FilterRequest request) throws ApiException {
        List<GeneratedPlaylistSongDbResponse> generatedPlaylist = songDAO
                .generatePlaylist(this.extractPlaylistGenerateRequestFromFilter(request.getFilter()));
        PagedData<GeneratedPlaylistSongDbResponse> pagedData = new PagedData<>();
        pagedData.setRecords(generatedPlaylist);
        return new PagedPayloadResponse<>(request, ResponseCode.OK, pagedData,
                generatePlaylistServiceHelper::complexDbObjectsToGeneratedPlaylistSongs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Playlist> saveGeneratedPlaylist(final EntityRequest<SavePlaylistRequest> entityRequest) throws ApiException {
        songRequestValidation.validateExistsSongsInRequest(entityRequest);

        List<SongEntity> songEntities = songDAO.findSongsByIdArray(entityRequest.getEntity().getSongIds());

        PlaylistEntity playlistEntity = new PlaylistEntity();

        playlistEntity.setId(null);
        playlistEntity.setName(entityRequest.getEntity().getName());
        playlistEntity.setCreated(LocalDateTime.now());
        playlistEntity.setCreatedBy(entityRequest.getUserId());
        playlistEntity.setUserCode(entityRequest.getUserId());
        playlistEntity.setTotalPlaytime(
                calculateSecondsPlayTime(songEntities.stream().map((song) -> song.getPlaytime()).collect(Collectors.toList())));
        playlistEntity.setStatus(Status.ACTIVE.value());

        playlistEntity = playlistDAO.persist(playlistEntity);

        List<SongPlaylistEntity> songPlaylistEntities = new ArrayList<>();

        for (SongEntity songEntity : songEntities) {
            SongPlaylistEntity songPlaylistEntity = new SongPlaylistEntity();

            songPlaylistEntity.setCreated(LocalDateTime.now());
            songPlaylistEntity.setCreatedBy(entityRequest.getUserId());
            songPlaylistEntity.setStatus(Status.ACTIVE.value());
            songPlaylistEntity.setPlaylist(playlistEntity);
            songPlaylistEntity.setSong(songEntity);

            songPlaylistEntities.add(songPlaylistEntity);
        }

        songPlaylistDAO.persistCollection(songPlaylistEntities);

        return new PayloadResponse<>(entityRequest, ResponseCode.OK, playlistMapper.entityToDto(playlistEntity));
    }

    private Long calculateSecondsPlayTime(List<String> playTimes) {
        Long seconds = 0L;
        for (String playTime : playTimes) {
            seconds += PlayTimeHelper.playTimeToSeconds(playTime);
        }
        return seconds;
    }

    private PlaylistGenerateRequest extractPlaylistGenerateRequestFromFilter(Filter f) {
        PlaylistGenerateRequest playlistGenerateRequest = new PlaylistGenerateRequest();

        f.getFilterExpressions().forEach((FilterExpression fe) -> {
            if (fe.getAttribute().equals("genreId")) {
                playlistGenerateRequest.setGenreId(Long.parseLong(((String) fe.getExpressionValueObject())));
            } else if (fe.getAttribute().equals("includeRemixes")) {
                playlistGenerateRequest.setIncludeRemixes(Boolean.parseBoolean((String) fe.getExpressionValueObject()));
            } else if (fe.getAttribute().equals("includeCovers")) {
                playlistGenerateRequest.setIncludeCovers(Boolean.parseBoolean((String) fe.getExpressionValueObject()));
            } else if (fe.getAttribute().equals("amountOfSongs")) {
                playlistGenerateRequest.setAmountOfSongs(Long.parseLong((String) fe.getExpressionValueObject()));
            }
        });

        return playlistGenerateRequest;
    }
}
