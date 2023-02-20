package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("songArtistRequestValidation")
public class SongArtistRequestValidation {
    private SongArtistDAO songArtistDAO;
    private SongDAO songDAO;
    private AlbumDAO albumDAO;
    private LabelDAO labelDAO;
    private ArtistDAO artistDAO;

    public ValidationResponse validateCreateSongArtistRequest(final EntityRequest<SongArtistCreateRequest> entityRequest) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(exists(albumDAO, entityRequest.getEntity().getAlbumId(), "ALBUM_FK_NOT_FOUND", "Album"));

        errors.put(exists(labelDAO, entityRequest.getEntity().getLabelId(), "LABEL_FK_NOT_FOUND", "Label"));

        errors.put(exists(artistDAO, entityRequest.getEntity().getArtistId(), "ARTIST_FK_NOT_FOUND", "Artist"));

        errors.put(exists(songDAO, entityRequest.getEntity().getSongId(), "SONG_FK_NOT_FOUND", "Song"));

        return ValidationResponse.of(entityRequest, errors);
    }

    public ValidationResponse validateDeleteSongArtistRequest(final EntityRequest<Long> entityRequest) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(songArtistDAO, entityRequest.getEntity(), "SONG_ARTIST_NOT_FOUND", "Song-Artist"));

        return ValidationResponse.of(entityRequest, errors);
    }

    private ValidationError exists(AbstractDAO<?, Long> dao, Long id, String errorName, String tableName) {
        if (!dao.existsByPK(id)) {
            return ValidationError.of(errorName, tableName + " with id: " + id + " does not exist!");
        }
        return null;
    }

}
