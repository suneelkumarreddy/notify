package in.notwork.notify.client.queues;

import in.notwork.notify.client.router.Router;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * The interface that should be implemented by the queueing implementation being used.
 *
 * @author rishabh.
 */
public interface Queue {

    /**
     * Publish/send the content (in bytes) to the queuing implementation.
     *
     * @param bytes The content to send to queue.
     * @throws IOException
     */
    void publish(byte[] bytes) throws IOException;

    /**
     * Connect to the queue.
     *
     * @throws IOException
     * @throws TimeoutException
     */
    void connect() throws IOException, TimeoutException;

    /**
     * Disconnect fro mthe queue.
     *
     * @throws IOException
     * @throws TimeoutException
     */
    void disconnect() throws IOException, TimeoutException;

    /**
     * Subscribe the queue.
     *
     * @param router The {@link Router} that would read the content from the queue.
     * @throws IOException
     */
    void subscribe(Router router) throws IOException;
}
