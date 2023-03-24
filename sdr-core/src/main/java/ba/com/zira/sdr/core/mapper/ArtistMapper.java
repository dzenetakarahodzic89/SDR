package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistSingleResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.PersonEntity;

@Component
@Mapper(componentModel = "spring")

public interface ArtistMapper {

    ArtistResponse entityToDto(ArtistEntity artistEntity);

    ArtistEntity dtoToEntity(ArtistCreateRequest artistRequest);

    ArtistSingleResponse entityToSingleArtistDto(ArtistEntity artistEntity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "information", source = "information")
    @Mapping(target = "outlineText", source = "outlineText")
    ArtistEntity personToArtist(PersonEntity personEntity);

    void updateEntity(ArtistUpdateRequest artistRequest, @MappingTarget ArtistEntity artistEntity);

    List<ArtistResponse> entitiesToDtos(List<ArtistEntity> artistEntity);

}
