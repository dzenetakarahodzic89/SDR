package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.PlaylistService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistSearchRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.PlaylistMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.PlaylistRequestValidation;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.dao.model.PlaylistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    PlaylistDAO playlistDAO;
    PlaylistMapper playlistMapper;
    PlaylistRequestValidation playlistRequestValidation;
    LookupService lookupService;

    @Override
    public PagedPayloadResponse<Playlist> find(final FilterRequest request) {
        PagedData<PlaylistEntity> playlistEntities = playlistDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, playlistEntities, playlistMapper::entitiesToDtos);
    }

    @Override
    public PagedPayloadResponse<Playlist> searchByNameSongGenre(final EntityRequest<PlaylistSearchRequest> request) {

        PagedData<PlaylistEntity> playlistEntities = new PagedData<>();
        var data = playlistDAO.findPlaylistsByNameAndGenre(request.getEntity().getName(), request.getEntity().getGenreId());
        if (request.getEntity().getSongId() != null) {
            data = data.stream().filter(p -> p.getSongPlaylists().stream().map(sp -> sp.getSong().getId()).collect(Collectors.toList())
                    .contains(request.getEntity().getSongId())).collect(Collectors.toList());
        }

        if (request.getEntity().getSortBy() != null) {
            switch (request.getEntity().getSortBy()) {
            case "LastEdit":
                data.sort((y, x) -> {
                    if (x.getModified() != null) {
                        if (y.getModified() != null) {
                            return x.getModified().compareTo(y.getModified());
                        } else {
                            return x.getModified().compareTo(y.getCreated());
                        }
                    } else {
                        if (y.getModified() != null) {
                            return x.getCreated().compareTo(y.getModified());
                        } else {
                            return x.getCreated().compareTo(y.getCreated());
                        }
                    }
                });
                break;
            case "Alphabetical":
                data.sort((x, y) -> x.getName().compareTo(y.getName()));
                break;
            case "NoOfSongs":
                data.sort((y, x) -> ((Integer) x.getSongPlaylists().size()).compareTo(y.getSongPlaylists().size()));
                break;
            }
        }

        playlistEntities.setRecords(data);
        PagedData<Playlist> response = new PagedData<>();
        response.setRecords(playlistMapper.entitiesToDtos(playlistEntities.getRecords()));
        PagedDataMetadataMapper.remapMetadata(playlistEntities, response);
        lookupService.lookupCoverImage(response.getRecords(), Playlist::getId, ObjectType.SONG.getValue(), Playlist::setImageUrl,
                Playlist::getImageUrl);
        return new PagedPayloadResponse<>(request, ResponseCode.OK, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Playlist> create(final EntityRequest<PlaylistCreateRequest> request) {
        var playlistEntity = playlistMapper.dtoToEntity(request.getEntity());
        playlistEntity.setStatus(Status.ACTIVE.value());
        playlistEntity.setCreated(LocalDateTime.now());
        playlistEntity.setCreatedBy(request.getUserId());

        playlistDAO.persist(playlistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, playlistMapper.entityToDto(playlistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Playlist> update(final EntityRequest<PlaylistUpdateRequest> request) {
        playlistRequestValidation.validateUpdatePlaylistRequest(request);

        var playlistEntity = playlistDAO.findByPK(request.getEntity().getId());
        playlistMapper.updateEntity(request.getEntity(), playlistEntity);

        playlistEntity.setModified(LocalDateTime.now());
        playlistEntity.setModifiedBy(request.getUserId());
        playlistDAO.merge(playlistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, playlistMapper.entityToDto(playlistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Playlist> delete(final EntityRequest<Long> request) {
        playlistRequestValidation.validateExistsPlaylistRequest(request);

        var playlistEntity = playlistDAO.findByPK(request.getEntity());
        playlistEntity.setStatus(Status.ACTIVE.value());
        playlistEntity.setModified(LocalDateTime.now());
        playlistEntity.setModifiedBy(request.getUser().getUserId());
        playlistDAO.merge(playlistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, playlistMapper.entityToDto(playlistEntity));
    }

}
