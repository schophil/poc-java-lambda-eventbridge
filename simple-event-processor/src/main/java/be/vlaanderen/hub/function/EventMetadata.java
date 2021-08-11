package be.vlaanderen.hub.function;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class EventMetadata {
    private String id;
    @JsonProperty("detail-type")
    private String detailType;
    private String source;
    private OffsetDateTime time;

    @Override
    public String toString() {
        return "EventMetadata{" +
                "id='" + id + '\'' +
                ", detailType='" + detailType + '\'' +
                ", source='" + source + '\'' +
                ", time=" + time +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getDetailType() {
        return detailType;
    }

    public String getSource() {
        return source;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }
}
