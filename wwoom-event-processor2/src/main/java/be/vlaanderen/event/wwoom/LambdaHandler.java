package be.vlaanderen.event.wwoom;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LambdaHandler implements RequestStreamHandler {

    private static FunctionInvoker functionInvoker;
    private static final CompletableFuture<FunctionInvoker> initializer = CompletableFuture.supplyAsync(FunctionInvoker::new);

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        if (functionInvoker == null) {
            try {
                functionInvoker = initializer.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        functionInvoker.handleRequest(inputStream, outputStream, context);
    }
}
