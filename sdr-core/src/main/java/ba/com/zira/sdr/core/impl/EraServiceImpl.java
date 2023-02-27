package ba.com.zira.sdr.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.EraService;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.EraDAO;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class EraServiceImpl implements EraService{
	
	EraDAO eraDAO;
	@Override
	public ListPayloadResponse<LoV> getEraLoVs(EmptyRequest req) throws ApiException {
		// TODO Auto-generated method stub
        List<LoV> eras = eraDAO.getAllErasLoV();
        return new ListPayloadResponse<>(req, ResponseCode.OK, eras);
	}

}
