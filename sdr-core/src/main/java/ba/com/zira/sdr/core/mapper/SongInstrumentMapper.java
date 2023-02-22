package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
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

    SongInstrumentEntity dtoToEntity(SongInstrumentCreateRequest songInstrumentCreateRequest);

    SongInstrument entityToDto(SongInstrumentEntity songInstrumentEntity);

    void updateEntity(SongInstrumentUpdateRequest songInstrument, @MappingTarget SongInstrumentEntity songInstrumentEntity);

    List<SongInstrument> entitiesToDtos(List<SongInstrumentEntity> songInstrumentEntity);

}