package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.LanguageService;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.mapper.LanguageMapper;
import ba.com.zira.sdr.dao.LanguageDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    LanguageDAO languageDAO;
    LanguageMapper languageMapper;

    @Override
    public ListPayloadResponse<LoV> get(EmptyRequest req) throws ApiException {
        var languageEntities = languageDAO.findAll();
        return new ListPayloadResponse<>(req, ResponseCode.OK, languageMapper.entitiesToDtos(languageEntities));
    }
}
