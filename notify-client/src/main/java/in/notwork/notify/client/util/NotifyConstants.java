package in.notwork.notify.client.util;

/**
 * @author rishabh.
 */
public class NotifyConstants {

    // Generic constants.
    public static final String QUEUE_IMPL = "queue.impl";
    public static final String QUEUE_NAME = "queue.name";
    public static final String QUEUE_USERNAME = "queue.username";
    public static final String QUEUE_PASSWORD = "queue.password";
    public static final String QUEUE_HOST = "queue.host";

    // Constants specific to RabbitMQ implementation.
    public static final String RMQ_DURABLE = "rmq.queue.durable";
    public static final String RMQ_EXCLUSIVE = "rmq.queue.exclusive";
    public static final String RMQ_AUTO_DELETE = "rmq.queue.autoDelete";
}
