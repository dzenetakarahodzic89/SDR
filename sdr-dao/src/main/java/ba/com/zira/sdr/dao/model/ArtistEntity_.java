package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ArtistEntity.class)
public abstract class ArtistEntity_ {
    public static volatile SingularAttribute<ArtistEntity, Long> id;
    public static volatile SingularAttribute<ArtistEntity, String> name;
    public static volatile SingularAttribute<ArtistEntity, String> surname;
    public static volatile ListAttribute<ArtistEntity, PersonArtistEntity> personArtists;

}
