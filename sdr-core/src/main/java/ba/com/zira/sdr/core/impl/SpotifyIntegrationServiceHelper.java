package ba.com.zira.sdr.core.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;

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
import ba.com.zira.sdr.spotify.SpotifyAlbumItem;
import ba.com.zira.sdr.spotify.album.search.SpotifyAlbumSearch;
import ba.com.zira.sdr.spotify.album.songs.SpotifyAlbumsTrackItem;
import ba.com.zira.sdr.spotify.album.songs.SpotifyGetAlbumsSongs;
import ba.com.zira.sdr.spotify.artist.albums.SpotifyGetArtistsAlbums;
import ba.com.zira.sdr.spotify.artist.search.SpotifyArtistItem;
import ba.com.zira.sdr.spotify.artist.search.SpotifyArtistSearch;
import ba.com.zira.sdr.spotify.song.search.SpotifyTrackItem;
import ba.com.zira.sdr.spotify.song.search.SpotifyTrackSearch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * The Class SpotifyIntegrationServiceHelper.
 */
@Service

/**
 * Instantiates a new spotify integration service helper.
 *
 * @param restTemplate
 *            the rest template
 * @param albumDAO
 *            the album DAO
 * @param songDAO
 *            the song DAO
 * @param artistDAO
 *            the artist DAO
 * @param labelDAO
 *            the label DAO
 * @param genreDAO
 *            the genre DAO
 * @param songArtistDAO
 *            the song artist DAO
 * @param spotifyIntegrationService
 *            the spotify integration service
 * @param spotifyIntegrationDAO
 *            the spotify integration DAO
 * @param multiSearchDAO
 *            the multi search DAO
 */

/**
 * Instantiates a new spotify integration service helper.
 *
 * @param restTemplate
 *            the rest template
 * @param albumDAO
 *            the album DAO
 * @param songDAO
 *            the song DAO
 * @param artistDAO
 *            the artist DAO
 * @param labelDAO
 *            the label DAO
 * @param genreDAO
 *            the genre DAO
 * @param songArtistDAO
 *            the song artist DAO
 * @param spotifyIntegrationService
 *            the spotify integration service
 * @param spotifyIntegrationDAO
 *            the spotify integration DAO
 * @param multiSearchDAO
 *            the multi search DAO
 */
@RequiredArgsConstructor
public class SpotifyIntegrationServiceHelper {

    private static final String SEARCH_Q = "/search?q=";

    private static final String STATUS_ACTIVE = "Active";

    /** The Constant SPOTIFY_STATUS_DONE. */
    private static final String SPOTIFY_STATUS_DONE = "Done";

    /** The rest template. */
    @Autowired
    private final RestTemplate restTemplate;

    /** The album DAO. */
    @NonNull
    private AlbumDAO albumDAO;

    /** The song DAO. */
    @NonNull
    private SongDAO songDAO;

    /** The artist DAO. */
    @NonNull
    private ArtistDAO artistDAO;

    /** The label DAO. */
    @NonNull
    private LabelDAO labelDAO;

    /** The genre DAO. */
    @NonNull
    private GenreDAO genreDAO;

    /** The song artist DAO. */
    @NonNull
    private SongArtistDAO songArtistDAO;

    /** The spotify integration service. */
    @NonNull
    private SpotifyIntegrationService spotifyIntegrationService;

    /** The spotify integration DAO. */
    @NonNull
    private SpotifyIntegrationDAO spotifyIntegrationDAO;

    /** The multi search DAO. */
    @NonNull
    MultiSearchDAO multiSearchDAO;

    /** The client id. */
    @Value("${spring.security.oauth2.client.registration.spotify.clientId}")
    private String clientId;

    /** The client secret. */
    @Value("${spring.security.oauth2.client.registration.spotify.clientSecret}")
    private String clientSecret;

    /** The spotify auth url. */
    @Value("${spring.security.oauth2.client.registration.spotify.accessTokenUri}")
    private String spotifyAuthUrl;

