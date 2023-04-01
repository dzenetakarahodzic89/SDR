package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReleaseCountryEntity.class)
public class ReleaseCountryEntity_ {

    public static volatile SingularAttribute<ReleaseCountryEntity, Long> id;
    public static volatile SingularAttribute<ReleaseCountryEntity, ReleaseEntity> release;
    public static volatile SingularAttribute<ReleaseCountryEntity, CountryEntity> country;
    public static volatile SingularAttribute<ReleaseCountryEntity, Long> dateYear;
    public static volatile SingularAttribute<ReleaseCountryEntity, Long> dateMonth;
    public static volatile SingularAttribute<ReleaseCountryEntity, Long> dateDay;

}
