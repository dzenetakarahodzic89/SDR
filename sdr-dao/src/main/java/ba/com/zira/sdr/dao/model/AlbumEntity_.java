package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AlbumEntity.class)
public abstract class AlbumEntity_ {

    public static volatile ListAttribute<AlbumEntity, SongArtistEntity> songArtists;
    public static volatile SingularAttribute<AlbumEntity, EraEntity> era;
    public static volatile SingularAttribute<AlbumEntity, Long> id;
    public static volatile SingularAttribute<AlbumEntity, String> name;
    public static volatile SingularAttribute<AlbumEntity, LocalDateTime> modified;

}
