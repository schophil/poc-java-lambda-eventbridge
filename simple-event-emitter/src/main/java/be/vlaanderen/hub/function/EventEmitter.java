package be.vlaanderen.hub.function;

import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClientBuilder;
import com.amazonaws.services.eventbridge.model.PutEventsRequest;
import com.amazonaws.services.eventbridge.model.PutEventsRequestEntry;
import com.amazonaws.services.eventbridge.model.PutEventsResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EventEmitter implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String message, Context context) {
        AmazonEventBridge eventBridge = getAmazonEventBridge();

        PutEventsResult putEventsResult = eventBridge.putEvents(new PutEventsRequest().withEntries(createRequestEntry(message)));
        System.out.println(putEventsResult);

        if (putEventsResult.getFailedEntryCount() > 0) {
            throw new RuntimeException("Sending message failed!");
        }

        return "Events sent";
    }

    private AmazonEventBridge getAmazonEventBridge() {
        AmazonEventBridgeClientBuilder builder = AmazonEventBridgeClientBuilder.standard();
        builder.setRegion("eu-west-1");
        return builder.build();
    }

    private PutEventsRequestEntry createRequestEntry(String message) {
        PutEventsRequestEntry putEventsRequestEntry = new PutEventsRequestEntry();
        putEventsRequestEntry.setEventBusName("mbp-events-poc");
        putEventsRequestEntry.setSource("EventEmitter");
        putEventsRequestEntry.setDetailType("Notification");
        putEventsRequestEntry.setDetail(String.format("{\"message\": \"%s\"}", message));
        return putEventsRequestEntry;
    }
}
