package ba.com.zira.sdr.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.EraService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.era.EraSearchRequest;
import ba.com.zira.sdr.api.model.era.EraSearchResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.GenreDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EraServiceImpl implements EraService {

    EraDAO eraDAO;
    ArtistDAO artistDAO;
    GenreDAO genreDAO;
    LookupService lookupService;

    @Override
    public ListPayloadResponse<LoV> getEraLoVs(EmptyRequest req) throws ApiException {
        List<LoV> eras = eraDAO.getAllErasLoV();
        return new ListPayloadResponse<>(req, ResponseCode.OK, eras);
    }

    @Override
    public ListPayloadResponse<EraSearchResponse> find(final EntityRequest<EraSearchRequest> request) {
        // EraRequestValidation.validateExistsEraRequest(request);

        List<EraSearchResponse> eras = eraDAO.find(request.getEntity().getName(), request.getEntity().getSortBy(),

                request.getEntity().getPage(), request.getEntity().getPageSize(), request.getEntity().getAlbumIds(),
                request.getEntity().getArtistIds(), request.getEntity().getGenreIds());

        lookupService.lookupCoverImage(eras, EraSearchResponse::getId, ObjectType.ERA.getValue(), EraSearchResponse::setImageUrl,
                EraSearchResponse::getImageUrl);

        return new ListPayloadResponse<>(request, ResponseCode.OK, eras);
    }

}
