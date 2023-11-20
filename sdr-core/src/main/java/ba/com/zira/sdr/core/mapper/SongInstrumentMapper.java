package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songinstrument.SongInstrument;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentCreateRequest;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentUpdateRequest;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 *
 * @author zira
 *
 */
@Mapper(componentModel = "spring")
public interface SongInstrumentMapper {

    @Mapping(source = "songId", target = "song.id")
    @Mapping(source = "instrumentId", target = "instrument.id")
    @Mapping(source = "personId", target = "person.id")
    SongInstrumentEntity dtoToEntity(SongInstrumentCreateRequest songInstrumentCreateRequest);

    @Mapping(source = "song.id", target = "songId")
    @Mapping(source = "song.name", target = "songName")
    @Mapping(source = "instrument.id", target = "instrumentId")
    @Mapping(source = "instrument.name", target = "instrumentName")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.name", target = "personName")
    @Mapping(source = "person.dateOfBirth", target = "personDob")
    @Mapping(source = "person.surname", target = "personSurname")
    SongInstrument entityToDto(SongInstrumentEntity songInstrumentEntity);

    @Mapping(source = "songId", target = "song.id")
    @Mapping(source = "instrumentId", target = "instrument.id")
    @Mapping(source = "personId", target = "person.id")
    void updateEntity(SongInstrumentUpdateRequest songInstrument, @MappingTarget SongInstrumentEntity songInstrumentEntity);

    List<SongInstrument> entitiesToDtos(List<SongInstrumentEntity> songInstrumentEntity);

}