package be.vlaanderen.event.wwoom;

import be.vlaanderen.burgerprofiel.wwoom.security.client.oauth.geosecure.GeoSecure;
import be.vlaanderen.burgerprofiel.wwoom.security.client.oauth.geosecure.GeoSecureClientConfig;
import be.vlaanderen.event.wwoom.applications.ApplicationRepository;
import be.vlaanderen.event.wwoom.applications.SimpleApplicationRepository;
import be.vlaanderen.event.wwoom.function.NotificationFunction;
import be.vlaanderen.event.wwoom.model.NotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.function.Function;

@SpringBootApplication
@Import({
        GeoSecureClientConfig.class
})
public class Application {

    @Bean
    public ApplicationRepository applicationRepository(@Value("#{environment.PUBLIC_KEY}") String publicKey) {
        return new SimpleApplicationRepository(publicKey);
    }

    @Bean
    public Function<NotificationEvent, String> notificationProcessor(GeoSecure geoSecure, ObjectMapper objectMapper, ApplicationRepository applicationRepository) {
        return new NotificationFunction(geoSecure, objectMapper, applicationRepository);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration legalLoggingRegistration) {

        return new InMemoryClientRegistrationRepository(legalLoggingRegistration);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    @Bean
    public ClientRegistration legalLoggingRegistration(@Value("#{environment.GEOSECURE_CLIENT_ID}") String geoSecureClientId,
                                                       @Value("#{environment.GEOSECURE_CLIENT_SECRET}") String geoSecureClientSecret,
                                                       @Value("#{environment.GEOSECURE_TOKEN_URL}") String geoSecureTokenUrl) {
        return ClientRegistration.withRegistrationId("legallogging")
                .clientId(geoSecureClientId)
                .clientSecret(geoSecureClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("BurgerProfielAudit")
                .tokenUri(geoSecureTokenUrl)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

