package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.label.LabelCreateRequest;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;
import ba.com.zira.sdr.dao.model.LabelEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 *
 * @author zira
 *
 */

@Mapper(componentModel = "spring")
public interface LabelMapper {

    @Mapping(source = "labelName", target = "name")
    @Mapping(source = "founderId", target = "founder.id")
    LabelEntity dtoToEntity(LabelCreateRequest label);

    @Mapping(source = "name", target = "labelName")
    @Mapping(source = "founder.id", target = "founderId")
    LabelResponse entityToDto(LabelEntity labelEntity);

    @Mapping(source = "labelName", target = "name")
    @Mapping(source = "founderId", target = "founder.id")
    void updateEntity(LabelUpdateRequest label, @MappingTarget LabelEntity labelEntity);

    List<LabelResponse> entitiesToDtos(List<LabelEntity> labelEntity);

}