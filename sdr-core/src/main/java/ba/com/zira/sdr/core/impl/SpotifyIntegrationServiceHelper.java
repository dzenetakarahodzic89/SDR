package ba.com.zira.sdr.core.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.sdr.api.SpotifyIntegrationService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.MultiSearchDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SpotifyIntegrationDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.spotify.album.search.SpotifyAlbumItem;
import ba.com.zira.sdr.spotify.album.search.SpotifyAlbumSearch;
import ba.com.zira.sdr.spotify.album.songs.SpotifyAlbumsTrackItem;
import ba.com.zira.sdr.spotify.album.songs.SpotifyGetAlbumsSongs;
import ba.com.zira.sdr.spotify.artist.albums.SpotifyArtistsAlbumItem;
import ba.com.zira.sdr.spotify.artist.albums.SpotifyGetArtistsAlbums;
import ba.com.zira.sdr.spotify.artist.search.SpotifyArtistItem;
import ba.com.zira.sdr.spotify.artist.search.SpotifyArtistSearch;
import ba.com.zira.sdr.spotify.song.search.SpotifyTrackItem;
import ba.com.zira.sdr.spotify.song.search.SpotifyTrackSearch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotifyIntegrationServiceHelper {
    @Autowired
    private final RestTemplate restTemplate;
    @NonNull
    private AlbumDAO albumDAO;
    @NonNull
    private SongDAO songDAO;
    @NonNull
    private ArtistDAO artistDAO;
    @NonNull
    private LabelDAO labelDAO;
    @NonNull
    private GenreDAO genreDAO;
    @NonNull
    private SongArtistDAO songArtistDAO;
    @NonNull
    private SpotifyIntegrationService spotifyIntegrationService;
    @NonNull
    private SpotifyIntegrationDAO spotifyIntegrationDAO;

    @NonNull
    MultiSearchDAO multiSearchDAO;

    @Value("${spring.security.oauth2.client.registration.spotify.clientId}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.spotify.clientSecret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.spotify.accessTokenUri}")
    private String spotifyAuthUrl;
    @Value("${spring.security.oauth2.client.registration.spotify.apiUrl}")
    private String spotifyApiUrl;
    @Value("${spring.security.oauth2.client.registration.spotify.responseLimit}")
    private int responseLimit;
    private static final User systemUser = new User("Spotify Integration");
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyIntegrationServiceHelper.class);
    private static final String AUTHORIZATION = "Authorization";

    private HttpEntity<?> getHttpEntity(String token) {
        var headers = new HttpHeaders();
        headers.set(AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }

    private String getAuthenticationToken() {
        LOGGER.info("SPOTIFY INTEGRATION: Fetching authentication token...");
        String auth = clientId + ":" + clientSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        var headers = new HttpHeaders();
        headers.set(AUTHORIZATION, authHeader);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var body = "grant_type=client_credentials";

        try {
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(spotifyAuthUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                LOGGER.info("SPOTIFY INTEGRATION: Authentication token successfully obtained!");
                var responseBody = response.getBody();
                var springParser = JsonParserFactory.getJsonParser();
                Map<String, Object> map = springParser.parseMap(responseBody);
                return (String) map.get("access_token");
            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Authentication token request failed! Api response: {}", response.getBody());
            }
        } catch (Exception e) {
            LOGGER.error("SPOTIFY INTEGRATION: Authentication token request failed! Api response: {}", e.getMessage());
        }
        return null;
    }

    private void fetchAlbumsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for albums on Spotify...");
        List<LoV> albums = albumDAO.findAlbumsToFetchFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} albums to fetch from Spotify.", albums.size());
        albums.forEach(album -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(album.getName());
            spotifyIntegration.setObjectId(album.getId());
            spotifyIntegration.setObjectType("ALBUM");

            var url = spotifyApiUrl + "/search?q=" + album.getName() + "&type=album&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    spotifyIntegration.setResponse(response.getBody());
                    LOGGER.info("SPOTIFY INTEGRATION: Album {} successfully found!", album.getName());
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Album response successfully saved to database!");
                } else {
                    LOGGER.error("SPOTIFY INTEGRATION: Album search request failed! Api response: {}", response.getBody());
                }
            } catch (Exception e) {
                LOGGER.error("SPOTIFY INTEGRATION: Album search request failed! Api response: {}", e.getMessage());
            }

        });

    }

    private void fetchSongsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for songs on Spotify...");
        List<LoV> songs = songDAO.findSongsToFetchFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} songs to fetch from Spotify.", songs.size());
        songs.forEach(song -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(song.getName());
            spotifyIntegration.setObjectId(song.getId());
            spotifyIntegration.setObjectType("SONG");

            var url = spotifyApiUrl + "/search?q=" + song.getName() + "&type=track&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    spotifyIntegration.setResponse(response.getBody());
                    LOGGER.info("SPOTIFY INTEGRATION: Song {} successfully found!", song.getName());
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Song response successfully saved to database!");

                } else {
                    LOGGER.error("SPOTIFY INTEGRATION: Song search request failed! Api response: {}", response.getBody());
                }
            } catch (Exception e) {
                LOGGER.error("SPOTIFY INTEGRATION: Song search request failed! Api response: {}", e.getMessage());
            }

        });

    }

    private void fetchArtistsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for artists on Spotify...");
        List<LoV> artists = artistDAO.findArtistsToFetchFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} artists to fetch from Spotify.", artists.size());
        artists.forEach(artist -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(artist.getName());
            spotifyIntegration.setObjectId(artist.getId());
            spotifyIntegration.setObjectType("ARTIST");

            var url = spotifyApiUrl + "/search?q=" + artist.getName() + "&type=artist&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    spotifyIntegration.setResponse(response.getBody());
                    LOGGER.info("SPOTIFY INTEGRATION: Artist {} successfully found!", artist.getName());
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Artist response successfully saved to database!");
                } else {
                    LOGGER.error("SPOTIFY INTEGRATION: Artist search request failed! Api response: {}", response.getBody());
                }
            } catch (Exception e) {
                LOGGER.error("SPOTIFY INTEGRATION: Artist search request failed! Api response: {}", e.getMessage());
            }
        });

    }

    private void updateSpotifyId() {
        LOGGER.info("SPOTIFY INTEGRATION: Fetching objects without Spotify id...");
        var objectsToUpdateSpotifyId = spotifyIntegrationDAO.getObjectsWithoutSpotifyId(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Fetched {} objects without Spotify id.", objectsToUpdateSpotifyId.size());
        objectsToUpdateSpotifyId.forEach(r -> {

            switch (r.getObjectType()) {
            case "ALBUM":
                updateAlbumSpotifyId(r.getResponse(), r.getObjectId());
                break;
            case "SONG":
                updateSongSpotifyId(r.getResponse(), r.getObjectId());
                break;
            case "ARTIST":
                updateArtistSpotifyId(r.getResponse(), r.getObjectId());
                break;
            default:
                LOGGER.info("SPOTIFY INTEGRATION: Unsupported object type.");
                break;

            }

        });
        LOGGER.info("SPOTIFY INTEGRATION: Updating Spotify ids done!");
    }

    private void updateAlbumSpotifyId(String response, Long objectId) {
        var mapper = new N2bObjectMapper();
        try {
            SpotifyAlbumSearch albums = mapper.readValue(response, SpotifyAlbumSearch.class);
            List<SpotifyAlbumItem> albumItemList = albums.getAlbums().getItems();
            var albumEntity = albumDAO.findByPK(objectId);
            if (!albumItemList.isEmpty()) {
                SpotifyAlbumItem firstResponse = albumItemList.get(0);
                albumEntity.setSpotifyId(firstResponse.getId());
            } else {
                albumEntity.setSpotifyStatus("Done");
            }
            albumEntity.setModified(LocalDateTime.now());
            albumEntity.setModifiedBy(systemUser.getUserId());
            albumDAO.merge(albumEntity);
        } catch (JsonMappingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error mapping album response. Exception message: {}", e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error processing album response. Exception message: {}", e.getMessage());
        }
    }

    private void updateSongSpotifyId(String response, Long objectId) {
        var mapper = new N2bObjectMapper();
        try {
            SpotifyTrackSearch tracks = mapper.readValue(response, SpotifyTrackSearch.class);
            List<SpotifyTrackItem> trackItemList = tracks.getTracks().getItems();
            var songEntity = songDAO.findByPK(objectId);
            if (!trackItemList.isEmpty()) {
                SpotifyTrackItem firstResponse = trackItemList.get(0);
                songEntity.setSpotifyId(firstResponse.getId());
                songEntity.setModified(LocalDateTime.now());
                songEntity.setModifiedBy(systemUser.getUserId());
            }
            songEntity.setSpotifyStatus("Done");

            songDAO.merge(songEntity);
        } catch (JsonMappingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error mapping song response. Exception message: {}", e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error processing song response. Exception message: {}", e.getMessage());
        }
    }

    private void updateArtistSpotifyId(String response, Long objectId) {
        var mapper = new N2bObjectMapper();
        try {
            SpotifyArtistSearch artists = mapper.readValue(response, SpotifyArtistSearch.class);
            List<SpotifyArtistItem> artistItemList = artists.getArtists().getItems();
            var artistEntity = artistDAO.findByPK(objectId);
            if (!artistItemList.isEmpty()) {
                SpotifyArtistItem firstResponse = artistItemList.get(0);
                artistEntity.setSpotifyId(firstResponse.getId());
            } else {
                artistEntity.setSpotifyStatus("Done");
            }
            artistEntity.setModified(LocalDateTime.now());
            artistEntity.setModifiedBy(systemUser.getUserId());
            artistDAO.merge(artistEntity);
        } catch (JsonMappingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error mapping artist response. Exception message: {}", e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error processing artist response. Exception message: {}", e.getMessage());
        }
    }

    private LocalDateTime parseDate(String date) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter).atStartOfDay();
        } catch (Exception e) {
            return LocalDate.parse(date.concat("-01-01"), formatter).atStartOfDay();
        }
    }

    private String fetchSongsOfAlbumFromSpotify(String albumSpotifyId, String albumName) {
        var token = getAuthenticationToken();
        var url = spotifyApiUrl + "/albums/" + albumSpotifyId;

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                LOGGER.info("SPOTIFY INTEGRATION: Songs of album {} successfully fetched!", albumName);
                return response.getBody();

            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Get album songs request failed! Api response: {}", response.getBody());
            }
        } catch (Exception e) {
            LOGGER.error("SPOTIFY INTEGRATION: Get album songs request failed! Api response: {}", e.getMessage());
        }
        return null;
    }

    private void saveSongs(List<SpotifyAlbumsTrackItem> songs, AlbumEntity album, List<ArtistEntity> artists, LabelEntity label,
            GenreEntity genre, LocalDateTime releaseDate, Map<String, MultiSearchResponse> mapOfExistingSpotifyIds) {
        songs.forEach(song -> {
            if (mapOfExistingSpotifyIds.get(song.getId()) == null) {
                var durationInMs = song.getDurationMs();
                var songEntity = new SongEntity();
                songEntity.setName(song.getName());
                songEntity.setSpotifyId(song.getId());
                songEntity.setSpotifyStatus("Done");
                songEntity.setPlaytime(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(durationInMs),
                        TimeUnit.MILLISECONDS.toMinutes(durationInMs)
                                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(durationInMs)),
                        TimeUnit.MILLISECONDS.toSeconds(durationInMs)
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationInMs))));
                songEntity.setDateOfRelease(releaseDate);
                songEntity.setGenre(genre);
                songEntity.setCreated(LocalDateTime.now());
                songEntity.setCreatedBy(systemUser.getUserId());
                songEntity.setStatus(Status.ACTIVE.getValue());
                songEntity.setPlaytimeInSeconds(TimeUnit.MILLISECONDS.toSeconds(durationInMs));
                mapOfExistingSpotifyIds.put(song.getId(),
                        new MultiSearchResponse(song.getName(), ObjectType.SONG.getValue(), song.getId()));
                try {
                    songDAO.persist(songEntity);

                    artists.forEach(artist -> {
                        var songArtistEntity = new SongArtistEntity();
                        songArtistEntity.setAlbum(album);
                        songArtistEntity.setArtist(artist);
                        songArtistEntity.setSong(songEntity);
                        songArtistEntity.setCreated(LocalDateTime.now());
                        songArtistEntity.setCreatedBy(systemUser.getUserId());
                        songArtistEntity.setLabel(label);
                        songArtistEntity.setStatus("Active");
                        try {
                            songArtistDAO.persist(songArtistEntity);
                            LOGGER.info("SPOTIFY INTEGRATION: Successfully saved song {} to database!", song.getName());
                        } catch (Exception e) {
                            LOGGER.error("SPOTIFY INTEGRATION: Saving new SongArtist record to database failed! Exception message: {}",
                                    e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Saving new song to database failed! Exception message: {}", e.getMessage());

                }
            } else {
                LOGGER.info("Song {} has already been integrated.", song.getName());
            }
        });

    }

    private void addSongsFromSpotifyForAlbum(AlbumEntity album, List<ArtistEntity> artists, LabelEntity label,
            Map<String, MultiSearchResponse> mapOfExistingSpotifyIds) {
        var mapper = new ObjectMapper();
        try {
            SpotifyGetAlbumsSongs response = mapper.readValue(fetchSongsOfAlbumFromSpotify(album.getSpotifyId(), album.getName()),
                    SpotifyGetAlbumsSongs.class);

            GenreEntity genre = null;
            if (!response.getGenres().isEmpty()) {
                genre = genreDAO.findByName((String) response.getGenres().get(0));
                if (genre == null) {
                    genre = genreDAO.findByPK(0L);
                }
            } else {
                genre = genreDAO.findByPK(0L);
            }

            saveSongs(response.getTracks().getItems(), album, artists, label, genre, parseDate(response.getReleaseDate()),
                    mapOfExistingSpotifyIds);
        } catch (JsonMappingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error mapping GetAlbum response! Exception message: {}", e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error processing GetAlbum response! Exception message: {}", e.getMessage());
        }

    }

    private void addSongsFromSpotifyForAlbums(Map<String, MultiSearchResponse> mapOfExistingSpotifyIds) {
        LabelEntity defaultLabel = labelDAO.findByPK(0L);
        var albums = albumDAO.findAlbumsToFetchSongsFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} albums to fetch songs for!", albums.size());

        albums.forEach(album -> {
            List<ArtistEntity> artists = artistDAO.artistsByAlbum(album.getId());
            addSongsFromSpotifyForAlbum(album, artists, defaultLabel, mapOfExistingSpotifyIds);
            album.setSpotifyStatus("Done");
            album.setModified(LocalDateTime.now());
            album.setModifiedBy(systemUser.getUserId());
            albumDAO.merge(album);
        });
    }

    private String fetchAlbumsOfArtistFromSpotify(String artistSpotifyId, String artistName) {
        var token = getAuthenticationToken();
        var url = spotifyApiUrl + "/artists/" + artistSpotifyId + "/albums";

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                LOGGER.info("SPOTIFY INTEGRATION: Albums of artist {} successfully fetched!", artistName);
                return response.getBody();

            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Get artist albums request failed! Api response: {}", response.getBody());
            }
        } catch (Exception e) {
            LOGGER.error("SPOTIFY INTEGRATION: Get artist albums request failed! Api response: {}", e.getMessage());
        }
        return null;
    }

    private void saveAlbums(List<SpotifyArtistsAlbumItem> albums, ArtistEntity artist, LabelEntity defaultLabel,
            Map<String, MultiSearchResponse> mapOfExistingSpotifyIds) {
        albums.forEach(album -> {
            if (mapOfExistingSpotifyIds.get(album.getId()) == null) {
                List<ArtistEntity> artists = new ArrayList<>();
                artists.add(artist);
                var albumEntity = new AlbumEntity();
                albumEntity.setName(album.getName());
                albumEntity.setSpotifyId(album.getId());
                albumEntity.setSpotifyStatus("Done");
                albumEntity.setDateOfRelease(parseDate(album.getReleaseDate()));
                albumEntity.setCreated(LocalDateTime.now());
                albumEntity.setCreatedBy(systemUser.getUserId());
                albumEntity.setStatus("Active");
                mapOfExistingSpotifyIds.put(album.getId(),
                        new MultiSearchResponse(album.getName(), ObjectType.ALBUM.getValue(), album.getId()));
                try {
                    albumDAO.persist(albumEntity);
                    addSongsFromSpotifyForAlbum(albumEntity, artists, defaultLabel, mapOfExistingSpotifyIds);
                    albumEntity.setSpotifyStatus("Done");
                    albumEntity.setModified(LocalDateTime.now());
                    albumEntity.setModifiedBy(systemUser.getUserId());
                    albumDAO.merge(albumEntity);
                    LOGGER.info("SPOTIFY INTEGRATION: Successfully saved album {} to database!", album.getName());
                } catch (Exception e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Saving new album to database failed! Exception message: {}", e.getMessage());

                }
            } else {
                LOGGER.info("Album {} has already been integrated.", album.getName());
            }
        });

    }

    private void addAlbumsFromSpotifyForArtist(Map<String, MultiSearchResponse> mapOfExistingSpotifyIds) {
        LabelEntity defaultLabel = labelDAO.findByPK(0L);
        var artists = artistDAO.findArtistsToFetchAlbumsFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} artists to fetch albums for!", artists.size());
        var mapper = new N2bObjectMapper();

        artists.forEach(artist -> {
            var artistName = artist.getName();
            if (artist.getSurname() != null) {
                artistName += " " + artist.getSurname();
            }
            try {
                SpotifyGetArtistsAlbums response = mapper.readValue(fetchAlbumsOfArtistFromSpotify(artist.getSpotifyId(), artistName),
                        SpotifyGetArtistsAlbums.class);

                saveAlbums(response.getItems(), artist, defaultLabel, mapOfExistingSpotifyIds);
                artist.setSpotifyStatus("Done");
                artist.setModified(LocalDateTime.now());
                artist.setModifiedBy(systemUser.getUserId());
                artistDAO.merge(artist);
            } catch (JsonMappingException e) {
                LOGGER.error("SPOTIFY INTEGRATION: Error mapping GetAlbum response! Exception message: {}", e.getMessage());
            } catch (JsonProcessingException e) {
                LOGGER.error("SPOTIFY INTEGRATION: Error processing GetAlbum response! Exception message: {}", e.getMessage());
            }
        });
    }

    @Scheduled(fixedDelayString = "${spring.security.oauth2.client.registration.spotify.taskDelay}", initialDelay = 30000)
    public void fetchDataFromSpotify() {
        LOGGER.info("SPOTIFY INTEGRATION: Scheduled search started");
        String token = getAuthenticationToken();
        fetchAlbumsFromSpotify(token);
        fetchSongsFromSpotify(token);
        fetchArtistsFromSpotify(token);
    }

    @Scheduled(fixedDelayString = "${spring.security.oauth2.client.registration.spotify.taskDelay}")
    public void updateWithDataFromSpotify() {
        LOGGER.info("SPOTIFY INTEGRATION: Scheduled data update started");
        updateSpotifyId();
        multiSearchDAO.deleteTable();
        multiSearchDAO.createTableAndFillWithData();
        Map<String, MultiSearchResponse> mapOfExistingSpotifyIds = multiSearchDAO.getCurrentSpotifyIds();
        addSongsFromSpotifyForAlbums(mapOfExistingSpotifyIds);
        addAlbumsFromSpotifyForArtist(mapOfExistingSpotifyIds);

    }

}
