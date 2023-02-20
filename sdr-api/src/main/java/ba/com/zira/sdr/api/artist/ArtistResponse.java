package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import ba.com.zira.sdr.api.model.lov.LoV;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime dateOfBirth;
    private LocalDateTime dateOfDeath;
    private String information;
    private LocalDateTime modified;
    private String modifiedBy;
    private String status;
    private String surname;
    private String type;
    private List<LoV> personArtists;
    private List<LoV> songArtists;

    public ArtistResponse() {
    };

    public ArtistResponse(String name, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath, String information, String status,
            String surname, String type) {
        super();
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.information = information;
        this.status = status;
        this.surname = surname;
        this.type = type;
    }

    public ArtistResponse(Long id, String name, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath, String information, String status,
            String surname, String type) {
        super();
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.information = information;
        this.status = status;
        this.surname = surname;
        this.type = type;
    }

    public ArtistResponse(Long id, String name, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath, String information, String status,
            String surname, String type, List<LoV> personArtists, List<LoV> songArtists) {
        super();
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.information = information;
        this.status = status;
        this.surname = surname;
        this.type = type;
        this.personArtists = personArtists;
        this.songArtists = songArtists;
    }

}
