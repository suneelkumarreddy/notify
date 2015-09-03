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

    // Kapsys constants
    public static final String KAPSYS_URL="url";
    public static final String KAPSYS_WORKING_KEY="workingKey";
    public static final String KAPSYS_SENDER_ID="senderId";
    public static final String KAPSYS_DLR_URL="deliverURL";
    public static final String KAPSYS_STATUS="status";
    public static final String KAPSYS_STATUS_OK="OK";
    public static final String KAPSYS_RESPONSE_MESSAGE="message";
    public static final String KAPSYS_DATA="data";
    public static final String KAPSYS_ERROR_CODE="A";
    public static final String KAPSYS_ERROR_STATUS="Error";
    public static final String KAPSYS_DELVRY_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    public static final String KAPSYS_GROUP_ID="group_id";
}
