package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistCreateRequest;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongPlaylistDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("songPlaylistRequestValidation")
public class SongPlaylistRequestValidation {

    private SongPlaylistDAO songPlaylistDAO;
    private SongDAO songDAO;
    private PlaylistDAO playlistDAO;
    private static final String DOES_NOT_EXIST = " does not exist!";

    public ValidationResponse validateCreateSongPlaylistRequest(final EntityRequest<SongPlaylistCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(songExists(request.getEntity().getSongId()));
        errors.put(playlistExists(request.getEntity().getPlaylistId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateUpdateSongPlaylistRequest(final EntityRequest<SongPlaylistUpdateRequest> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity().getId()));
        errors.put(songExists(request.getEntity().getSongId()));
        errors.put(playlistExists(request.getEntity().getPlaylistId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateExistsSongPlaylistRequest(final EntityRequest<Long> request) {
        ValidationErrors errors = new ValidationErrors();
        errors.put(exists(request.getEntity()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError exists(Long id) {
        if (!songPlaylistDAO.existsByPK(id)) {
            return ValidationError.of("SOngPlaylist_NOT_FOUND", "SongPlaylist with id: " + id + DOES_NOT_EXIST);
        }
        return null;
    }

    private ValidationError songExists(Long id) {
        if (!songDAO.existsByPK(id)) {
            return ValidationError.of("Song_NOT_FOUND", "Song with id: " + id + DOES_NOT_EXIST);
        }
        return null;
    }

    private ValidationError playlistExists(Long id) {
        if (!playlistDAO.existsByPK(id)) {
            return ValidationError.of("Playlist_NOT_FOUND", "Playlist with id: " + id + DOES_NOT_EXIST);
        }
        return null;
    }

}
