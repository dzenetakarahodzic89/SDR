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
import org.springframework.boot.json.JsonParser;
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

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.User;
import ba.com.zira.sdr.api.SpotifyIntegrationService;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.dao.LabelDAO;
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
    private static final User systemUser = new User("System");
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyIntegrationServiceHelper.class);

    private HttpEntity getHttpEntity(String token) {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }

    private String getAuthenticationToken() {
        LOGGER.info("SPOTIFY INTEGRATION: Fetching authentication token...");
        String auth = clientId + ":" + clientSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        var headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var body = "grant_type=client_credentials";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(spotifyAuthUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("SPOTIFY INTEGRATION: Authentication token successfully obtained!");
            var responseBody = response.getBody();
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = springParser.parseMap(responseBody);
            return (String) map.get("access_token");
        } else {
            LOGGER.error("SPOTIFY INTEGRATION: Authentication token request failed! Api response: {}", response.getBody());
        }

        return null;
    }

    private void fetchAlbumsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for albums on Spotify...");
        List<LoV> albums = albumDAO.findAlbumsToFetchFromSpotify(responseLimit);
        albums.forEach(album -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(album.getName());
            spotifyIntegration.setObjectId(album.getId());
            spotifyIntegration.setObjectType("ALBUM");

            var url = spotifyApiUrl + "/search?q=" + album.getName() + "&type=album&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                spotifyIntegration.setResponse(response.getBody());
                LOGGER.info("SPOTIFY INTEGRATION: Albums successfully found!");
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Album responses successfully saved to database!");
                } catch (ApiException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error saving album responses to database! Exception message: {}", e.getMessage());
                }
            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Album search request failed! Api response: {}", response.getBody());
            }

        });

    }

    private void fetchSongsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for songs on Spotify...");
        List<LoV> songs = songDAO.findSongsToFetchFromSpotify(responseLimit);
        songs.forEach(song -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(song.getName());
            spotifyIntegration.setObjectId(song.getId());
            spotifyIntegration.setObjectType("SONG");

            var url = spotifyApiUrl + "/search?q=" + song.getName() + "&type=track&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                spotifyIntegration.setResponse(response.getBody());
                LOGGER.info("SPOTIFY INTEGRATION: Songs successfully found!");
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Song responses successfully saved to database!");
                } catch (ApiException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error saving song responses to database! Exception message: {}", e.getMessage());
                }
            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Song search request failed! Api response: {}", response.getBody());
            }

        });

    }

    private void fetchArtistsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for artists on Spotify...");
        List<LoV> artists = artistDAO.findArtistsToFetchFromSpotify(responseLimit);
        artists.forEach(artist -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(artist.getName());
            spotifyIntegration.setObjectId(artist.getId());
            spotifyIntegration.setObjectType("ARTIST");

            var url = spotifyApiUrl + "/search?q=" + artist.getName() + "&type=artist&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                spotifyIntegration.setResponse(response.getBody());
                LOGGER.info("SPOTIFY INTEGRATION: Artists successfully found!");
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Artist responses successfully saved to database!");
                } catch (ApiException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error saving artist responses to database! Exception message: {}", e.getMessage());
                }
            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Artist search request failed! Api response: {}", response.getBody());
            }
        });

    }

    private void updateSpotifyId() {
        LOGGER.info("SPOTIFY INTEGRATION: Fetching objects without Spotify id...");
        var objectsToUpdateSpotifyId = spotifyIntegrationDAO.getObjectsWithoutSpotifyId(responseLimit);
        var numberOfFetchedObjects = objectsToUpdateSpotifyId.size();
        LOGGER.info("SPOTIFY INTEGRATION: Fetched {} objects without Spotify id.", numberOfFetchedObjects);
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            SpotifyAlbumSearch albums = mapper.readValue(response, SpotifyAlbumSearch.class);
            List<SpotifyAlbumItem> albumItemList = albums.getAlbums().getItems();
            AlbumEntity albumEntity = albumDAO.findByPK(objectId);
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            SpotifyTrackSearch tracks = mapper.readValue(response, SpotifyTrackSearch.class);
            List<SpotifyTrackItem> trackItemList = tracks.getTracks().getItems();
            SongEntity songEntity = songDAO.findByPK(objectId);
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            SpotifyArtistSearch artists = mapper.readValue(response, SpotifyArtistSearch.class);
            List<SpotifyArtistItem> artistItemList = artists.getArtists().getItems();
            ArtistEntity artistEntity = artistDAO.findByPK(objectId);
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

    private String fetchSongsOfAlbumFromSpotify(String albumSpotifyId) {
        var token = getAuthenticationToken();
        var url = spotifyApiUrl + "/albums/" + albumSpotifyId;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("SPOTIFY INTEGRATION: Songs of album successfully fetched!");
            return response.getBody();

        } else {
            LOGGER.error("SPOTIFY INTEGRATION: Get album songs request failed! Api response: {}", response.getBody());
        }
        return null;
    }

    private void saveSongs(List<SpotifyAlbumsTrackItem> songs, AlbumEntity album, List<ArtistEntity> artists, LabelEntity label,
            GenreEntity genre, LocalDateTime releaseDate) {
        songs.forEach(song -> {
            var durationInMs = song.getDurationMs();
            SongEntity songEntity = new SongEntity();
            songEntity.setName(song.getName());
            songEntity.setSpotifyId(song.getId());
            songEntity.setSpotifyStatus("Done");
            songEntity.setPlaytime(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(durationInMs),
                    TimeUnit.MILLISECONDS.toMinutes(durationInMs) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(durationInMs)),
                    TimeUnit.MILLISECONDS.toSeconds(durationInMs)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationInMs))));
            songEntity.setDateOfRelease(releaseDate);
            songEntity.setGenre(genre);
            songEntity.setCreated(LocalDateTime.now());
            songEntity.setCreatedBy(systemUser.getUserId());
            songEntity.setStatus("Active");

            try {
                songDAO.persist(songEntity);

                artists.forEach(artist -> {
                    SongArtistEntity songArtistEntity = new SongArtistEntity();
                    songArtistEntity.setAlbum(album);
                    songArtistEntity.setArtist(artist);
                    songArtistEntity.setSong(songEntity);
                    songArtistEntity.setCreated(LocalDateTime.now());
                    songArtistEntity.setCreatedBy(systemUser.getUserId());
                    songArtistEntity.setLabel(label);
                    songArtistEntity.setStatus("Active");
                    try {
                        songArtistDAO.persist(songArtistEntity);
                        LOGGER.info("SPOTIFY INTEGRATION: Successfully saved new song to database!");
                    } catch (Exception e) {
                        LOGGER.error("SPOTIFY INTEGRATION: Saving new SongArtist record to database failed! Exception message: {}",
                                e.getMessage());
                    }
                });
            } catch (Exception e) {
                LOGGER.error("SPOTIFY INTEGRATION: Saving new song to database failed! Exception message: {}", e.getMessage());

            }

        });

    }

    private void addSongsFromSpotifyForAlbum(AlbumEntity album, List<ArtistEntity> artists, LabelEntity label) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            SpotifyGetAlbumsSongs response = mapper.readValue(fetchSongsOfAlbumFromSpotify(album.getSpotifyId()),
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

            saveSongs(response.getTracks().getItems(), album, artists, label, genre, parseDate(response.getReleaseDate()));
        } catch (JsonMappingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error mapping GetAlbum response! Exception message: {}", e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error processing GetAlbum response! Exception message: {}", e.getMessage());
        }

    }

    private void addSongsFromSpotifyForAlbums() {
        LabelEntity defaultLabel = labelDAO.findByPK(0L);
        var albums = albumDAO.findAlbumsToFetchSongsFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} albums to fetch songs for!", albums.size());

        albums.forEach(album -> {
            List<ArtistEntity> artists = artistDAO.artistsByAlbum(album.getId());
            addSongsFromSpotifyForAlbum(album, artists, defaultLabel);
        });
    }

    private String fetchAlbumsOfArtistFromSpotify(String artistSpotifyId) {
        var token = getAuthenticationToken();
        var url = spotifyApiUrl + "/artists/" + artistSpotifyId + "/albums";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("SPOTIFY INTEGRATION: Albums of artist successfully fetched!");
            return response.getBody();

        } else {
            LOGGER.error("SPOTIFY INTEGRATION: Get artist albums request failed! Api response: {}", response.getBody());
        }
        return null;
    }

    private void saveAlbums(List<SpotifyArtistsAlbumItem> albums, ArtistEntity artist, LabelEntity defaultLabel) {
        albums.forEach(album -> {
            List<ArtistEntity> artists = new ArrayList<>();
            artists.add(artist);
            AlbumEntity albumEntity = new AlbumEntity();
            albumEntity.setName(album.getName());
            albumEntity.setSpotifyId(album.getId());
            albumEntity.setSpotifyStatus("Done");
            albumEntity.setDateOfRelease(parseDate(album.getReleaseDate()));
            albumEntity.setCreated(LocalDateTime.now());
            albumEntity.setCreatedBy(systemUser.getUserId());
            albumEntity.setStatus("Active");

            try {
                albumDAO.persist(albumEntity);
                addSongsFromSpotifyForAlbum(albumEntity, artists, defaultLabel);
                albumEntity.setSpotifyStatus("Done");
                albumEntity.setModified(LocalDateTime.now());
                albumEntity.setModifiedBy(systemUser.getUserId());
                albumDAO.merge(albumEntity);
                LOGGER.info("SPOTIFY INTEGRATION: Successfully saved new album to database!");
            } catch (Exception e) {
                LOGGER.error("SPOTIFY INTEGRATION: Saving new album to database failed! Exception message: {}", e.getMessage());

            }

        });

    }

    private void addAlbumsFromSpotifyForArtist() {
        LabelEntity defaultLabel = labelDAO.findByPK(0L);
        var artists = artistDAO.findArtistsToFetchAlbumsFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INETGRATION: Found {} artists to fetch albums for!", artists.size());
        ObjectMapper mapper = new ObjectMapper();
        artists.forEach(artist -> {
            try {
                SpotifyGetArtistsAlbums response = mapper.readValue(fetchAlbumsOfArtistFromSpotify(artist.getSpotifyId()),
                        SpotifyGetArtistsAlbums.class);

                saveAlbums(response.getItems(), artist, defaultLabel);
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

    @Scheduled(fixedDelay = 600000)
    public void fetchDataFromSpotify() {
        LOGGER.info("SPOTIFY INTEGRATION: Scheduled search started");
        String token = getAuthenticationToken();
        fetchAlbumsFromSpotify(token);
        fetchSongsFromSpotify(token);
        fetchArtistsFromSpotify(token);
    }

    @Scheduled(fixedDelay = 600000, initialDelay = 300000)
    public void updateWithDataFromSpotify() {
        LOGGER.info("SPOTIFY INTEGRATION: Scheduled data update started");
        updateSpotifyId();
        addSongsFromSpotifyForAlbums();
        addAlbumsFromSpotifyForArtist();

    }

}
