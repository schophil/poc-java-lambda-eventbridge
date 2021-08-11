package be.vlaanderen.hub.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EventProcessor implements RequestHandler<NotificationEvent, String> {
    @Override
    public String handleRequest(NotificationEvent event, Context context) {
        System.out.println("Received: " + event);
        return "ok";
    }
}
