package in.notwork.notify.client.queues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rishabh.
 */
public interface Queue {

    void publish(byte[] bytes) throws IOException;

    void connect() throws IOException, TimeoutException;

    void disconnect() throws IOException, TimeoutException;

    void subscribe() throws IOException;
}
