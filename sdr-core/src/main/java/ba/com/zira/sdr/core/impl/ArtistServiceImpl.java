package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.Artist;
import ba.com.zira.sdr.api.artist.ArtistByEras;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistSearchRequest;
import ba.com.zira.sdr.api.artist.ArtistSearchResponse;
import ba.com.zira.sdr.api.artist.ArtistSingleResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.lov.LoV;
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
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.EraEntity;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> create(final EntityRequest<ArtistCreateRequest> request) {
        var artistEntity = artistMapper.dtoToEntity(request.getEntity());
        artistEntity.setCreated(LocalDateTime.now());
        artistEntity.setCreatedBy(request.getUserId());
        artistEntity.setModified(LocalDateTime.now());
        artistEntity.setModifiedBy(request.getUserId());

        artistDAO.persist(artistEntity);
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

        if (artistDAO.personArtistExist(id).booleanValue()) {
            var errors = new ValidationErrors();
            errors.put(ValidationError.of("PERSON_ARTIST_EXISTS", "Not allowed to be deleted."));
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, "Artist delete validation error");
        }

        if (artistDAO.songArtistExist(id).booleanValue()) {
            var errors = new ValidationErrors();
            errors.put(ValidationError.of("SONG_ARTIST_EXISTS", "Not allowed to be deleted."));
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, "Artist delete validation error");
        }

        artistDAO.remove(artistDAO.findByPK(id));
        return new PayloadResponse<>(request, ResponseCode.OK, "Artist successfully deleted!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> update(final EntityRequest<ArtistUpdateRequest> request) {
        artistRequestValidation.validateUpdateArtistRequest(request);

        var artistEntity = artistDAO.findByPK(request.getEntity().getId());
        artistMapper.updateEntity(request.getEntity(), artistEntity);
        artistEntity.setCreated(LocalDateTime.now());
        artistEntity.setCreatedBy(request.getUserId());
        artistEntity.setModified(LocalDateTime.now());
        artistEntity.setModifiedBy(request.getUserId());

        artistDAO.merge(artistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, artistMapper.entityToDto(artistEntity));
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
        var artists = artistDAO.getArtistLoVs();
        return new ListPayloadResponse<>(request, ResponseCode.OK, artists);
    }

    @Override
    public PayloadResponse<ArtistSingleResponse> findById(EntityRequest<Long> request) throws ApiException {
        Long artistId = request.getEntity();
        var artist = artistDAO.findByPK(artistId);
        var artistSingleResponse = artistMapper.entityToSingleArtistDto(artist);
        List<SongEntity> recentSongs = songDAO.findByArtistId(artistId);
        artistSingleResponse.setRecentsSong(songMapper.entitiesToDtos(recentSongs));
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

}
