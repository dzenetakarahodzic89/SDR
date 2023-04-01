package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CountryEntity.class)
public abstract class CountryEntity_ {
    public static volatile SingularAttribute<CountryEntity, Long> id;
    public static volatile SingularAttribute<CountryEntity, String> name;
    public static volatile ListAttribute<CountryEntity, ReleaseCountryEntity> releaseCountries;
}
