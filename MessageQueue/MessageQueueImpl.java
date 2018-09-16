package MessageQueue;

import MessageProcessor.MessageReceiver;
import MessageProcessor.MessageReceiverImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renim on 16/09/2018.
 * a simple message queue example implementation
 */
public class MessageQueueImpl implements MessageQueue {

    private static List<String> messageQ;
    private MessageReceiver receiver;
    private boolean acceptingMessages = true;

    public MessageQueueImpl() {
        receiver = new MessageReceiverImpl(this);
        messageQ = new ArrayList<>();
        System.out.print(receiver.getUsage());
    }

    public void setAcceptingMessages(boolean acceptingMessages) {
        this.acceptingMessages = acceptingMessages;
    }

    public void queueMessage(String message) {
        messageQ.add(message);
        receiver.newMessage(message);
    }

    public void popMessage(String message) {
        messageQ.remove(message);
    }

    @Override
    public boolean acceptingMessages() {
        return acceptingMessages;
    }
}
