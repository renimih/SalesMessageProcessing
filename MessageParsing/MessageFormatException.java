package MessageParsing;

/**
 * Created by renim on 16/09/2018.
 * Custom exception for incorrect message format
 */
public class MessageFormatException extends Exception {

    MessageFormatException(String exceptionMessage, String ... strings) {
        super("Invalid message format: " + String.format(exceptionMessage, (Object[]) strings));
    }
}
