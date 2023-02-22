package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegration;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.model.AudioDBIntegrationEntity;

@Mapper(componentModel = "spring")
public interface AudioDBIntegrationMapper {
    // @Mapping(source = "name", target = "name")
    AudioDBIntegrationEntity dtoToEntity(AudioDBIntegrationCreateRequest audioDBIntegration);

    // @Mapping(source = "name", target = "name")
    AudioDBIntegration entityToDto(AudioDBIntegrationEntity audioDBIntegrationEntity);

    /*
     * @Mapping(source = "documentName", target = "docname") void
     * updateEntity(AudioDBIntegrationUpdate audioDBIntegration, @MappingTarget
     * AudioDBIntegrationEntity audioDBIntegration)
     */
    // @Mapping(source = "name", target = "name")
    void updateEntity(AudioDBIntegrationUpdateRequest audioDBIntegrationUpdateRequest,
            @MappingTarget AudioDBIntegrationEntity audioDBIntegrationEntity);

    List<AudioDBIntegration> entitiesToDtos(List<AudioDBIntegrationEntity> audioDBIntegrationlEntity);

}
