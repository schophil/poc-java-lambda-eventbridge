package be.vlaanderen.event.wwoom.model;

import lombok.Data;

@Data
public class Notification {
    private String id;
    private String target;
    private String title;
    private String body;
}
