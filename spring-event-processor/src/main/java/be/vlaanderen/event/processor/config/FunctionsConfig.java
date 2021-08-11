package be.vlaanderen.event.processor.config;

import be.vlaanderen.event.processor.notifications.NotificationEvent;
import be.vlaanderen.event.processor.notifications.NotificationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class FunctionsConfig {

    @Bean
    public Function<NotificationEvent, String> notificationProcessor() {
        return new NotificationProcessor();
    }
}
