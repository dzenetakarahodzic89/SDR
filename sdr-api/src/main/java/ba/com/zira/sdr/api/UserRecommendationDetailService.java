package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailResponse;

public interface UserRecommendationDetailService {

	public PagedPayloadResponse<UserRecommendationDetailResponse> find(final FilterRequest filterRequest)
			throws ApiException;

	public PayloadResponse<UserRecommendationDetailResponse> create(
			final EntityRequest<UserRecommendationDetailCreateRequest> entityRequest) throws ApiException;

	public PayloadResponse<UserRecommendationDetailResponse> delete(final EntityRequest<Long> entityRequest);

}
