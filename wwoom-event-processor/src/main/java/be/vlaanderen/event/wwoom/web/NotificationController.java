package be.vlaanderen.event.wwoom.web;

import be.vlaanderen.burgerprofiel.wwoom.security.client.oauth.geosecure.GeoSecure;
import be.vlaanderen.event.wwoom.model.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final GeoSecure geoSecure;

    @GetMapping("/")
    public String handleNotification(NotificationEvent notificationEvent) {
        System.out.println("Got event " + notificationEvent);
        return "wwoom-event-processor";
    }
}
