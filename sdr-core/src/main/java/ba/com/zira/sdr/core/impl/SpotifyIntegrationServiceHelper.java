package ba.com.zira.sdr.core.impl;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.User;
import ba.com.zira.sdr.api.SpotifyIntegrationService;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;

@Service
public class SpotifyIntegrationServiceHelper {
    private final RestTemplate restTemplate;
    private AlbumDAO albumDAO;
    private SongDAO songDAO;
    private ArtistDAO artistDAO;
    private SpotifyIntegrationService spotifyIntegrationService;
    @Value("${spring.security.oauth2.client.registration.spotify.clientId}")
    private final String clientId;
    @Value("${spring.security.oauth2.client.registration.spotify.clientSecret}")
    private final String clientSecret;
    @Value("${spring.security.oauth2.client.registration.spotify.accessTokenUri}")
    private final String spotifyAuthUrl;
    @Value("${spring.security.oauth2.client.registration.spotify.apiUrl}")
    private final String spotifyApiUrl;
    @Value("${spring.security.oauth2.client.registration.spotify.responseLimit}")
    private final int responseLimit;
    private static final User systemUser = new User("System");

    SpotifyIntegrationServiceHelper(AlbumDAO albumDAO, SongDAO songDAO, ArtistDAO artistDAO,
            SpotifyIntegrationService spotifyIntegrationService,
            @Value("${spring.security.oauth2.client.registration.spotify.clientId}") String clientId,
            @Value("${spring.security.oauth2.client.registration.spotify.clientSecret}") String clientSecret,
            @Value("${spring.security.oauth2.client.registration.spotify.accessTokenUri}") String spotifyAuthUrl,
            @Value("${spring.security.oauth2.client.registration.spotify.apiUrl}") String spotifyApiUrl,
            @Value("${spring.security.oauth2.client.registration.spotify.responseLimit}") int responseLimit) {
        this.albumDAO = albumDAO;
        this.songDAO = songDAO;
        this.artistDAO = artistDAO;
        this.spotifyIntegrationService = spotifyIntegrationService;
        RestTemplateBuilder builder = new RestTemplateBuilder();
        this.restTemplate = builder.build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.responseLimit = responseLimit;
        this.spotifyApiUrl = spotifyApiUrl;
        this.spotifyAuthUrl = spotifyAuthUrl;
    }

    private String getAuthenticationToken() {
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
            var responseBody = response.getBody();
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = springParser.parseMap(responseBody);
            return (String) map.get("access_token");
        }

        return null;
    }

    private void fetchAlbumsFromSpotify(String token) {
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
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void fetchSongsFromSpotify(String token) {
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
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void fetchArtistsFromSpotify(String token) {
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
                try {
                    var entityRequest = new EntityRequest<>(spotifyIntegration);
                    entityRequest.setUser(systemUser);
                    spotifyIntegrationService.create(entityRequest);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Scheduled(fixedDelay = 600000)
    public void fetchDataFromSpotify() {
        String token = getAuthenticationToken();
        fetchAlbumsFromSpotify(token);
        fetchSongsFromSpotify(token);
        fetchArtistsFromSpotify(token);
    }

}
