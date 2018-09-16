package MessageProcessor;

/**
 * Created by renim on 16/09/2018.
 * Interface for the Message receiver
 */
public interface MessageReceiver {

    /**
     * processes a new message
     * @param message the message to be processed
     */
    void newMessage(String message);

    /**
     *
     * @return a usage String describing what messages can be received
     */
    String getUsage();
}
