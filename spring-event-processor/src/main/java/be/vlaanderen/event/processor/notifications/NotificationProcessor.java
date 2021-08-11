package be.vlaanderen.event.processor.notifications;

import java.util.function.Function;

public class NotificationProcessor implements Function<NotificationEvent, String> {
    @Override
    public String apply(NotificationEvent notificationEvent) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> NotificationProcessor");
        System.out.println("Got event: " + notificationEvent);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> NotificationProcessor");
        return "ok:" + notificationEvent.getEventMetadata().getId();
    }
}
