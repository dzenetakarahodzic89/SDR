package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.notesheet.NoteSheet;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetCreateRequest;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 *
 * @author zira
 *
 */
@Mapper(componentModel = "spring")
public interface NoteSheetMapper {

    @Mapping(source = "songId", target = "song.id")
    @Mapping(source = "instrumentId", target = "instrument.id")
    NoteSheetEntity dtoToEntity(NoteSheetCreateRequest noteSheetCreateRequest);

    @Mapping(source = "instrument.id", target = "instrumentId")
    @Mapping(source = "song.id", target = "songId")
    NoteSheet entityToDto(NoteSheetEntity noteSheetEntity);

    @Mapping(source = "songId", target = "song.id")
    @Mapping(source = "instrumentId", target = "instrument.id")
    void updateEntity(NoteSheetUpdateRequest notesheet, @MappingTarget NoteSheetEntity noteSheetEntity);

    List<NoteSheet> entitiesToDtos(List<NoteSheetEntity> noteSheetEntity);

}