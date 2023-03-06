package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GeneratedSongViewEntity.class)
public abstract class GeneratedSongViewEntity_ {
    public static volatile SingularAttribute<GeneratedSongViewEntity, String> song;
    public static volatile SingularAttribute<GeneratedSongViewEntity, String> artists;
    public static volatile SingularAttribute<GeneratedSongViewEntity, String> genre;
    public static volatile SingularAttribute<GeneratedSongViewEntity, String> countries;
    public static volatile SingularAttribute<GeneratedSongViewEntity, String> albums;

    public static volatile SingularAttribute<GeneratedSongViewEntity, Long> coverId;
    public static volatile SingularAttribute<GeneratedSongViewEntity, Long> remixId;
    public static volatile SingularAttribute<GeneratedSongViewEntity, Long> genreId;
}