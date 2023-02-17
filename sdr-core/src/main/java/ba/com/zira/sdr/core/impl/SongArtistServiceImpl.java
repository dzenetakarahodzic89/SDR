package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.FilterExpression;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.core.mapper.SongArtistMapper;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongArtistServiceImpl implements SongArtistService {
    SongArtistDAO songArtistDAO;
    SongArtistMapper songArtistMapper;

    // TODO: add validation object for validating inupt requests

    @Override
    public PagedPayloadResponse<SongArtistResponse> getFiltered(FilterRequest filterRequest) {
        Filter processedFilter = this.processFilterAsSongArtistReadRequest(filterRequest.getFilter());
        PagedData<SongArtistEntity> songArtistEntities = songArtistDAO.findAll(processedFilter);
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, songArtistEntities, songArtistMapper::entitiesToDtos);
    }

    private Filter processFilterAsSongArtistReadRequest(Filter filter) {
        filter.getFilterExpressions().forEach((FilterExpression expr) -> {
            String attribute = expr.getAttribute();
            Long value = Long.parseLong((String) expr.getExpressionValueObject());

            if (attribute.equals("song")) {

                expr.setExpressionValueObject(new SongEntity(value, null, null, null, null, null, attribute, attribute, attribute,
                        attribute, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
            } else if (attribute.equals("artist")) {
                expr.setExpressionValueObject(
                        new ArtistEntity(value, null, null, null, null, null, null, null, null, null, null, null, null, null));
            } else if (attribute.equals("album")) {
                expr.setExpressionValueObject(new AlbumEntity(value, null, null, null, null, null, null, null, null, null, null));
            } else if (attribute.equals("label")) {
                expr.setExpressionValueObject(new LabelEntity(value, null, null, null, null, null, null, null, null, null, null));
            }
        });
        return filter;
    }
}
