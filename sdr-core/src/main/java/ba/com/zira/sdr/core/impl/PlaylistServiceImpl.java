package ba.com.zira.sdr.core.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
import ba.com.zira.sdr.api.PlaylistService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistOfUserResponse;
import ba.com.zira.sdr.api.model.playlist.PlaylistResponse;
import ba.com.zira.sdr.api.model.playlist.PlaylistSearchRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.PlaylistMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.PlaylistRequestValidation;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.dao.SongPlaylistDAO;
import ba.com.zira.sdr.dao.model.PlaylistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    PlaylistDAO playlistDAO;
    PlaylistMapper playlistMapper;
    PlaylistRequestValidation playlistRequestValidation;
    LookupService lookupService;
    SongPlaylistDAO songPlaylistDAO;
    private static SecureRandom random = new SecureRandom();

    @Override
    public PagedPayloadResponse<Playlist> find(final FilterRequest request) {
        PagedData<PlaylistEntity> playlistEntities = playlistDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, playlistEntities, playlistMapper::entitiesToDtos);
    }

    @Override
    public PagedPayloadResponse<Playlist> searchByNameSongGenre(final EntityRequest<PlaylistSearchRequest> request) {

        PagedData<PlaylistEntity> playlistEntities = new PagedData<>();
        var data = playlistDAO.findPlaylistsByNameAndGenre(request.getEntity().getName(), request.getEntity().getGenreId(),
                request.getEntity().getSortBy());
        if (request.getEntity().getSongId() != null) {
            data = data.stream().filter(p -> p.getSongPlaylists().stream().map(sp -> sp.getSong().getId()).collect(Collectors.toList())
                    .contains(request.getEntity().getSongId())).collect(Collectors.toList());
        }

        playlistEntities.setRecords(data);
        Map<Object, Object> randomSongs = data.stream().map(p -> {
            var songPlaylists = p.getSongPlaylists();
            return new AbstractMap.SimpleEntry<>(p.getId(), songPlaylists.get(random.nextInt(songPlaylists.size())).getSong().getId());
        }).collect(Collectors.toMap(AbstractMap.SimpleEntry<Long, Long>::getKey, AbstractMap.SimpleEntry<Long, Long>::getValue));
        PagedData<Playlist> response = new PagedData<>();
        response.setRecords(playlistMapper.entitiesToDtos(playlistEntities.getRecords()));
        PagedDataMetadataMapper.remapMetadata(playlistEntities, response);
        lookupService.lookupCoverImage(response.getRecords(), playlist -> (Long) randomSongs.get(playlist.getId()),
                ObjectType.SONG.getValue(), Playlist::setImageUrl, Playlist::getImageUrl);
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
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        playlistRequestValidation.validateExistsPlaylistRequest(request);
        songPlaylistDAO.deleteByPlaylistId(request.getEntity());
        playlistDAO.removeByPK(request.getEntity());

        return new PayloadResponse<>(request, ResponseCode.OK, "Playlist successfully deleted.");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ListPayloadResponse<PlaylistOfUserResponse> findPlaylistOfUser(EntityRequest<String> request) throws ApiException {
        Map<Long, List<PlaylistOfUserResponse>> mapSongs = playlistDAO.findPlaylistOfUser(request.getEntity());
        if (mapSongs != null && !mapSongs.isEmpty()) {
            List<PlaylistOfUserResponse> listSongs = null;
            int mapSize = mapSongs.size();
            if (mapSize > 1) {
                int randomIndex = new Random().nextInt(mapSize);
                listSongs = new ArrayList<>(mapSongs.values()).get(randomIndex);

            } else {
                listSongs = new ArrayList<>(mapSongs.values()).get(0);

            }

            if (listSongs != null) {
                lookupService.lookupAudio(listSongs, PlaylistOfUserResponse::getSongId, PlaylistOfUserResponse::setAudioUrl);
                return new ListPayloadResponse<>(request, ResponseCode.OK, listSongs);
            }
        }

        return new ListPayloadResponse<>(request, ResponseCode.OK, Collections.emptyList());
    }

    @Override
    public ListPayloadResponse<PlaylistResponse> getAll(final EntityRequest<Long> req) throws ApiException {
        List<PlaylistResponse> playlist = playlistDAO.getPlaylistInfo(req.getEntity());

        return new ListPayloadResponse<>(req, ResponseCode.OK, playlist);
    }

}
