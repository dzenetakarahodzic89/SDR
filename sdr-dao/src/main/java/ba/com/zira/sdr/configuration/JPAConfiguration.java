package ba.com.zira.sdr.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = { "ba.com.zira.sdr.dao.model" })
public class JPAConfiguration {

}
