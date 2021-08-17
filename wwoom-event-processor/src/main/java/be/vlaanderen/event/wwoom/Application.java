package be.vlaanderen.event.wwoom;

import be.vlaanderen.burgerprofiel.wwoom.security.client.oauth.geosecure.GeoSecureClientConfig;
import be.vlaanderen.event.wwoom.web.NotificationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        GeoSecureClientConfig.class,
        NotificationController.class
})
public class Application {

    @Bean
    public ContextHolder contextHolder() {
        return new ContextHolder();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

