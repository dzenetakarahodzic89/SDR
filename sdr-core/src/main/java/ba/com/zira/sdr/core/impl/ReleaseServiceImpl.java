package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ReleaseService;
import ba.com.zira.sdr.api.model.release.ReleaseSearchRequest;
import ba.com.zira.sdr.api.model.release.ReleaseSearchResponse;
import ba.com.zira.sdr.core.mapper.ReleaseMapper;
import ba.com.zira.sdr.dao.ReleaseDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReleaseServiceImpl implements ReleaseService {

    ReleaseDAO releaseDAO;
    ReleaseMapper releaseMapper;

    @Override
    public PagedPayloadResponse<ReleaseSearchResponse> find(final SearchRequest<ReleaseSearchRequest> request) {

        PagedData<ReleaseSearchResponse> releases = releaseDAO.find(request);

        return new PagedPayloadResponse<>(request, ResponseCode.OK, releases);
    }

}
