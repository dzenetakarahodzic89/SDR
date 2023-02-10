package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.SampleModel;
import ba.com.zira.sdr.api.model.SampleModelCreateRequest;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;
import ba.com.zira.sdr.dao.model.SampleModelEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 * 
 * @author zira
 *
 */
@Mapper(componentModel = "spring")
public interface SampleModelMapper {

    @Mapping(source = "documentName", target = "docname")
    SampleModelEntity dtoToEntity(SampleModelCreateRequest sampleModel);
    
    @Mapping(source = "docname", target = "documentName")
    SampleModel entityToDto(SampleModelEntity sampleModelEntity);

    @Mapping(source = "documentName", target = "docname")
    void updateEntity(SampleModelUpdateRequest sampleModel, @MappingTarget SampleModelEntity sampleModelEntity);

    List<SampleModel> entitiesToDtos(List<SampleModelEntity> sampleModelEntity);

}