package MessageProducer;

import MessageQueue.MessageQueue;
import MessageQueue.MessageQueueImpl;

import java.util.Scanner;

/**
 * Created by renim on 16/09/2018.
 * A simple message producer which takes lines entered in the command line input and sends them to the message queue
 */
public class MessageProducer {

    public static void main(String[] args) {
        MessageQueue messageQ = new MessageQueueImpl();
        System.out.println("Enter Messages:\n");
        Scanner input = new Scanner(System.in);
        while (input.hasNext() && messageQ.acceptingMessages()) {
            messageQ.queueMessage(input.nextLine());
        }
    }
}
