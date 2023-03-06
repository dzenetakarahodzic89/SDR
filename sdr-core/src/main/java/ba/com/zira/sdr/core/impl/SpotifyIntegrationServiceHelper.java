package ba.com.zira.sdr.core.impl;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SpotifyIntegrationDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.spotify.album.search.AlbumItem;
import ba.com.zira.sdr.spotify.album.search.AlbumSearch;
import ba.com.zira.sdr.spotify.artist.search.ArtistItem;
import ba.com.zira.sdr.spotify.artist.search.ArtistSearch;
import ba.com.zira.sdr.spotify.song.search.TrackItem;
import ba.com.zira.sdr.spotify.song.search.TrackSearch;
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
            LOGGER.error("SPOTIFY INTEGRATION: Authentication token request failed! Api response:" + response.getBody());
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

            var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            var url = spotifyApiUrl + "?q=" + album.getName() + "&type=album&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                spotifyIntegration.setResponse(response.getBody());
                LOGGER.info("SPOTIFY INTEGRATION: Albums successfully found!");
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Album responses successfully saved to database!");
                } catch (ApiException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error saving album responses to database! Error message: " + e.getMessage());
                }
            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Album search request failed! Api response: " + response.getBody());
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

            var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            var url = spotifyApiUrl + "?q=" + song.getName() + "&type=track&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                spotifyIntegration.setResponse(response.getBody());
                LOGGER.info("SPOTIFY INTEGRATION: Songs successfully found!");
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Song responses successfully saved to database!");
                } catch (ApiException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error saving song responses to database! Error message: " + e.getMessage());
                }
            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Song search request failed! Api response: " + response.getBody());
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

            var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            var url = spotifyApiUrl + "?q=" + artist.getName() + "&type=artist&limit=" + responseLimit;

            spotifyIntegration.setRequest(url);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                spotifyIntegration.setResponse(response.getBody());
                LOGGER.info("SPOTIFY INTEGRATION: Artists successfully found!");
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                    LOGGER.info("SPOTIFY INTEGRATION: Artist responses successfully saved to database!");
                } catch (ApiException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error saving artist responses to database! Error message: " + e.getMessage());
                }
            } else {
                LOGGER.error("SPOTIFY INTEGRATION: Artist search request failed! Api response: " + response.getBody());
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

    @Scheduled(fixedDelay = 600000, initialDelay = 60000)
    public void updateWithDataFromSpotify() {
        LOGGER.info("SPOTIFY INTEGRATION: Scheduled data update started");
        updateSpotifyId();
        // get 5 rows from spotify int where spotify_id in corresponding object
        // table is null
        // update spotify_id on those rows
        // get 5 album rows with spotify status null and fetch tracks for those
        // albums, add songs and songartist
        // get 5 artist rows with spotify status null and fetch albums for those
        // artists
    }

    private void updateSpotifyId() {
        LOGGER.info("SPOTIFY INTEGRATION: Fetching objects without Spotify id...");
        var objectsToUpdateSpotifyId = spotifyIntegrationDAO.getObjectsWithoutSpotifyId(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Fetched " + objectsToUpdateSpotifyId.size() + " objects without Spotify id.");
        System.out.println(objectsToUpdateSpotifyId);
        objectsToUpdateSpotifyId.forEach(r -> {
            var response = r.getResponse();
            ObjectMapper mapper = new ObjectMapper();

            switch (r.getObjectType()) {
            case "ALBUM":
                try {
                    AlbumSearch albums = mapper.readValue(response, AlbumSearch.class);
                    List<AlbumItem> albumItemList = albums.getAlbums().getItems();
                    AlbumEntity albumEntity = albumDAO.findByPK(r.getObjectId());
                    if (!albumItemList.isEmpty()) {
                        AlbumItem firstResponse = albumItemList.get(0);
                        albumEntity.setSpotifyId(firstResponse.getId());
                    } else {
                        albumEntity.setSpotifyStatus("Done");
                    }
                    albumDAO.persist(albumEntity);
                } catch (JsonMappingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error mapping album response. " + e.getMessage());
                } catch (JsonProcessingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error processing album response. " + e.getMessage());
                }
                break;
            case "SONG":
                try {
                    TrackSearch tracks = mapper.readValue(response, TrackSearch.class);
                    List<TrackItem> trackItemList = tracks.getTracks().getItems();
                    SongEntity songEntity = songDAO.findByPK(r.getObjectId());
                    if (!trackItemList.isEmpty()) {
                        TrackItem firstResponse = trackItemList.get(0);
                        songEntity.setSpotifyId(firstResponse.getId());
                    }
                    songEntity.setSpotifyStatus("Done");

                    songDAO.persist(songEntity);
                } catch (JsonMappingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error mapping song response. " + e.getMessage());
                } catch (JsonProcessingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error processing song response. " + e.getMessage());
                }
                break;
            case "ARTIST":
                try {
                    ArtistSearch artists = mapper.readValue(response, ArtistSearch.class);
                    List<ArtistItem> artistItemList = artists.getArtists().getItems();
                    ArtistEntity artistEntity = artistDAO.findByPK(r.getObjectId());
                    if (!artistItemList.isEmpty()) {
                        ArtistItem firstResponse = artistItemList.get(0);
                        artistEntity.setSpotifyId(firstResponse.getId());
                    } else {
                        artistEntity.setSpotifyStatus("Done");
                    }
                    artistDAO.persist(artistEntity);
                } catch (JsonMappingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error mapping artist response. " + e.getMessage());
                } catch (JsonProcessingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error processing artist response. " + e.getMessage());
                }
                break;

            }
            LOGGER.info("SPOTIFY INTEGRATION: Updating Spotify ids done!");
        });
    }

}
