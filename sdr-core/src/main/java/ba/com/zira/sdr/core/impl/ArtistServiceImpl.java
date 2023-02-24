package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.Artist;
import ba.com.zira.sdr.api.artist.ArtistByEras;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.api.model.person.Person;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.ArtistMapper;
import ba.com.zira.sdr.core.validation.ArtistValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    ArtistDAO artistDAO;
    EraDAO eraDAO;
    PersonDAO personDAO;
    ArtistMapper artistMapper;
    ArtistValidation artistRequestValidation;
    PersonArtistDAO personArtistDAO;
    SongArtistDAO songArtistDAO;

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
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        artistRequestValidation.validateExistsArtistRequest(request);
        Long id = request.getEntity();

        if (artistDAO.personArtistExist(id).booleanValue()) {
            ValidationErrors errors = new ValidationErrors();
            errors.put(ValidationError.of("PERSON_ARTIST_EXISTS", "Not allowed to be deleted."));
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, "Artist delete validation error");
        }

        if (artistDAO.songArtistExist(id).booleanValue()) {
            ValidationErrors errors = new ValidationErrors();
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
    public ListPayloadResponse<ArtistByEras> getArtistByEras(final EntityRequest<Long> request) throws ApiException {
        Long eraId = request.getEntity();
        List<Artist> artistGroup = new ArrayList<>();
        List<Artist> artistSolo = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        Map<Long, String> artistMap = artistDAO.artistByEra(eraId);

        for (Map.Entry<Long, String> artistEntry : artistMap.entrySet()) {
            Long artistId = artistEntry.getKey();
            String artistName = artistEntry.getValue();

            Map<Long, String> personMap = personDAO.personByArtistId(artistId);
            List<Person> artistPersons = new ArrayList<>();

            for (Map.Entry<Long, String> personEntry : personMap.entrySet()) {
                Long personId = personEntry.getKey();
                artistPersons.add(new Person(personId));
                persons.add(new Person(personId));
            }

            if (artistPersons.size() == 1) {
                artistSolo.add(new Artist(artistId, artistName, artistPersons));
            } else {
                artistGroup.add(new Artist(artistId, artistName, artistPersons));
            }
        }

        ArtistByEras artistByEras = new ArtistByEras(artistGroup, artistSolo);
        List<ArtistByEras> artistByEras1 = new ArrayList<>();
        artistByEras1.add(artistByEras);

        return new ListPayloadResponse<>(request, ResponseCode.OK, artistByEras1);
    }

}
