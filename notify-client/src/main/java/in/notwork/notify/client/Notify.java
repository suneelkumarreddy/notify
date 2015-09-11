package in.notwork.notify.client;

import in.notwork.notify.client.queues.Queue;
import in.notwork.notify.client.queues.QueueFactory;
import in.notwork.notify.protos.MessageProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * The class that sends out the message to the Notify server!
 *
 * @author rishabh.
 */
public final class Notify {

    private static final Logger LOG = LoggerFactory.getLogger(Notify.class);

    private Notify() {
    }

    /**
     * This method sends the Message to the Notify server.
     *
     * @param message The protobuf Message object.
     *                Use the {@link in.notwork.notify.client.message.Email},
     *                {@link in.notwork.notify.client.message.Sms} or
     *                {@link in.notwork.notify.client.message.Notification}
     *                to prepare the respective protobuf message object.
     * @throws IOException
     */
    // TODO Exception handling - to shutdown queue and test
    public static void send(final MessageProto.Message message) throws IOException {
        final Queue queue = QueueFactory.getInstance().getQueue();

        try {
            queue.connect();
        } catch (IOException | TimeoutException e) {
            LOG.error("Unable to connect to the queue...", e);
            throw new IOException("Unable to connect to the queue...", e);
        }

        try {
            queue.publish(message.toByteArray());
        } catch (IOException e) {
            LOG.error("Unable to send the message...", e);
            throw new IOException("Unable to send the message...", e);
        }

        try {
            queue.disconnect();
        } catch (IOException | TimeoutException e) {
            LOG.error("Unable to disconnect from the queue...", e);
            throw new IOException("Unable to disconnect from the queue...", e);
        }
    }
}
