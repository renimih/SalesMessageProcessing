package MessageProcessor;

import MessageParsing.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import MessageQueue.MessageQueueImpl;


/**
 * Created by renim on 16/09/2018.
 * Message receiver example implementation
 */
public class MessageReceiverImpl implements MessageReceiver {

    private Map<String, ProductStats> report;

    private MessageFactory messageFactory = new MessageFactory();

    private int messageCount;
    private MessageQueueImpl messageQ;

    public MessageReceiverImpl(MessageQueueImpl messageQ) {
        this.messageQ = messageQ;
        report = new HashMap<>();
        messageCount = 0;
    }

    @Override
    public void newMessage(String message) {
        // split msg by spaces
        if (message.isEmpty()) {
            return;
        }
        String[] words = message.split(" ", 2);

        try {
            MessageType messageType = MessageType.valueOf(words[0].toUpperCase());

            Message salesMessage = messageFactory.createMessage(messageType, words[1]);
            salesMessage.record(report);
            // message processed => consume from queue
            messageQ.popMessage(message);
            messageCount++;

            if (messageCount % 10 == 0) {
                onTenMessages();
            }
            if (messageCount == 50) {
                System.out.println("50 messages have been received. Message Receiver is pausing...\n");
                onFiftyMessages();
                messageQ.setAcceptingMessages(false);
                System.out.println("Message processing terminated. Press Enter 'q' to exit.\n");
            }
        } catch (MessageFormatException e) {
            System.err.print(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(String.format("Message type %s not supported." +
                    "Supported message types are %s", words[0], Arrays.toString(MessageType.values())));
        }
    }

    private void onTenMessages() {
        System.out.println(String.format("\n%30s %25s %10s %25s %10s", "Product", "|", "Price(p)", "|", "Qty"));
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (String product : report.keySet()) {
            System.out.println(String.format("%30s %25s %10.2f %25s %10s",
                    product, "|", report.get(product).getTotalValue(), "|", report.get(product).getQuantity()));
        }
    }

    private void onFiftyMessages() {
        System.out.println(String.format("\n%30s %25s %10s %25s %10s", "Product", "|", "Price(p)", "|", "Adjustment"));
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (String product: report.keySet()) {
            System.out.print(product);
            for (PriceAdjustmentMessage priceAdj : report.get(product).getProductAdjustments()) {
                System.out.println(String.format("%30s %25s %10.2f %25s %10s", "",
                        "|", priceAdj.getPrice(), "|", priceAdj.getOperation()));
            }
        }
    }

    public String getUsage() {
        return "Supported message types:\n" +
                "\tSINGLE <product type> <price>  | e.g. single apple 10p\n" +
                "\tMULTIPLE <quantity> <product type> <price> | e.g. multiple 10 apple 10p\n" +
                "\tADJUSTMENT <operation> <price adjustment> <product type> | e.g. adjustment add 10p apple\n" +
                "Supported prices are in pounds or pence | e.g. £50 or 50p\n" +
                "Supported adjustment operations are ADD, SUBTRACT and MULTIPLY\n";
    }
}
