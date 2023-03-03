package ba.com.zira.sdr.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.generateplaylist.GeneratedPlaylistSong;
import ba.com.zira.sdr.api.model.generateplaylist.GeneratedPlaylistSongDbResponse;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.song.Song;

@Service
public class GeneratePlaylistServiceHelper {
    private static final String PROPERTY_DELIMITER = ";;;;;";
    private static final String OBJECT_DELIMITER = ",,,,,";

    Song stringToSongMapper(String songString) {
        var song = new Song();
        if (songString == null) {
            return song;
        }
        var stringArray = songString.split(PROPERTY_DELIMITER);
        song.setId(Long.parseLong(stringArray[0]));
        song.setName(stringArray[1]);
        song.setPlaytime(stringArray[2]);
        if (!stringArray[3].equals("-1")) {
            song.setCoverId(Long.parseLong(stringArray[3]));
        }
        if (!stringArray[4].equals("-1")) {
            song.setRemixId(Long.parseLong(stringArray[4]));
        }
        return song;
    }

    Genre stringToGenreMapper(String genreString) {
        if (genreString == null) {
            return new Genre();
        }
        var stringArray = genreString.split(PROPERTY_DELIMITER);
        var genre = new Genre(Long.parseLong(stringArray[0]), stringArray[1]);

        if (!stringArray[2].equals("-1") && !stringArray[3].equals("null")) {
            genre.setMainGenre(new Genre(Long.parseLong(stringArray[2]), stringArray[3]));
        }
        return genre;
    }

    ArtistResponse stringToArtistMapper(String artistString) {
        var artistResponse = new ArtistResponse();
        if (artistString == null) {
            return artistResponse;
        }
        var stringArray = artistString.split(PROPERTY_DELIMITER);
        artistResponse.setId(Long.parseLong(stringArray[0]));
        artistResponse.setName(stringArray[1]);
        artistResponse.setSurname(stringArray[2]);
        return artistResponse;
    }

    List<ArtistResponse> stringToArtistsMapper(String artistsString) {
        if (artistsString == null) {
            return new ArrayList<>();
        }
        return Stream.of(artistsString.split(OBJECT_DELIMITER)).map(this::stringToArtistMapper).collect(Collectors.toList());
    }

    AlbumResponse stringToAlbumMapper(String artistString) {
        var albumResponse = new AlbumResponse();
        if (artistString == null) {
            return albumResponse;
        }
        var stringArray = artistString.split(PROPERTY_DELIMITER);
        albumResponse.setId(Long.parseLong(stringArray[0]));
        albumResponse.setName(stringArray[1]);
        return albumResponse;
    }

    List<AlbumResponse> stringToAlbumsMapper(String albumsString) {
        if (albumsString == null) {
            return new ArrayList<>();
        }
        return Stream.of(albumsString.split(OBJECT_DELIMITER)).map(this::stringToAlbumMapper).collect(Collectors.toList());
    }

    CountryResponse stringToCountryMapper(String countryString) {
        var countryResponse = new CountryResponse();
        if (countryString == null) {
            return countryResponse;
        }
        var stringArray = countryString.split(PROPERTY_DELIMITER);
        countryResponse.setId(Long.parseLong(stringArray[0]));
        countryResponse.setName(stringArray[1]);
        countryResponse.setFlagAbbriviation(stringArray[2]);
        countryResponse.setRegion(stringArray[3]);
        return countryResponse;
    }

    List<CountryResponse> stringToCountriesMapper(String countriesString) {
        if (countriesString == null) {
            return new ArrayList<>();
        }
        return Stream.of(countriesString.split(OBJECT_DELIMITER)).map(this::stringToCountryMapper).collect(Collectors.toList());
    }

    List<GeneratedPlaylistSong> complexDbObjectsToGeneratedPlaylistSongs(List<GeneratedPlaylistSongDbResponse> responses) {
        List<GeneratedPlaylistSong> generatedPlaylistSongs = new ArrayList<>();
        responses.forEach(response -> generatedPlaylistSongs.add(new GeneratedPlaylistSong(stringToSongMapper(response.getSong()),
                stringToGenreMapper(response.getGenre()), stringToArtistsMapper(response.getArtists()),
                stringToAlbumsMapper(response.getAlbums()), stringToCountriesMapper(response.getCountries()))));
        return generatedPlaylistSongs;
    }
}
