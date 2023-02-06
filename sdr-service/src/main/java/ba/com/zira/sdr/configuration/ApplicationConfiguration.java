package ba.com.zira.sdr.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import ba.com.zira.commons.configuration.BaseApplicationConfiguration;

@EnableDiscoveryClient 
@SpringBootApplication
public class ApplicationConfiguration extends BaseApplicationConfiguration{
    
}
