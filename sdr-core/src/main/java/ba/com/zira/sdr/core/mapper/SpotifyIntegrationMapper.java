package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.model.SpotifyIntegrationEntity;

@Mapper(componentModel = "spring")
public interface SpotifyIntegrationMapper {

    SpotifyIntegrationEntity dtoToEntity(SpotifyIntegrationCreateRequest moritsIntegration);

    SpotifyIntegrationResponse entityToDto(SpotifyIntegrationEntity moritsIntegrationEntity);

    void updateEntity(SpotifyIntegrationUpdateRequest moritsIntegration, @MappingTarget SpotifyIntegrationEntity moritsIntegrationEntity);

    List<SpotifyIntegrationResponse> entitiesToDtos(List<SpotifyIntegrationEntity> moritsIntegrationEntity);

}
