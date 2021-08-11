package be.vlaanderen.hub.function;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationEvent {

    private String id;

    @JsonProperty("detail-type")
    private String detailType;

    private String source;

    private String time;

    private String details;

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "id='" + id + '\'' +
                ", detailType='" + detailType + '\'' +
                ", source='" + source + '\'' +
                ", time=" + time +
                ", details=" + details +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
