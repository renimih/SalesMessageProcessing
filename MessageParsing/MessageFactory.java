package MessageParsing;

import java.util.Arrays;

/**
 * Created by renim on 16/09/2018.
 * Message factory to create the Message object based on the message type
 */
public class MessageFactory {

    /**
     * creates the Message object based on the message type
     * @param type the message type
     * @param message the contents of the message
     * @return the Message as an instance of the appropriate message type
     * @throws MessageFormatException if the message type is not supported or if the message arguments don't match the type
     */
    public Message createMessage(MessageType type, String message) throws MessageFormatException {
        String salesMessage[] = message.split(" ");
        switch (type) {
            case SINGLE:
                if (salesMessage.length == 2) {
                    return new SingleSaleMessage(salesMessage);
                }
                throw new MessageFormatException("Message type \"single\" should contain the product type and price." +
                        "e.g. \"single apple 10p\"");
            case MULTIPLE:
                if (salesMessage.length == 3) {
                    return new MultipleSaleMessage(salesMessage);
                }
                throw new MessageFormatException("Message type \"multiple\" should contain the quantity, product type and price." +
                        "e.g. \"multiple 10 apple 10p\"");
            case ADJUSTMENT:
                if (salesMessage.length == 3) {
                    return new PriceAdjustmentMessage(salesMessage);
                }
                throw new MessageFormatException("Message type \"adjustment\" should contain the adjustment operation," +
                        " adjustment price and product type. e.g. \"adjustment add 10p apple\"");
            default:
                throw new MessageFormatException("Message type %s not supported." +
                        "Supported message types are %s", type.toString(), Arrays.toString(MessageType.values()));
        }
    }

}
