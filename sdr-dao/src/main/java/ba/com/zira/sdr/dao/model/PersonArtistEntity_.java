package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonArtistEntity.class)
public abstract class PersonArtistEntity_ {
    public static volatile SingularAttribute<PersonArtistEntity, PersonEntity> person;
    public static volatile SingularAttribute<PersonArtistEntity, Long> id;
    public static volatile SingularAttribute<PersonArtistEntity, ArtistEntity> artist;
}
