package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistUpdateRequest;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.SongArtistEntity;

@Mapper(componentModel = "spring", uses = { SongDAO.class, ArtistDAO.class, LabelDAO.class, AlbumDAO.class })
public interface SongArtistMapper {

    // @Mapping(source = "documentName", target = "docname")
    // SampleModelEntity dtoToEntity(SampleModelCreateRequest sampleModel);

    @Mapping(target = "song", source = "songId")
    @Mapping(target = "label", source = "labelId")
    @Mapping(target = "artist", source = "artistId")
    @Mapping(target = "album.id", source = "albumId")
    SongArtistEntity dtoToEntity(SongArtistCreateRequest songArtistCreateRequest);

    @Mapping(target = "artistId", source = "artist.id")
    @Mapping(target = "songId", source = "song.id")
    @Mapping(target = "labelId", source = "label.id")
    @Mapping(target = "albumId", source = "album.id")
    @Mapping(target = "artistName", source = "artist.name")
    @Mapping(target = "songName", source = "song.name")
    @Mapping(target = "labelName", source = "label.name")
    @Mapping(target = "albumName", source = "album.name")
    SongArtistResponse entityToDto(SongArtistEntity songArtistEntity);

    @Mapping(source = "songId", target = "song")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "albumId", target = "album")
    void updateEntity(SongArtistUpdateRequest songArtistUpdateRequest, @MappingTarget SongArtistEntity songArtistEntity);

    List<SongArtistResponse> entitiesToDtos(List<SongArtistEntity> sampleModelEntity);
}
