package MessageQueue;

/**
 * Created by renim on 16/09/2018.
 * A basic message queue interface
 */
public interface MessageQueue {

    /**
     * adds a message to the queue and notifies the receiver of the new message
     * @param message the new message
     */
    void queueMessage(String message);

    /**
     * removes a message from the queue
     * @param message the message to be removed
     */
    void popMessage(String message);

    /**
     * @return a flag to determine if any receivers are accepting messages
     */
    boolean acceptingMessages();
}
