package ba.com.zira.sdr.application;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

import ba.com.zira.sdr.configuration.ApplicationConfiguration;

public class SoundRepositoryLauncher implements Daemon {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundRepositoryLauncher.class);

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(final DaemonContext context) throws Exception {
        try {
            LOGGER.info("Initializing application");
            applicationContext = SpringApplication.run(ApplicationConfiguration.class);
        } catch (Exception e) {
            LOGGER.error("init => {}", e.getMessage(), e);
            if (context != null) {
                context.getController().shutdown();
            }
        }
    }

    @Override
    public void start() throws Exception {
        LOGGER.info("Application started successfully");
    }

    @Override
    public void stop() throws Exception {
        applicationContext.stop();
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroying application");
    }

    public static void main(final String[] args) {
        SpringApplication springApplication = new SpringApplication(ApplicationConfiguration.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

}
