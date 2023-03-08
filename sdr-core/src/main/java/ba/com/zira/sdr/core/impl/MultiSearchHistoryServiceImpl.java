package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.exception.ApiRuntimeException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.MultiSearchHistoryService;
import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryDataStructure;
import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryResponse;
import ba.com.zira.sdr.core.mapper.MultiSearchHistoryMapper;
import ba.com.zira.sdr.dao.MultiSearchDAO;
import ba.com.zira.sdr.dao.MultiSearchHistoryDAO;
import ba.com.zira.sdr.dao.model.MultiSearchHistoryEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MultiSearchHistoryServiceImpl implements MultiSearchHistoryService {

    MultiSearchDAO multiSearchDAO;
    MultiSearchHistoryDAO multiSearchHistoryDAO;
    MultiSearchHistoryMapper multiSearchHistoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> create(EmptyRequest req) throws ApiException {
        var mapper = new N2bObjectMapper();
        var multiSearchHistory = new MultiSearchHistoryEntity();
        var multiSearchDataStructure = new MultiSearchHistoryDataStructure();
        multiSearchHistory.setId(UUID.randomUUID().toString());
        multiSearchHistory.setRefreshTime(LocalDateTime.now());
        multiSearchHistory.setRowsBefore((long) multiSearchDAO.countAll());
        multiSearchDAO.deleteTable();
        multiSearchDAO.createTableAndFillWithData();
        multiSearchHistory.setRowsAfter((long) multiSearchDAO.countAll());
        var countMultiSearches = multiSearchDAO.getWiki();

        countMultiSearches.forEach(res -> {
            if (res.getType().equals("SONG")) {
                multiSearchDataStructure.setSongs(res.getCountType());
            } else if (res.getType().equals("ALBUM")) {
                multiSearchDataStructure.setAlbums(res.getCountType());
            } else {
                multiSearchDataStructure.setPersons(res.getCountType());
            }
        });
        try {
            multiSearchHistory.setDataStructure(mapper.writeValueAsString(multiSearchDataStructure));
        } catch (Exception e) {
            throw ApiRuntimeException.createFrom(e);
        }
        multiSearchHistoryDAO.persist(multiSearchHistory);
        return new PayloadResponse<>(req, ResponseCode.OK, "created successfully");
    }

    @Override
    public PayloadResponse<MultiSearchHistoryResponse> getLast(EmptyRequest request) throws ApiException {
        var mapper = new N2bObjectMapper();

        MultiSearchHistoryEntity last = multiSearchHistoryDAO.getLast();
        var lastMultiSearchResponse = multiSearchHistoryMapper.entityToDto(last);
        try {
            lastMultiSearchResponse.setDataStructure(mapper.readValue(last.getDataStructure(), MultiSearchHistoryDataStructure.class));
        } catch (Exception e) {
            throw ApiRuntimeException.createFrom(e);
        }

        return new PayloadResponse<>(request, ResponseCode.OK, lastMultiSearchResponse);
    }

    @Override
    public ListPayloadResponse<MultiSearchHistoryResponse> getAllOrderedByRefreshTime(EmptyRequest request) throws ApiException {
        var mapper = new N2bObjectMapper();

        List<MultiSearchHistoryEntity> history = multiSearchHistoryDAO.getAllOrderedByRefreshTime();
        List<MultiSearchHistoryResponse> multiSearches = new ArrayList<>();
        for (var ms : history) {
            try {
                multiSearches.add(new MultiSearchHistoryResponse(ms.getId(), ms.getRefreshTime(), ms.getRowsBefore(), ms.getRowsAfter(),
                        mapper.readValue(ms.getDataStructure(), MultiSearchHistoryDataStructure.class)));
            } catch (Exception e) {
                throw ApiRuntimeException.createFrom(e);
            }
        }

        return new ListPayloadResponse<>(request, ResponseCode.OK, multiSearches);
    }

}
