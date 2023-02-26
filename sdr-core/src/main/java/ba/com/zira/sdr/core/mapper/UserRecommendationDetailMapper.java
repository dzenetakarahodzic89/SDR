package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailResponse;
import ba.com.zira.sdr.dao.model.UserRecommendationDetailEntity;

@Mapper(componentModel = "spring")
public interface UserRecommendationDetailMapper {

	@Mapping(target = "userRecommendation.id", source = "userRecommendationId")
	@Mapping(target = "song.id", source = "songId")

	UserRecommendationDetailEntity dtoToEntity(
			UserRecommendationDetailCreateRequest userRecommendationDetailCreateRequest);

	@Mapping(target = "userRecommendationId", source = "userRecommendation.id")
	@Mapping(target = "songId", source = "song.id")
	
	
	UserRecommendationDetailResponse entityToDto(UserRecommendationDetailEntity userRecommendationDetailEntity);

	List<UserRecommendationDetailResponse> entitiesToDtos(
			List<UserRecommendationDetailEntity> userRecommendationDetailEntity);
}
