package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SongEntity.class)
public abstract class SongEntity_ {
    public static volatile SingularAttribute<SongEntity, SongEntity> remix;
    public static volatile SingularAttribute<SongEntity, SongEntity> cover;
    public static volatile SingularAttribute<SongEntity, Long> id;
    public static volatile ListAttribute<SongEntity, SongArtistEntity> songArtists;
    public static volatile SingularAttribute<SongEntity, String> name;
    public static volatile SingularAttribute<SongEntity, GenreEntity> genre;
    public static volatile SingularAttribute<SongEntity, Long> playtimeInSeconds;

}
