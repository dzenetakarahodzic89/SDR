package ba.com.zira.sdr.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import ba.com.zira.commons.configuration.BaseApplicationConfiguration;

@EnableEurekaClient
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "ba.com.zira.**.core.**" })
public class ApplicationConfiguration extends BaseApplicationConfiguration {

}
