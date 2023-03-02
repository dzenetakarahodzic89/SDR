package ba.com.zira.sdr.core.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Tuple;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.generateplaylist.GeneratedPlaylistSong;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.song.Song;

@Mapper(componentModel = "spring")
public interface GeneratePlaylistMapper {
    default public Song stringToSongMapper(String songString) {
        Song song = new Song();
        if (songString == null) {
            return song;
        }
        var stringArray = songString.split(";;;;;");
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

    default public Genre stringToGenreMapper(String genreString) {
        if (genreString == null) {
            return new Genre();
        }
        var stringArray = genreString.split(";;;;;");
        Genre genre = new Genre(Long.parseLong(stringArray[0]), stringArray[1]);

        if (!stringArray[2].equals("-1") && !stringArray[3].equals("null")) {
            genre.setMainGenre(new Genre(Long.parseLong(stringArray[2]), stringArray[3]));
        }
        return genre;
    }

    default public ArtistResponse stringToArtistMapper(String artistString) {
        ArtistResponse artistResponse = new ArtistResponse();
        if (artistString == null) {
            return artistResponse;
        }
        var stringArray = artistString.split(";;;;;");
        artistResponse.setId(Long.parseLong(stringArray[0]));
        artistResponse.setName(stringArray[1]);
        artistResponse.setSurname(stringArray[2]);
        return artistResponse;
    }

    default public List<ArtistResponse> stringToArtistsMapper(String artistsString) {
        if (artistsString == null) {
            return new ArrayList<ArtistResponse>();
        }
        return Stream.of(artistsString.split(",,,,,")).map(this::stringToArtistMapper).collect(Collectors.toList());
    }

    default public AlbumResponse stringToAlbumMapper(String artistString) {
        AlbumResponse albumResponse = new AlbumResponse();
        if (artistString == null) {
            return albumResponse;
        }
        var stringArray = artistString.split(";;;;;");
        albumResponse.setId(Long.parseLong(stringArray[0]));
        albumResponse.setName(stringArray[1]);
        return albumResponse;
    }

    default public List<AlbumResponse> stringToAlbumsMapper(String albumsString) {
        if (albumsString == null) {
            return new ArrayList<AlbumResponse>();
        }
        return Stream.of(albumsString.split(",,,,,")).map(this::stringToAlbumMapper).collect(Collectors.toList());
    }

    default public CountryResponse stringToCountryMapper(String countryString) {
        CountryResponse countryResponse = new CountryResponse();
        if (countryString == null) {
            return countryResponse;
        }
        var stringArray = countryString.split(";;;;;");
        countryResponse.setId(Long.parseLong(stringArray[0]));
        countryResponse.setName(stringArray[1]);
        countryResponse.setFlagAbbriviation(stringArray[2]);
        countryResponse.setRegion(stringArray[3]);
        return countryResponse;
    }

    default public List<CountryResponse> stringToCountriesMapper(String countriesString) {
        if (countriesString == null) {
            return new ArrayList<CountryResponse>();
        }
        return Stream.of(countriesString.split(",,,,,")).map(this::stringToCountryMapper).collect(Collectors.toList());
    }

    default public List<GeneratedPlaylistSong> complexDbObjectsToGeneratedPlaylistSongs(List<Tuple> objects) {
        List<GeneratedPlaylistSong> generatedPlaylistSongs = new ArrayList<>();
        objects.forEach(record -> {
            generatedPlaylistSongs.add(new GeneratedPlaylistSong(stringToSongMapper((String) record.get(0)),
                    stringToGenreMapper((String) record.get(3)), stringToArtistsMapper((String) record.get(1)),
                    stringToAlbumsMapper((String) record.get(2)), stringToCountriesMapper((String) record.get(4))));
        });
        return generatedPlaylistSongs;
    }
}
