package in.notwork.notify.server;

import in.notwork.notify.client.queues.Queue;
import in.notwork.notify.client.queues.QueueFactory;
import in.notwork.notify.client.router.Router;
import in.notwork.notify.server.routers.MessageRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/**
 * @author rishabh.
 */
public class Task implements Callable<Queue> {

    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    @Override
    public Queue call() throws Exception {

        final Queue queue = QueueFactory.getInstance().getQueue();

        try {
            queue.connect();
        } catch (IOException | TimeoutException e) {
            LOG.error("Unable to connect to the queue... {0}", e.getCause(), e);
            throw new IOException("Unable to connect to the queue... " + e.getCause(), e);
        }

        Router router = new MessageRouter();

        try {
            queue.subscribe(router);
        } catch (IOException e) {
            LOG.error("Unable to retrieve the message... {0}", e.getCause(), e);
            throw new IOException("Unable to retrieve the message... " + e.getCause(), e);
        }

        return queue;
    }
}
