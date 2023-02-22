package ba.com.zira.sdr.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.MultiSearchService;
import ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse;
import ba.com.zira.sdr.api.model.wiki.WikiResponse;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.dao.MultiSearchDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MultiSearchServiceImpl implements MultiSearchService {
    MultiSearchDAO multiSearchDAO;
    LookupService lookupService;

    @Override
    public ListPayloadResponse<MultiSearchResponse> find(EntityRequest<String> request) throws ApiException {

        List<MultiSearchResponse> multiSearchList = multiSearchDAO.getAllMultiSearches(request.getEntity());
        lookupService.lookupMultiSearchCoverImage(multiSearchList, MultiSearchResponse::getId, MultiSearchResponse::getType,
                MultiSearchResponse::setImageUrl, MultiSearchResponse::getImageUrl);
        return new ListPayloadResponse<>(request, ResponseCode.OK, multiSearchList);
    }

    @Override
    public ListPayloadResponse<WikiResponse> findWiki(EmptyRequest req) throws ApiException {

        List<WikiResponse> wikiList = multiSearchDAO.getWiki();
        return new ListPayloadResponse<>(req, ResponseCode.OK, wikiList);
    }

    @Override
    public ListPayloadResponse<MultiSearchResponse> getAll(EmptyRequest req) throws ApiException {

        List<MultiSearchResponse> multiSearchList = multiSearchDAO.getAllMultiSearches();
        return new ListPayloadResponse<>(req, ResponseCode.OK, multiSearchList);
    }

    @Override
    public ListPayloadResponse<MultiSearchResponse> getRandom(EmptyRequest req) throws ApiException {

        List<MultiSearchResponse> multiSearchList = multiSearchDAO.getRandomMultiSearches();
        return new ListPayloadResponse<>(req, ResponseCode.OK, multiSearchList);
    }

}
