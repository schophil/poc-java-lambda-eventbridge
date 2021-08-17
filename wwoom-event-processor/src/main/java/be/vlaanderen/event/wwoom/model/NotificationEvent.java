package be.vlaanderen.event.wwoom.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class NotificationEvent {
    @JsonUnwrapped
    private EventMetadata eventMetadata;
    private Notification detail;
}
