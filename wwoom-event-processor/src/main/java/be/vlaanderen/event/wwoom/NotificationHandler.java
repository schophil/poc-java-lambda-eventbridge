package be.vlaanderen.event.wwoom;

import be.vlaanderen.event.wwoom.model.NotificationEvent;
import be.vlaanderen.event.wwoom.web.NotificationController;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NotificationHandler implements RequestStreamHandler {

    @SneakyThrows
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        ApplicationContext applicationContext = ContextHolder.getApplicationContext();

        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);

        NotificationEvent notificationEvent = objectMapper.readValue(inputStream, NotificationEvent.class);

        NotificationController notificationController = applicationContext.getBean(NotificationController.class);
        String result = notificationController.handleNotification(notificationEvent);

        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
    }
}
