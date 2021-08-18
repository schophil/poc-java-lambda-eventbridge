# poc-java-lambda-eventbridge

## Simple Event Emitter

Use request handler: `be.vlaanderen.hub.function.EventEmitter::handleRequest`

## Simple Event Processor

Use request handler: `be.vlaanderen.hub.function.EventProcessor::handleRequest`

## Spring Event Processor

Maakt gebruik van spring cloud function.

Use request handler: `org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest`

## WWOOM Event Processor

Maakt gebruik van WebMVC en AWS serverless java container voor Spring Boot 2.

Use request handler: `be.vlaanderen.event.wwoom.StreamLambdaHandler::handleRequest`

## WWOOM Event Processor 2

Maakt gebruik van spring cloud function.

Use request handler: `org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest`
