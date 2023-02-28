package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.exception.ApiRuntimeException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.MultiSearchHistoryService;
import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryDataStructure;
import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryResponse;
import ba.com.zira.sdr.dao.MultiSearchDAO;
import ba.com.zira.sdr.dao.MultiSearchHistoryDAO;
import ba.com.zira.sdr.dao.model.MultiSearchHistoryEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MultiSearchHistoryServiceImpl implements MultiSearchHistoryService {

    MultiSearchDAO multiSearchDAO;
    MultiSearchHistoryDAO multiSearchHistoryDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> create(EmptyRequest req) throws ApiException {
        var mapper = new N2bObjectMapper();
        var multiSearchHistory = new MultiSearchHistoryEntity();
        multiSearchHistory.setId(UUID.randomUUID().toString());
        multiSearchHistory.setRefreshTime(LocalDateTime.now());
        multiSearchHistory.setRowsBefore(multiSearchDAO.countFields());
        multiSearchDAO.deleteTable();
        multiSearchDAO.createTableAndFillWithData();
        multiSearchHistory.setRowsAfter(multiSearchDAO.countFields());
        var dataStructure = multiSearchDAO.createDataStructure();
        try {
            multiSearchHistory.setDataStructure(mapper.writeValueAsString(dataStructure));
        } catch (Exception e) {
            throw ApiRuntimeException.createFrom(e);
        }
        multiSearchHistory = multiSearchHistoryDAO.persist(multiSearchHistory);
        return new PayloadResponse<>(req, ResponseCode.OK, "created successfully");
    }

    @Override
    public PayloadResponse<MultiSearchHistoryResponse> getLast(EmptyRequest request) throws ApiException {
        var mapper = new N2bObjectMapper();

        MultiSearchHistoryEntity last = multiSearchHistoryDAO.getLast();
        MultiSearchHistoryResponse lastMultiSearch = new MultiSearchHistoryResponse();

        lastMultiSearch.setId(last.getId());
        LocalDateTime date = last.getRefreshTime();
        lastMultiSearch.setRefreshTime(date);
        lastMultiSearch.setRowsBefore(last.getRowsBefore());
        lastMultiSearch.setRowsAfter(last.getRowsAfter());
        try {
            lastMultiSearch.setDataStructure(mapper.readValue(last.getDataStructure(), MultiSearchHistoryDataStructure.class));
        } catch (Exception e) {
            throw ApiRuntimeException.createFrom(e);
        }

        return new PayloadResponse<>(request, ResponseCode.OK, lastMultiSearch);
    }

}
