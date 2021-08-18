package be.vlaanderen.event.wwoom.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.Map;

@Data
public class NotificationEvent {
    @JsonUnwrapped
    private EventMetadata eventMetadata;
    private Map<String, Object> detail;
}
