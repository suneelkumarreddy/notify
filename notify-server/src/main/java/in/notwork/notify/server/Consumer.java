package in.notwork.notify.server;

import in.notwork.notify.client.queues.Queue;
import in.notwork.notify.client.queues.QueueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/**
 * @author rishabh.
 */
public class Consumer implements Callable<Queue> {

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    @Override
    public Queue call() throws Exception {

        final Queue queue = QueueFactory.getInstance().getQueue();

        try {
            queue.connect();
        } catch (IOException | TimeoutException e) {
            LOG.error("Unable to connect to the queue..." + e.getCause(), e);
            throw new IOException("Unable to connect to the queue..." + e.getCause(), e);
        }

        try {
            queue.subscribe();
        } catch (IOException e) {
            LOG.error("Unable to send the message... " + e.getCause(), e);
            throw new IOException("Unable to send the message..." + e.getCause(), e);
        }

        return queue;
    }
}
