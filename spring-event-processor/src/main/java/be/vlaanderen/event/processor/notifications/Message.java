package be.vlaanderen.event.processor.notifications;

import lombok.Data;

@Data
public class Message {
    private String id;
    private String target;
    private String title;
    private String body;
}
