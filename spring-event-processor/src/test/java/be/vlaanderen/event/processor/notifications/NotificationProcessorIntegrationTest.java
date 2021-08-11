package be.vlaanderen.event.processor.notifications;

import be.vlaanderen.event.processor.model.EventMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;

import java.util.UUID;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@FunctionalSpringBootTest
public class NotificationProcessorIntegrationTest {

    @Autowired
    private FunctionCatalog catalog;

    @Test
    public void test() {
        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setEventMetadata(new EventMetadata());
        notificationEvent.getEventMetadata().setId(UUID.randomUUID().toString());


        Function<NotificationEvent, String> function = catalog.lookup(Function.class,
                "notificationProcessor");
        assertThat(function.apply(notificationEvent)).isEqualTo("ok:" + notificationEvent.getEventMetadata().getId());
    }
}
