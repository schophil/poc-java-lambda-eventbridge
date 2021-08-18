package be.vlaanderen.event.wwoom;

import be.vlaanderen.burgerprofiel.wwoom.security.client.oauth.geosecure.GeoSecureClientConfig;
import be.vlaanderen.event.wwoom.applications.ApplicationRepository;
import be.vlaanderen.event.wwoom.applications.SimpleApplicationRepository;
import be.vlaanderen.event.wwoom.web.NotificationController;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    public ApplicationRepository applicationRepository(@Value("#{environment.PUBLIC_KEY}") String publicKey) {
        return new SimpleApplicationRepository(publicKey);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

