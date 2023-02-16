package ba.com.zira.sdr.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ChordProgressionService;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChordProgressionServiceImpl implements ChordProgressionService {

    ChordProgressionDAO choreProgressionDAO;

    @Override
    public ListPayloadResponse<ChordProgressionResponse> getAll(EmptyRequest req) throws ApiException {
        List<ChordProgressionResponse> chordProgressionList = choreProgressionDAO.getAllChordProgressions();
        return new ListPayloadResponse<>(req, ResponseCode.OK, chordProgressionList);
    }

}
