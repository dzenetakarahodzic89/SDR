package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 *
 * @author zira
 *
 */

@Mapper(componentModel = "spring")
public interface UserRecommendationMapper {

    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    UserRecommendationEntity dtoToEntity(UserRecommendationCreateRequest userRecommendation);

    @Mapping(source = "description", target = "description")
    @Mapping(source = "name", target = "name")
    UserRecommendationResponse entityToDto(UserRecommendationEntity userRecommendationEntity);

    List<UserRecommendationResponse> entitiesToDtos(List<UserRecommendationEntity> userRecommendationEntity);

}
