package ba.com.zira.sdr.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.core.mapper.SongArtistMapper;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongArtistServiceImpl implements SongArtistService {
    SongArtistDAO songArtistDAO;
    SongArtistMapper songArtistMapper;

    // TODO: add validation object for validating inupt requests

    @Override
    public ListPayloadResponse<SongArtistResponse> getAll(EmptyRequest request) {
        List<SongArtistEntity> songArtistEntities = songArtistDAO.findAll();
        return new ListPayloadResponse<>(request, ResponseCode.OK, songArtistMapper.entitiesToDtos(songArtistEntities));
    }

}
