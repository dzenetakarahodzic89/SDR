package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.MediaStoreService;
import ba.com.zira.sdr.core.mapper.MediaStoreMapper;
import ba.com.zira.sdr.core.validation.MediaStoreRequestValidation;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaStoreServiceImpl implements MediaStoreService {

    MediaStoreRequestValidation mediaStoreRequestValidation;
    MediaStoreDAO mediaStoreDAO;
    MediaStoreMapper mediaStoreMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayloadResponse<String> delete(EntityRequest<String> request) throws ApiException {
        mediaStoreRequestValidation.validateExistsMediaStoreRequest(request);

        mediaStoreDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK,
                String.format("MediaStore with id %s has been deleted!", request.getEntity()));
    }
}
