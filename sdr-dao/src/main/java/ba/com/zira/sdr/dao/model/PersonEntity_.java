package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonEntity.class)
public abstract class PersonEntity_ {

    public static volatile SingularAttribute<PersonEntity, Long> id;
    public static volatile ListAttribute<PersonEntity, PersonArtistEntity> personArtists;
    public static volatile SingularAttribute<PersonEntity, String> name;
    public static volatile SingularAttribute<PersonEntity, CountryEntity> country;
}
