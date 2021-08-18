package be.vlaanderen.event.wwoom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Log
public class Serialization {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void initialize() {
        objectMapper = new ObjectMapper();
    }

    @Data
    static class MyModel {
        String id;
        Object data;
    }

    @Test
    public void deserialize() throws JsonProcessingException {
        String json = "{\"id\": 1, \"data\": { \"message\": \"hello world\" }}";

        MyModel myModel = objectMapper.readValue(json, MyModel.class);

        log.info(myModel.getData().getClass().getName());
        log.info(myModel.toString());

        String data = objectMapper.writeValueAsString(myModel.getData());
        log.info("DATA > " + data);
    }
}
