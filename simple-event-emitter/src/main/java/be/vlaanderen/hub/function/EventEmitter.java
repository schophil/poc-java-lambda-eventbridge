package be.vlaanderen.hub.function;

import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClientBuilder;
import com.amazonaws.services.eventbridge.model.PutEventsRequest;
import com.amazonaws.services.eventbridge.model.PutEventsRequestEntry;
import com.amazonaws.services.eventbridge.model.PutEventsResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventEmitter implements RequestHandler<EmitEvents, String> {

    private ObjectMapper objectMapper;

    public EventEmitter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String handleRequest(EmitEvents emitEvents, Context context) {
        AmazonEventBridge eventBridge = getAmazonEventBridge();

        PutEventsResult putEventsResult = eventBridge.putEvents(new PutEventsRequest().withEntries(createRequestEntries(emitEvents)));
        System.out.println(putEventsResult);

        return "Events sent!";
    }

    private Collection<PutEventsRequestEntry> createRequestEntries(EmitEvents emitEvents) {
        return IntStream.range(0, emitEvents.getTotal())
                .mapToObj(i -> createRequestEntry(generateMessage(emitEvents)))
                .collect(Collectors.toList());
    }

    private Message generateMessage(EmitEvents emitEvents) {
        Message message = new Message();
        message.setCorrelationId(UUID.randomUUID().toString());
        message.setNotificatieId(UUID.randomUUID().toString());
        message.setOnderwerp(RandomStringUtils.randomAlphabetic(10));
        message.setClientId(Optional.ofNullable(emitEvents.getTarget())
                .orElseGet(() -> RandomStringUtils.random(5).toUpperCase()));
        return message;
    }

    private AmazonEventBridge getAmazonEventBridge() {
        AmazonEventBridgeClientBuilder builder = AmazonEventBridgeClientBuilder.standard();
        builder.setRegion("eu-west-1");
        return builder.build();
    }

    private PutEventsRequestEntry createRequestEntry(Message message) {
        PutEventsRequestEntry putEventsRequestEntry = new PutEventsRequestEntry();
        putEventsRequestEntry.setEventBusName("mbp-events-poc");
        putEventsRequestEntry.setSource("EventEmitter");
        putEventsRequestEntry.setDetailType("Notification");
        try {
            putEventsRequestEntry.setDetail(objectMapper.writeValueAsString(message));
            return putEventsRequestEntry;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
