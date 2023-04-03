package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.artist.Artist;
import ba.com.zira.sdr.api.artist.ArtistByEras;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistImageResponse;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistSearchRequest;
import ba.com.zira.sdr.api.artist.ArtistSearchResponse;
import ba.com.zira.sdr.api.artist.ArtistSingleResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonArtistSingleResponse;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.AlbumMapper;
import ba.com.zira.sdr.core.mapper.ArtistMapper;
import ba.com.zira.sdr.core.mapper.LabelMapper;
import ba.com.zira.sdr.core.mapper.PersonMapper;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.ArtistValidation;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.MediaDAO;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.EraEntity;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.MediaEntity;
import ba.com.zira.sdr.dao.model.MediaStoreEntity;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    ArtistDAO artistDAO;
    EraDAO eraDAO;
    AlbumDAO albumDAO;
    LabelDAO labelDAO;
    PersonDAO personDAO;
    ArtistMapper artistMapper;
    AlbumMapper albumMapper;
    SongMapper songMapper;
    LabelMapper labelMapper;
    PersonMapper personMapper;
    ArtistValidation artistRequestValidation;
    PersonArtistDAO personArtistDAO;
    SongArtistDAO songArtistDAO;
    SongDAO songDAO;
    PersonRequestValidation personRequestValidation;
    LookupService lookupService;
    MediaDAO mediaDAO;
    MediaStoreDAO mediaStoreDAO;
    MediaService mediaService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> create(final EntityRequest<ArtistCreateRequest> request) throws ApiException {
        var artistEntity = artistMapper.dtoToEntity(request.getEntity());
        artistEntity.setCreated(LocalDateTime.now());
        artistEntity.setCreatedBy(request.getUserId());
        artistEntity.setStatus(Status.ACTIVE.value());

        if (request.getEntity().getPersonIds() != null && request.getEntity().getPersonIds().size() > 1) {
            artistEntity.setType("GROUP");
        } else {
            artistEntity.setType("ARTIST");
        }

        artistDAO.persist(artistEntity);

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {
            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.ARTIST.getValue());
            mediaRequest.setObjectId(artistEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));
        }

        for (Long personId : request.getEntity().getPersonIds()) {
            var personEntity = personDAO.findByPK(personId);

            var personArtistEntity = new PersonArtistEntity();
            personArtistEntity.setId(null);
            personArtistEntity.setArtist(artistEntity);
            personArtistEntity.setPerson(personEntity);
            personArtistEntity.setCreated(LocalDateTime.now());
            personArtistEntity.setCreatedBy(request.getUserId());
            personArtistEntity.setStatus(Status.ACTIVE.value());

            personArtistDAO.persist(personArtistEntity);
        }

        return new PayloadResponse<>(request, ResponseCode.OK, artistMapper.entityToDto(artistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> update(final EntityRequest<ArtistUpdateRequest> request) throws ApiException {
        artistRequestValidation.validateUpdateArtistRequest(request);

        var artistEntity = artistDAO.findByPK(request.getEntity().getId());
        artistMapper.updateEntity(request.getEntity(), artistEntity);

        artistEntity.setCreated(LocalDateTime.now());
        artistEntity.setCreatedBy(request.getUserId());
        artistEntity.setStatus(Status.ACTIVE.value());

        if (request.getEntity().getPersonIds() != null && request.getEntity().getPersonIds().size() > 1) {
            artistEntity.setType("GROUP");
        } else {
            artistEntity.setType("ARTIST");
        }

        artistDAO.persist(artistEntity);

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {
            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.ARTIST.getValue());
            mediaRequest.setObjectId(artistEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));
        }

        for (Long personId : request.getEntity().getPersonIds()) {
            var personEntity = personDAO.findByPK(personId);

            var personArtistEntity = new PersonArtistEntity();
            personArtistEntity.setId(null);
            personArtistEntity.setArtist(artistEntity);
            personArtistEntity.setPerson(personEntity);
            personArtistEntity.setCreated(LocalDateTime.now());
            personArtistEntity.setCreatedBy(request.getUserId());
            personArtistEntity.setStatus(Status.ACTIVE.value());

            personArtistDAO.persist(personArtistEntity);
        }

        return new PayloadResponse<>(request, ResponseCode.OK, artistMapper.entityToDto(artistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> createFromPerson(final EntityRequest<Long> request) throws ApiException {
        personRequestValidation.validateExistsPersonRequest(new EntityRequest<>(request.getEntity()));

        var personEntity = personDAO.findByPK(request.getEntity());
        var artistEntity = artistMapper.personToArtist(personEntity);

        artistEntity.setId(null);
        artistEntity.setCreated(LocalDateTime.now());
        artistEntity.setCreatedBy(request.getUserId());
        artistEntity.setStatus(Status.ACTIVE.value());
        artistEntity.setModified(null);
        artistEntity.setModifiedBy(null);
        artistEntity.setType("ARTIST");

        artistDAO.persist(artistEntity);

        var personArtistEntity = new PersonArtistEntity();

        personArtistEntity.setId(null);
        personArtistEntity.setArtist(artistEntity);
        personArtistEntity.setPerson(personEntity);
        personArtistEntity.setCreated(LocalDateTime.now());
        personArtistEntity.setCreatedBy(request.getUserId());
        personArtistEntity.setStatus(Status.ACTIVE.value());

        personArtistDAO.persist(personArtistEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, artistMapper.entityToDto(artistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        artistRequestValidation.validateExistsArtistRequest(request);
        Long id = request.getEntity();

        artistDAO.remove(artistDAO.findByPK(id));
        return new PayloadResponse<>(request, ResponseCode.OK, "Artist successfully deleted!");
    }

    @Override
    public PagedPayloadResponse<ArtistResponse> find(final FilterRequest request) {
        PagedData<ArtistEntity> artistEntity = artistDAO.findAll(request.getFilter());
        PagedData<ArtistResponse> artists = new PagedData<>();
        artists.setRecords(artistMapper.entitiesToDtos(artistEntity.getRecords()));
        PagedDataMetadataMapper.remapMetadata(artistEntity, artists);
        artists.getRecords().forEach(artist -> {
            artist.setSongArtistNames(songArtistDAO.songArtistEntityByArtist(artist.getId()));
            artist.setPersonArtistNames(personArtistDAO.personArtistEntityByArtist(artist.getId()));

        });
        return new PagedPayloadResponse<>(request, ResponseCode.OK, artists);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> copyImageToPersons(final EntityRequest<Long> request) {
        artistRequestValidation.validateExistsArtistRequest(request);
        Long id = request.getEntity();
        var artist = artistDAO.findByPK(id);
        var media = mediaDAO.findByTypeAndId(ObjectType.ARTIST.getValue(), id);
        if (media == null || media.getMediaStores().size() == 0) {
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, "Artist doesn't have image!");
        }
        for (var personArtist : artist.getPersonArtists()) {
            var existingPersonMedia = mediaDAO.findByTypeAndId(ObjectType.PERSON.getValue(), id);
            if (existingPersonMedia == null) {
                existingPersonMedia = new MediaEntity();
                existingPersonMedia.setCreated(LocalDateTime.now());
                existingPersonMedia.setCreatedBy(request.getUserId());
                existingPersonMedia.setModified(LocalDateTime.now());
                existingPersonMedia.setModifiedBy(request.getUserId());
                existingPersonMedia.setObjectId(personArtist.getPerson().getId());
                existingPersonMedia.setObjectType(ObjectType.PERSON.getValue());
                existingPersonMedia = mediaDAO.persist(existingPersonMedia);
            }
            var artistMediaStore = media.getMediaStores().get(0);
            var newMediaStore = new MediaStoreEntity();
            newMediaStore.setId(UUID.randomUUID().toString());
            newMediaStore.setCreated(LocalDateTime.now());
            newMediaStore.setCreatedBy(request.getUserId());
            newMediaStore.setModified(LocalDateTime.now());
            newMediaStore.setModifiedBy(request.getUserId());
            newMediaStore.setData(artistMediaStore.getData());
            newMediaStore.setExtension(artistMediaStore.getExtension());
            newMediaStore.setName(artistMediaStore.getName());
            newMediaStore.setUrl(artistMediaStore.getUrl());
            newMediaStore.setMedia(existingPersonMedia);
            newMediaStore.setType("COVER_IMAGE");
            mediaStoreDAO.persist(newMediaStore);

        }

        return new PayloadResponse<>(request, ResponseCode.OK, "Images Copied!");
    }

    @Override
    public ListPayloadResponse<ArtistByEras> getArtistsByEras(final EntityRequest<Long> request) throws ApiException {
        Long eraId = request.getEntity();
        List<Artist> artistGroup = new ArrayList<>();
        List<Artist> artistSolo = new ArrayList<>();
        List<LoV> persons = new ArrayList<>();
        List<LoV> artistList = artistDAO.artistsByEras(eraId);

        for (LoV artist : artistList) {
            Long artistId = artist.getId();
            String artistName = artist.getName();

            List<LoV> personList = personDAO.personsByArtistId(artistId);
            List<LoV> artistPersons = new ArrayList<>();

            for (LoV person : personList) {
                Long personId = person.getId();
                String personName = person.getName();
                artistPersons.add(new LoV(personId, personName));
                persons.add(new LoV(personId, personName));
            }

            if (artistPersons.size() == 1) {
                artistSolo.add(new Artist(artistId, artistName, artistPersons));
            } else {
                artistGroup.add(new Artist(artistId, artistName, artistPersons));
            }
        }

        var artistByEras = new ArtistByEras(artistGroup, artistSolo);
        List<ArtistByEras> artistByEras1 = new ArrayList<>();
        artistByEras1.add(artistByEras);

        return new ListPayloadResponse<>(request, ResponseCode.OK, artistByEras1);
    }

    @Override
    public ListPayloadResponse<LoV> getArtistNames(EmptyRequest request) throws ApiException {
        var artists = artistDAO.getArtistNamesAndSurnames();
        return new ListPayloadResponse<>(request, ResponseCode.OK, artists);
    }

    @Override
    public PayloadResponse<ArtistSingleResponse> findById(EntityRequest<Long> request) throws ApiException {
        Long artistId = request.getEntity();
        var artist = artistDAO.findByPK(artistId);
        var artistSingleResponse = artistMapper.entityToSingleArtistDto(artist);
        List<SongEntity> recentSongs = songDAO.findByArtistId(artistId);
        artistSingleResponse.setRecentsSong(songMapper.entitiesToDtos(recentSongs));
        List<LabelEntity> labelSongs = labelDAO.findByArtistId(artistId);
        artistSingleResponse.setLabels(labelMapper.entitiesToDtos(labelSongs));
        List<AlbumEntity> albums = albumDAO.findByArtistId(artistId);
        artistSingleResponse.setAlbums(albumMapper.entitiesToAlbumArtistSingleResponseDtos(albums));
        Long numberOfSongs = songArtistDAO.countAllByArtistId(artistId);
        artistSingleResponse.setNumberOfSongs(numberOfSongs);
        List<PersonEntity> persons = personDAO.findAllByArtistId(artistId);
        List<PersonArtistSingleResponse> personDTOs = new ArrayList<>();
        for (PersonEntity person : persons) {
            var personDTO = personMapper.entityToArtistSingleDtos(person);
            personDTO.setLabels(labelMapper.entitiesToDtos(person.getLabels()));
            personDTOs.add(personDTO);
        }
        artistSingleResponse.setPersons(personDTOs);
        artistSingleResponse.setAlbumCount(artistSingleResponse.getAlbums().size() + 0L);
        lookupService.lookupCoverImage(Arrays.asList(artistSingleResponse), ArtistSingleResponse::getId, ObjectType.ARTIST.getValue(),
                ArtistSingleResponse::setImageUrl, ArtistSingleResponse::getImageUrl);

        return new PayloadResponse<>(request, ResponseCode.OK, artistSingleResponse);
    }

    @Override
    public PayloadResponse<ArtistByEras> countArtistsByEras(EntityRequest<Long> request) {
        Long eraId = request.getEntity();
        Long soloArtistsCount = artistDAO.countSoloArtistsByEras(eraId);
        Long groupArtistsCount = artistDAO.countGroupArtistsByEras(eraId);

        EraEntity era = eraDAO.findByPK(eraId);
        var artistByEras = new ArtistByEras(era.getName(), soloArtistsCount, groupArtistsCount);

        return new PayloadResponse<>(request, ResponseCode.OK, artistByEras);
    }

    @Override
    public ListPayloadResponse<ArtistSearchResponse> getArtistsBySearch(EntityRequest<ArtistSearchRequest> request) throws ApiException {
        var requestEntity = request.getEntity();
        var artists = artistDAO.getArtistsBySearch(requestEntity.getName(), requestEntity.getGenre(), requestEntity.getAlbum(),
                requestEntity.getIsSolo(), requestEntity.getSortBy());

        for (var artist : artists) {
            lookupService.lookupCoverImage(Arrays.asList(artist), ArtistSearchResponse::getId, ObjectType.ARTIST.getValue(),
                    ArtistSearchResponse::setImageUrl, ArtistSearchResponse::getImageUrl);
        }
        return new ListPayloadResponse<>(request, ResponseCode.OK, artists);
    }

    @Override
    public ListPayloadResponse<ArtistSearchResponse> getRandomArtistsForSearch(EmptyRequest request) throws ApiException {
        var artists = artistDAO.getRandomArtistsForSearch();
        for (var artist : artists) {
            lookupService.lookupCoverImage(Arrays.asList(artist), ArtistSearchResponse::getId, ObjectType.ARTIST.getValue(),
                    ArtistSearchResponse::setImageUrl, ArtistSearchResponse::getImageUrl);
        }
        return new ListPayloadResponse<>(request, ResponseCode.OK, artists);
    }

    @Override
    public PayloadResponse<ArtistImageResponse> findPictureOfArtist(EntityRequest<Long> request) throws ApiException {
        var artist = artistDAO.findLoVForArtistImage(request.getEntity());
        lookupService.lookupCoverImage(Arrays.asList(artist), ArtistImageResponse::getId, ObjectType.ARTIST.getValue(),
                ArtistImageResponse::setImageUrl, ArtistImageResponse::getImageUrl);
        return new PayloadResponse<>(request, ResponseCode.OK, artist);
    }

}