    /** The spotify api url. */
    @Value("${spring.security.oauth2.client.registration.spotify.apiUrl}")
    private String spotifyApiUrl;

    /** The response limit. */
    @Value("${spring.security.oauth2.client.registration.spotify.responseLimit}")
    private int responseLimit;

    @Value("${spring.security.oauth2.client.registration.spotify.disabled:true}")
    private Boolean integrationDisabled;

    /** The Constant systemUser. */
    private static final User systemUser = new User("Spotify Integration");

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyIntegrationServiceHelper.class);

    /** The Constant AUTHORIZATION. */
    private static final String AUTHORIZATION = "Authorization";

    /** The map of existing spotify ids. */
    private Map<String, MultiSearchResponse> mapOfExistingSpotifyIds = new HashMap<>();

    private N2bObjectMapper mapper = new N2bObjectMapper();

    /**
     * Gets the http entity.
     *
     * @param token
     *            the token
     * @return the http entity
     */
    private HttpEntity<?> getHttpEntity(String token) {
        var headers = new HttpHeaders();
        headers.set(AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }

    /**
     * Gets the authentication token.
     *
     * @return the authentication token
     */
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

    /**
     * Fetch albums from spotify. Finds albums not present in spotify_int table
     * which do not have a set spotify_id and sends a search request to Spotify
     * API and saves the API response to spotify_int table. The number of albums
     * processed is limited by responseLimit property.
     *
     * @param token
     *            the token
     *
     *
     */
    private void fetchAlbumsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for albums on Spotify...");
        List<LoV> albums = albumDAO.findAlbumsToFetchFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} albums to fetch from Spotify.", albums.size());
        albums.forEach(album -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(album.getName());
            spotifyIntegration.setObjectId(album.getId());
            spotifyIntegration.setObjectType(ObjectType.ALBUM.getValue());

            var url = spotifyApiUrl + SEARCH_Q + album.getName() + "&type=album&limit=" + responseLimit;

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

    /**
     * Fetch songs from spotify. Finds songs not present in spotify_int table
     * which do not have a set spotify_id and sends a search request to Spotify
     * API and saves the API response to spotify_int table. The number of songs
     * processed is limited by responseLimit property.
     *
     * @param token
     *            the token
     *
     *
     */
    private void fetchSongsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for songs on Spotify...");
        List<LoV> songs = songDAO.findSongsToFetchFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} songs to fetch from Spotify.", songs.size());
        songs.forEach(song -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(song.getName());
            spotifyIntegration.setObjectId(song.getId());
            spotifyIntegration.setObjectType(ObjectType.SONG.getValue());

            var url = spotifyApiUrl + SEARCH_Q + song.getName() + "&type=track&limit=" + responseLimit;

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

    /**
     * Fetch artists from spotify. Finds artists not present in spotify_int
     * table which do not have a set spotify_id and sends a search request to
     * Spotify API and saves the API response to spotify_int table. The number
     * of artists processed is limited by responseLimit property.
     *
     * @param token
     *            the token
     *
     *
     */
    private void fetchArtistsFromSpotify(String token) {
        LOGGER.info("SPOTIFY INTEGRATION: Searching for artists on Spotify...");
        List<LoV> artists = artistDAO.findArtistsToFetchFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} artists to fetch from Spotify.", artists.size());
        artists.forEach(artist -> {
            var spotifyIntegration = new SpotifyIntegrationCreateRequest();
            spotifyIntegration.setName(artist.getName());
            spotifyIntegration.setObjectId(artist.getId());
            spotifyIntegration.setObjectType(ObjectType.ARTIST.getValue());

            var url = spotifyApiUrl + SEARCH_Q + artist.getName() + "&type=artist&limit=" + responseLimit;

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

    /**
     * Update spotify id.
     *
     * Finds objects present in spotify_int table which do not have a set
     * spotify_id and uses the API response to set their spotify_id. The number
     * of objects processed is limited by responseLimit property.
     */
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
                updateSongSpotifyId(r.getResponse(), r.getName(), r.getObjectId());
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

    /**
     * Update album spotify id. Sets spotify_id of an album based on the Spotify
     * API response. If the API response contains no data, the spotify_status of
     * the album is marked as done.
     *
     * @param response
     *            the response
     * @param objectId
     *            the object id
     *
     *
     */
    private void updateAlbumSpotifyId(String response, Long objectId) {
        try {
            SpotifyAlbumSearch albums = mapper.readValue(response, SpotifyAlbumSearch.class);
            List<SpotifyAlbumItem> albumItemList = albums.getAlbums().getItems();
            var albumEntity = albumDAO.findByPK(objectId);
            if (!albumItemList.isEmpty()) {
                SpotifyAlbumItem firstResponse = albumItemList.get(0);
                albumEntity.setSpotifyId(firstResponse.getId());
            } else {
                albumEntity.setSpotifyStatus(SPOTIFY_STATUS_DONE);
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

    /**
     * Update song spotify id. Sets spotify_id of a song based on the Spotify
     * API response. If the song is tied to an artist in the database, it is
     * marked as done.
     *
     * @param response
     *            the response
     * @param query
     *            the query
     * @param objectId
     *            the object id
     *
     *
     */
    private void updateSongSpotifyId(String response, String query, Long objectId) {
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
            if (query.contains("artist")) {
                songEntity.setSpotifyStatus(SPOTIFY_STATUS_DONE);
            }

            songDAO.merge(songEntity);
        } catch (JsonMappingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error mapping song response. Exception message: {}", e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error("SPOTIFY INTEGRATION: Error processing song response. Exception message: {}", e.getMessage());
        }
    }

    /**
     * Update artist spotify id. Sets spotify_id of an artist based on the
     * Spotify API response. If the API response contains no data, the
     * spotify_status of the artist is marked as done.
     *
     * @param response
     *            the response
     * @param objectId
     *            the object id
     *
     *
     */
    private void updateArtistSpotifyId(String response, Long objectId) {
        try {
            SpotifyArtistSearch artists = mapper.readValue(response, SpotifyArtistSearch.class);
            List<SpotifyArtistItem> artistItemList = artists.getArtists().getItems();
            var artistEntity = artistDAO.findByPK(objectId);
            if (!artistItemList.isEmpty()) {
                SpotifyArtistItem firstResponse = artistItemList.get(0);
                artistEntity.setSpotifyId(firstResponse.getId());
            } else {
                artistEntity.setSpotifyStatus(SPOTIFY_STATUS_DONE);
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

    /**
     * Parses the date.
     *
     * @param date
     *            the date
     * @return the local date time
     */
    private LocalDateTime parseDate(String date) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter).atStartOfDay();
        } catch (Exception e) {
            return LocalDate.parse(date.concat("-01-01"), formatter).atStartOfDay();
        }
    }

    /**
     * Fetch songs of album from spotify. Returns the response of a Spotify API
     * get request for all the tracks tied to a specific album, based on the
     * album spotify_id.
     *
     * @param albumSpotifyId
     *            the album spotify id
     * @param albumName
     *            the album name
     * @return the string
     *
     *
     */
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

    /**
     * Save song artist.
     *
     * @param album
     *            the album
     * @param artist
     *            the artist
     * @param song
     *            the song
     * @param label
     *            the label
     *
     */
    private void saveSongArtist(AlbumEntity album, ArtistEntity artist, SongEntity song, LabelEntity label) {
        var songArtistEntity = new SongArtistEntity();
        songArtistEntity.setAlbum(album);
        songArtistEntity.setArtist(artist);
        songArtistEntity.setSong(song);
        songArtistEntity.setCreated(LocalDateTime.now());
        songArtistEntity.setCreatedBy(systemUser.getUserId());
        songArtistEntity.setLabel(label);
        songArtistEntity.setStatus(STATUS_ACTIVE);
        songArtistDAO.persist(songArtistEntity);
    }

    /**
     * Save songs. Takes a list of song data fetched via Spotify API and creates
     * and saves a record to song table for each song which is not already
     * present in the database based on its spotify_id. Spotify_ids of newly
     * created songs are saved to the global map to prevent duplicate records.
     * Each newly created song is tied to the album and artists passed to the
     * function via the song_artist table, therefore its spotify_status is
     * marked as done.
     *
     * @param songs
     *            the songs
     * @param album
     *            the album
     * @param artists
     *            the artists
     * @param label
     *            the label
     * @param genre
     *            the genre
     * @param releaseDate
     *            the release date
     *
     *
     */
    private void saveSongs(List<SpotifyAlbumsTrackItem> songs, AlbumEntity album, List<ArtistEntity> artists, LabelEntity label,
            GenreEntity genre, LocalDateTime releaseDate) {
        songs.forEach(song -> {
            if (mapOfExistingSpotifyIds.get(song.getId()) == null) {
                var durationInMs = song.getDurationMs();
                var songEntity = new SongEntity();
                songEntity.setName(song.getName());
                songEntity.setSpotifyId(song.getId());
                songEntity.setSpotifyStatus(SPOTIFY_STATUS_DONE);
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
                        try {
                            saveSongArtist(album, artist, songEntity, label);
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

    /**
     * Adds the songs from spotify for album. Based on the Spotify API response
     * for a search request of an album, saves songs of the album to the
     * database and ties the album and artists with the songs.
     *
     * @param album
     *            the album
     * @param artists
     *            the artists
     * @param label
     *            the label
     *
     *
     */
    private void addSongsFromSpotifyForAlbum(AlbumEntity album, List<ArtistEntity> artists, LabelEntity label) {
        try {
            SpotifyGetAlbumsSongs response = mapper.readValue(fetchSongsOfAlbumFromSpotify(album.getSpotifyId(), album.getName()),
                    SpotifyGetAlbumsSongs.class);

            GenreEntity genre = null;
            if (!response.getGenres().isEmpty()) {
                var genreFound = response.getGenres().stream().filter(g -> genreDAO.findByName(g) != null).findFirst();

                if (genreFound.isPresent()) {
                    genre = genreDAO.findByName(genreFound.get());
                } else {
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

    /**
     * Save artist from spotify. Saves and returns a new artist to the database
     * if it is not already present based on its spotify_id, otherwise finds the
     * existing artist and returns it. If a new artist is created, its
     * spotify_id is saved to the global map to prevent duplicate records.
     *
     * @param artistName
     *            the artist name
     * @param artistSpotifyId
     *            the artist spotify id
     * @param spotifyStatus
     *            the spotify status
     * @return the artist entity
     *
     *
     */
    private ArtistEntity saveArtistFromSpotify(String artistName, String artistSpotifyId, String spotifyStatus) {
        if (mapOfExistingSpotifyIds.get(artistSpotifyId) == null) {
            ArtistEntity artistEntity = new ArtistEntity();
            artistEntity.setName(artistName);
            artistEntity.setStatus(STATUS_ACTIVE);
            artistEntity.setCreated(LocalDateTime.now());
            artistEntity.setCreatedBy(systemUser.getUserId());
            artistEntity.setSpotifyId(artistSpotifyId);
            artistEntity.setSpotifyStatus(spotifyStatus);
            artistDAO.persist(artistEntity);
            mapOfExistingSpotifyIds.put(artistSpotifyId,
                    new MultiSearchResponse(artistName, ObjectType.ARTIST.getValue(), artistSpotifyId));
            return artistEntity;
        } else {
            return artistDAO.getArtistBySpotifyId(artistSpotifyId);
        }
    }

    /**
     * Adds the songs from spotify for albums.
     *
     * Finds albums with a set spotify_id and spotify_status not marked as done.
     * If an album has no records in the song_artist table, its artists are
     * created and saved to the database based on the data provided by Spotify
     * API for that album, otherwise the artists of the album are fetched from
     * the database. The artists are passed to the auxiliary function which adds
     * songs of the album based on the data provided by Spotify API for that
     * album. The spotify_status of each album is marked as done, since all of
     * its songs are fetched from Spotify and saved to the database. The number
     * of albums processed is specified by responseLimit property.
     */
    private void addSongsFromSpotifyForAlbums() {
        LabelEntity defaultLabel = labelDAO.findByPK(0L);
        var albums = albumDAO.findAlbumsToFetchSongsFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} albums to fetch songs for!", albums.size());

        albums.forEach(album -> {
            LOGGER.info("SPOTIFY INTEGRATION: Fetching songs for album {}...", album.getName());
            List<ArtistEntity> artists = artistDAO.artistsByAlbum(album.getId());
            if (artists.isEmpty()) {
                // Add artists for album
                var spotifyArtists = new ArrayList<ArtistEntity>();
                try {
                    SpotifyAlbumItem spotifyResponse = mapper
                            .readValue(spotifyIntegrationDAO.getResponseByObjectIdAndObjectType(album.getId(), ObjectType.ALBUM.getValue()),
                                    SpotifyAlbumSearch.class)
                            .getAlbums().getItems().get(0);
                    spotifyResponse.getArtists().forEach(artist -> {
                        var artistEntity = saveArtistFromSpotify(artist.getName(), artist.getId(), SPOTIFY_STATUS_DONE);
                        spotifyArtists.add(artistEntity);
                    });

                } catch (JsonMappingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error mapping Album search response! Exception message: {}", e.getMessage());
                } catch (JsonProcessingException e) {
                    LOGGER.error("SPOTIFY INTEGRATION: Error processing Album search response! Exception message: {}", e.getMessage());
                }
                artists = spotifyArtists;
            }
            addSongsFromSpotifyForAlbum(album, artists, defaultLabel);
            album.setSpotifyStatus(SPOTIFY_STATUS_DONE);
            album.setModified(LocalDateTime.now());
            album.setModifiedBy(systemUser.getUserId());
            albumDAO.merge(album);
            LOGGER.info("SPOTIFY INTEGRATION: Successfully added songs for album {}!", album.getName());
        });
    }

    /**
     * Fetch albums of artist from spotify. Returns the response of a Spotify
     * API get request for all the albums tied to a specific artist, based on
     * the artist spotify_id.
     *
     * @param artistSpotifyId
     *            the artist spotify id
     * @param artistName
     *            the artist name
     * @return the string
     *
     *
     */
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

    /**
     * Save album. Creates and saves a new album to the database if it is not
     * already present based on its spotify_id. Auxiliary function is used to
     * add all songs of the album based on the data provided by Spotify API and
     * to tie the songs to the album and its artists, therefore the
     * spotify_status of the album is marked as done.
     *
     * @param albumName
     *            the album name
     * @param albumSpotifyId
     *            the album spotify id
     * @param releaseDate
     *            the release date
     * @param artists
     *            the artists
     * @param label
     *            the label
     * @return the album entity
     *
     *
     */
    private AlbumEntity saveAlbum(String albumName, String albumSpotifyId, String releaseDate, List<ArtistEntity> artists,
            LabelEntity label) {
        if (mapOfExistingSpotifyIds.get(albumSpotifyId) == null) {

            var albumEntity = new AlbumEntity();
            albumEntity.setName(albumName);
            albumEntity.setSpotifyId(albumSpotifyId);
            albumEntity.setSpotifyStatus(SPOTIFY_STATUS_DONE);
            albumEntity.setDateOfRelease(parseDate(releaseDate));
            albumEntity.setCreated(LocalDateTime.now());
            albumEntity.setCreatedBy(systemUser.getUserId());
            albumEntity.setStatus(STATUS_ACTIVE);
            mapOfExistingSpotifyIds.put(albumSpotifyId, new MultiSearchResponse(albumName, ObjectType.ALBUM.getValue(), albumSpotifyId));
            try {
                albumDAO.persist(albumEntity);
                addSongsFromSpotifyForAlbum(albumEntity, artists, label);
                albumEntity.setSpotifyStatus(SPOTIFY_STATUS_DONE);
                albumEntity.setModified(LocalDateTime.now());
                albumEntity.setModifiedBy(systemUser.getUserId());
                albumDAO.merge(albumEntity);
                LOGGER.info("SPOTIFY INTEGRATION: Successfully saved album {} to database!", albumName);
                return albumEntity;
            } catch (Exception e) {
                LOGGER.error("SPOTIFY INTEGRATION: Saving new album to database failed! Exception message: {}", e.getMessage());
                return null;
            }
        } else {
            LOGGER.info("Album {} has already been integrated.", albumName);
            return albumDAO.getAlbumBySpotifyId(albumSpotifyId);
        }

    }

    /**
     * Save albums.
     *
     * @param albums
     *            the albums
     * @param artist
     *            the artist
     * @param label
     *            the label
     */
    private void saveAlbums(List<SpotifyAlbumItem> albums, ArtistEntity artist, LabelEntity label) {
        albums.forEach(album -> {
            List<ArtistEntity> artists = new ArrayList<>();
            artists.add(artist);
            saveAlbum(album.getName(), album.getId(), album.getReleaseDate(), artists, label);
        });

    }

    /**
     * Adds the albums from spotify for artist.
     *
     * Finds artists with a set spotify_id and spotify_status not marked as done
     * and sends a get request for them based on the spotify_id to Spotify API.
     * The API response is used to add albums of the artists along with their
     * songs to the database. Spotify_status of each artist is marked as done.
     * The number of artists processed is specified by responseLimit property.
     */
    private void addAlbumsFromSpotifyForArtist() {
        LabelEntity defaultLabel = labelDAO.findByPK(0L);
        var artists = artistDAO.findArtistsToFetchAlbumsFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} artists to fetch albums for!", artists.size());

        artists.forEach(artist -> {
            var artistName = artist.getName();
            if (artist.getSurname() != null) {
                artistName += " " + artist.getSurname();
            }
            LOGGER.info("Fetching albums for artist {}...", artistName);
            try {
                SpotifyGetArtistsAlbums response = mapper.readValue(fetchAlbumsOfArtistFromSpotify(artist.getSpotifyId(), artistName),
                        SpotifyGetArtistsAlbums.class);

                saveAlbums(response.getItems(), artist, defaultLabel);
                artist.setSpotifyStatus(SPOTIFY_STATUS_DONE);
                artist.setModified(LocalDateTime.now());
                artist.setModifiedBy(systemUser.getUserId());
                artistDAO.merge(artist);
                LOGGER.info("Successfully added albums for artist {}!", artistName);
            } catch (JsonMappingException e) {
                LOGGER.error("SPOTIFY INTEGRATION: Error mapping GetAlbum response! Exception message: {}", e.getMessage());
            } catch (JsonProcessingException e) {
                LOGGER.error("SPOTIFY INTEGRATION: Error processing GetAlbum response! Exception message: {}", e.getMessage());
            }
        });
    }

    /**
     * Adds the albums and artists from spotify for songs.
     *
     * Finds songs with set spotify_id which have no records in the song_artist
     * table and adds artists and album of each song to the database based on
     * the data provided by Spotify API. Spotify_status of each song is marked
     * as done.
     */
    public void addAlbumsAndArtistsFromSpotifyForSongs() {
        LabelEntity defaultLabel = labelDAO.findByPK(0L);
        var songs = songDAO.findSongsToFetchArtistsAndAlbumFromSpotify(responseLimit);
        LOGGER.info("SPOTIFY INTEGRATION: Found {} songs to fetch artist and album for!", songs.size());

        songs.forEach(song -> {
            LOGGER.info("SPOTIFY INTEGRATION: Fetching album and artists for song {}...", song.getName());
            var artists = new ArrayList<ArtistEntity>();
            try {
                SpotifyTrackItem spotifyResponse = mapper
                        .readValue(spotifyIntegrationDAO.getResponseByObjectIdAndObjectType(song.getId(), "SONG"), SpotifyTrackSearch.class)
                        .getTracks().getItems().get(0);
                spotifyResponse.getArtists().forEach(artist -> {
                    var artistEntity = saveArtistFromSpotify(artist.getName(), artist.getId(), SPOTIFY_STATUS_DONE);
                    artists.add(artistEntity);
                });

                var album = spotifyResponse.getAlbum();
                var albumEntity = saveAlbum(album.getName(), album.getId(), album.getReleaseDate(), artists, defaultLabel);

                artists.forEach(artist -> {
                    try {
                        saveSongArtist(albumEntity, artist, song, defaultLabel);
                    } catch (Exception e) {
                        LOGGER.error("SPOTIFY INTEGRATION: Error saving SongArtistEntity for song {} and artist {}. Exception message: {}",
                                song.getName(), artist.getName(), e.getMessage());
                    }
                });

                song.setSpotifyStatus(SPOTIFY_STATUS_DONE);
                song.setModified(LocalDateTime.now());
                song.setModifiedBy(systemUser.getUserId());
                songDAO.merge(song);
            } catch (JsonMappingException e) {
                LOGGER.error("SPOTIFY INTEGRATION: Error mapping Track search response! Exception message: {}", e.getMessage());
            } catch (JsonProcessingException e) {
                LOGGER.error("SPOTIFY INTEGRATION: Error processing Track search response! Exception message: {}", e.getMessage());
            }
            LOGGER.info("SPOTIFY INTEGRATION: Successfully added album and artists for song {}!", song.getName());
        });

    }

    /**
     * Removes the duplicates.
     */
    private void removeDuplicates() {
        var duplicateAlbums = albumDAO.getDuplicateAlbums().stream().map(AlbumEntity::getId).collect(Collectors.toList());
        var duplicateArtists = artistDAO.getDuplicateArtists().stream().map(ArtistEntity::getId).collect(Collectors.toList());
        var duplicateSongs = songDAO.getDuplicateSongs().stream().map(SongEntity::getId).collect(Collectors.toList());

        LOGGER.info("SPOTIFY INTEGRATION: Found {} duplicate albums, {} duplicate songs and {} duplicate artists. Removing duplicates...",
                duplicateAlbums.size(), duplicateSongs.size(), duplicateArtists.size());
        songArtistDAO.removeDuplicateAlbums(duplicateAlbums);
        albumDAO.deleteAlbums(duplicateAlbums);

        songArtistDAO.removeDuplicateSongs(duplicateSongs);
        songDAO.deleteSongs(duplicateSongs);

        songArtistDAO.removeDuplicateArtists(duplicateArtists);
        artistDAO.deleteArtists(duplicateArtists);
    }

    /**
     * Fetch data from spotify.
     */
    @Scheduled(fixedDelayString = "${spring.security.oauth2.client.registration.spotify.taskDelay}")
    public void fetchDataFromSpotify() {
        if (Boolean.TRUE.equals(integrationDisabled)) {
            LOGGER.info("Spotify integration disabled!");
            return;
        }
        LOGGER.info("SPOTIFY INTEGRATION: Scheduled search started");
        String token = getAuthenticationToken();
        fetchAlbumsFromSpotify(token);
        fetchSongsFromSpotify(token);
        fetchArtistsFromSpotify(token);
    }

    /**
     * Update with data from spotify.
     */
    @Scheduled(fixedDelayString = "${spring.security.oauth2.client.registration.spotify.taskDelay}", initialDelay = 300000)
    public void updateWithDataFromSpotify() {
        if (Boolean.TRUE.equals(integrationDisabled)) {
            LOGGER.info("Spotify integration disabled!");
            return;
        }
        LOGGER.info("SPOTIFY INTEGRATION: Scheduled data update started");
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        updateSpotifyId();
        multiSearchDAO.deleteTable();
        multiSearchDAO.createTableAndFillWithData();
        mapOfExistingSpotifyIds = multiSearchDAO.getCurrentSpotifyIds();
        addSongsFromSpotifyForAlbums();
        addAlbumsFromSpotifyForArtist();
        addAlbumsAndArtistsFromSpotifyForSongs();
        removeDuplicates();
    }

}
