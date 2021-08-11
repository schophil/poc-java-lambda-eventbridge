package be.vlaanderen.event.processor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class EventMetadata {
    private String id;
    @JsonProperty("detail-type")
    private String detailType;
    private String source;
    private OffsetDateTime time;
}
