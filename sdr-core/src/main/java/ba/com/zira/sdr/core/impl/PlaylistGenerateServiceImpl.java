package ba.com.zira.sdr.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.FilterExpression;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.PlaylistGenerateService;
import ba.com.zira.sdr.api.model.generateplaylist.PlaylistGenerateRequest;
import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlaylistGenerateServiceImpl implements PlaylistGenerateService {
    SongDAO songDAO;
    SongMapper songMapper;

    @Override
    public PagedPayloadResponse<Song> generatePlaylist(FilterRequest request) throws ApiException {
        List<SongEntity> filteredSongEntities = songDAO
                .generatePlaylist(this.extractPlaylistGenerateRequestFromFilter(request.getFilter()));
        PagedData<SongEntity> pagedData = new PagedData<>();
        pagedData.setRecords(filteredSongEntities);
        return new PagedPayloadResponse<>(request, ResponseCode.OK, pagedData, songMapper::entitiesToDtos);
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
