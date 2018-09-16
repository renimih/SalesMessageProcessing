# SalesMessageProcessing

This submission is designed to simulate interaction with an external system (simulated by MessageProducer) via a simple MessageQueue ( simulated by MessageQueue). The application is implemented by the MessageParser and MessageReceiver packages.

MessageProducer also holds the main class for the simulation.

sample_messages.txt contains 50 correct sample messages for testing.

Correct message types are:
```
SINGLE <product type> <price>  | e.g. single apple 10p
  
MULTIPLE <quantity> <product type> <price> | e.g. multiple 10 apple 10p
  
ADJUSTMENT <operation> <price adjustment> <product type> | e.g. adjustment add 10p apple
```
## How to run
### For sample Message Producer
`javac -cp . MessageProducer/MessageProducer.java`

`java MessageProducer/MessageProducer`

### For message unit tests
`javac -cp junit-4.12.jar:hamcrest-core-1.3.jar:.  MessageParsing/MessageTest.java`

`java -cp junit-4.12.jar:hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore MessageParsing.MessageTest`

## Assumptions

* Messages will be sent to a message queue (using provided interface) by an external system (simulated by MessageProducer).
* Product types will be used as unique identifiers (i.e. "apple" and "apples" will be considered different product types) and it is up to the external system to define the identifiers.
* Prices will only be in pounds or pence which needs to be specified in the message by the external system (e.g. £50 or 50p).
* after 50 messages the application will not accept any more messages.
