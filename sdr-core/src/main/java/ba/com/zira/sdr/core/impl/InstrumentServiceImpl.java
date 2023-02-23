package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.request.ListRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.FilterExpression;
import ba.com.zira.commons.model.FilterExpression.FilterOperation;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.InstrumentService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.instrument.InsertSongInstrumentRequest;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.api.instrument.ResponseSongInstrument;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.InstrumentMapper;
import ba.com.zira.sdr.core.mapper.SongInstrumentMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.InstrumentRequestValidation;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.dao.model.InstrumentEntity;
import ba.com.zira.sdr.dao.model.InstrumentEntity_;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.PersonEntity_;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.SongEntity_;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class InstrumentServiceImpl implements InstrumentService {
    InstrumentDAO instrumentDAO;
    SongInstrumentDAO songInstrumentDAO;
    PersonDAO personDAO;
    SongDAO songDAO;
    InstrumentMapper instrumentMapper;
    LookupService lookupService;
    SongInstrumentMapper songInstrumentMapper;
    InstrumentRequestValidation instrumentRequestValidation;
    MediaService mediaService;

    @Override
    public PagedPayloadResponse<InstrumentResponse> get(final FilterRequest filterRequest) {
        PagedData<InstrumentEntity> instrumentEntities = instrumentDAO.findAll(filterRequest.getFilter());
        PagedData<InstrumentResponse> response = new PagedData<>();
        response.setRecords(instrumentMapper.entitiesToDtos(instrumentEntities.getRecords()));
        PagedDataMetadataMapper.remapMetadata(instrumentEntities, response);
        lookupService.lookupCoverImage(response.getRecords(), InstrumentResponse::getId, ObjectType.INSTRUMENT.getValue(),
                InstrumentResponse::setImageUrl, InstrumentResponse::getImageUrl);
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, instrumentEntities, instrumentMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<InstrumentResponse> create(final EntityRequest<InstrumentCreateRequest> request) throws ApiException {
        var instrumentEntity = instrumentMapper.dtoToEntity(request.getEntity());
        instrumentEntity.setStatus(Status.INACTIVE.value());
        instrumentEntity.setCreated(LocalDateTime.now());
        instrumentEntity.setModified(LocalDateTime.now());
        instrumentEntity.setCreatedBy(request.getUserId());
        instrumentEntity.setModifiedBy(request.getUserId());

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {

            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.INSTRUMENT.getValue());
            mediaRequest.setObjectId(instrumentEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));

        }

        instrumentDAO.persist(instrumentEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, instrumentMapper.entityToDto(instrumentEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<InstrumentResponse> update(final EntityRequest<InstrumentUpdateRequest> entityRequest) {
        instrumentRequestValidation.validateUpdateInstrumentRequest(entityRequest);
        InstrumentEntity instrumentEntity = instrumentDAO.findByPK(entityRequest.getEntity().getId());
        instrumentMapper.updateEntity(entityRequest.getEntity(), instrumentEntity);

        instrumentEntity.setModified(LocalDateTime.now());
        instrumentEntity.setModifiedBy(entityRequest.getUserId());

        instrumentDAO.merge(instrumentEntity);
        return new PayloadResponse<>(entityRequest, ResponseCode.OK, instrumentMapper.entityToDto(instrumentEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<InstrumentResponse> delete(final EntityRequest<Long> entityRequest) {
        instrumentRequestValidation.validateExistsInstrumentRequest(entityRequest);
        InstrumentEntity deletedEntity = instrumentDAO.findByPK(entityRequest.getEntity());

        instrumentDAO.removeByPK(entityRequest.getEntity());
        return new PayloadResponse<>(entityRequest, ResponseCode.OK, instrumentMapper.entityToDto(deletedEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ListPayloadResponse<ResponseSongInstrument> insertInstrumentsToSong(ListRequest<InsertSongInstrumentRequest> entityRequest)
            throws ApiException {
        Filter f = new Filter();

        f.addFilterExpression(new FilterExpression(SongEntity_.id.getName(), FilterOperation.IN,
                entityRequest.getList().stream().map(InsertSongInstrumentRequest::getSongId).collect(Collectors.toList())));
        List<SongEntity> songsFromRequest = songDAO.findAll(f).getRecords();
        f.setFilterExpressions(null);
        //

        f.addFilterExpression(new FilterExpression(InstrumentEntity_.id.getName(), FilterOperation.IN,
                entityRequest.getList().stream().map(InsertSongInstrumentRequest::getInstrumentId).collect(Collectors.toList())));
        List<InstrumentEntity> instrumentFromRequest = instrumentDAO.findAll(f).getRecords();
        f.setFilterExpressions(null);
        //
        f.addFilterExpression(new FilterExpression(PersonEntity_.id.getName(), FilterOperation.IN,
                entityRequest.getList().stream().map(InsertSongInstrumentRequest::getPersonId).collect(Collectors.toList())));
        List<PersonEntity> personFromRequest = personDAO.findAll(f).getRecords();

        List<SongInstrumentEntity> songInstrumentList = new ArrayList<>();

        for (var item : entityRequest.getList()) {
            var songInstrumentEntity = new SongInstrumentEntity();
            var song = songsFromRequest.stream().filter(a -> item.getSongId().equals(a.getId())).collect(Collectors.toList()).get(0);
            var instrument = instrumentFromRequest.stream().filter(a -> item.getInstrumentId().equals(a.getId()))
                    .collect(Collectors.toList()).get(0);
            var person = personFromRequest.stream().filter(a -> item.getPersonId().equals(a.getId())).collect(Collectors.toList()).get(0);

            songInstrumentEntity.setPerson(person);
            songInstrumentEntity.setSong(song);
            songInstrumentEntity.setInstrument(instrument);
            songInstrumentEntity.setCreated(LocalDateTime.now());
            songInstrumentEntity.setModified(LocalDateTime.now());
            songInstrumentEntity.setModifiedBy(entityRequest.getUserId());
            songInstrumentEntity.setName(item.getName());
            songInstrumentEntity.setCreatedBy(entityRequest.getUserId());

            songInstrumentList.add(songInstrumentEntity);
        }
        songInstrumentDAO.persistCollection(songInstrumentList);

        var responseList = new ArrayList<ResponseSongInstrument>();
        for (var item : songInstrumentList) {
            var response = new ResponseSongInstrument();
            response.setCreated(item.getCreated());
            response.setCreated_by(item.getCreatedBy());
            response.setInstrumentId(item.getInstrument().getId());
            response.setModified(item.getModified());
            response.setModifiedBy(item.getModifiedBy());
            response.setSongId(item.getSong().getId());
            response.setPersonId(item.getPerson().getId());
            response.setName(item.getName());
            responseList.add(response);
        }

        return new ListPayloadResponse<ResponseSongInstrument>(entityRequest, ResponseCode.OK, responseList);
    }

}
