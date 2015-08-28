package in.notwork.notify.client;

import in.notwork.notify.client.builder.Message;
import in.notwork.notify.client.queues.Queue;
import in.notwork.notify.client.queues.QueueFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rishabh.
 */
public final class Notify {

    private static final Log LOG = LogFactory.getLog(Notify.class);

//    private static Notify ourInstance = new Notify();
//
//    public static Notify getInstance() {
//        return ourInstance;
//    }

    private Notify() {
    }

    // TODO Exception handling - to shutdown queue and test
    public static void send(final Message message) throws IOException {
        final Queue queue = QueueFactory.getInstance().getQueue();

        try {
            queue.connect();
        } catch (IOException | TimeoutException e) {
            LOG.fatal("Unable to connect to the queue...", e);
            throw new IOException("Unable to connect to the queue...", e);
        }

        try {
            queue.put(message.build().toByteArray());
        } catch (IOException e) {
            LOG.fatal("Unable to send the message...", e);
            throw new IOException("Unable to send the message...", e);
        }

        try {
            queue.disconnect();
        } catch (IOException | TimeoutException e) {
            LOG.fatal("Unable to disconnect from the queue...", e);
            throw new IOException("Unable to disconnect from the queue...", e);
        }
    }
}
