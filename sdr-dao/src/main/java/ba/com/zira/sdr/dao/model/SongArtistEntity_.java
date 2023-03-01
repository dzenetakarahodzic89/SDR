package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SongArtistEntity.class)
public abstract class SongArtistEntity_ {

    public static volatile SingularAttribute<SongArtistEntity, SongEntity> song;
    public static volatile SingularAttribute<SongArtistEntity, AlbumEntity> album;
    public static volatile SingularAttribute<SongArtistEntity, Long> id;
    public static volatile SingularAttribute<SongArtistEntity, ArtistEntity> artist;

}
