package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.dao.model.ArtistEntity;

@Component
@Mapper(componentModel = "spring")

public interface ArtistMapper {

    ArtistResponse entityToDto(ArtistEntity artistEntity);

    ArtistEntity dtoToEntity(ArtistCreateRequest artistRequest);

    void updateEntity(ArtistUpdateRequest artistRequest, @MappingTarget ArtistEntity artistEntity);

    List<ArtistResponse> entitiesToDtos(List<ArtistEntity> artistEntity);

}
