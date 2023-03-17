package ba.com.zira.sdr.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.PlaylistGAService;
import ba.com.zira.sdr.api.playlistga.PlaylistRequestGA;
import ba.com.zira.sdr.api.playlistga.PlaylistResponseGA;
import ba.com.zira.sdr.dao.SongScoreDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlaylistGAServiceImpl implements PlaylistGAService {

    SongScoreDAO songScoreDAO;

    @Override
    public PagedPayloadResponse<PlaylistResponseGA> generatePlaylist(final EntityRequest<PlaylistRequestGA> request) throws ApiException {
        // TODO: Genetic Algorithm implementation!

        PagedData<PlaylistResponseGA> pagedData = new PagedData<>();
        pagedData.setRecords(List.of(new PlaylistResponseGA()));
        return new PagedPayloadResponse<>(request, ResponseCode.OK, pagedData);
    }

}
