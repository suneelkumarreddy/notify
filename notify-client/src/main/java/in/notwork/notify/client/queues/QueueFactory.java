package in.notwork.notify.client.queues;

import in.notwork.notify.client.queues.impl.RabbitMQ;
import in.notwork.notify.client.util.Instance;
import in.notwork.notify.client.util.NotifyConstants;
import in.notwork.notify.client.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory that creates the implementation of the {@link Queue}
 * based on the configured property.
 *
 * @author rishabh.
 */
public final class QueueFactory {

    private static final Logger LOG = LoggerFactory.getLogger(QueueFactory.class);

    private static QueueFactory ourInstance = new QueueFactory();

    private String configuredImplementation;

    /**
     * @return Instance of this factory.
     */
    public static QueueFactory getInstance() {
        return ourInstance;
    }

    private QueueFactory() {
        configuredImplementation = PropertiesUtil.getProperty(NotifyConstants.QUEUE_IMPL);
        // TODO get other configuration also, if any.
    }

    /**
     * @return The configured implementation of {@link Queue}.
     */
    public Queue getQueue() {
        Queue queue = null;
        try {
            queue = Instance.newInstance(configuredImplementation);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {

            LOG.error("Unable to load the queue implementation - {}", configuredImplementation, e);
        }
        if (null == queue) {
            queue = new RabbitMQ();
        }
        return queue;
    }
}
