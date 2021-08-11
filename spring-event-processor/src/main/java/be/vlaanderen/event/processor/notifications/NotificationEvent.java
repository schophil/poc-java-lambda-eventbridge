package be.vlaanderen.event.processor.notifications;

import be.vlaanderen.event.processor.model.EventMetadata;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class NotificationEvent {
    @JsonUnwrapped
    private EventMetadata eventMetadata;
    private Message detail;
}
