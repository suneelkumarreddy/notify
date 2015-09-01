package in.notwork.notify.client.queues;

import in.notwork.notify.client.queues.impl.RabbitMQ;
import in.notwork.notify.client.util.NotifyConstants;
import in.notwork.notify.client.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author rishabh.
 */
public final class QueueFactory {

    private static final Logger LOG = LoggerFactory.getLogger(QueueFactory.class);

    private static QueueFactory ourInstance = new QueueFactory();

    private String configuredImplementation;

    public static QueueFactory getInstance() {
        return ourInstance;
    }

    private QueueFactory() {
        configuredImplementation = PropertiesUtil.getProperty(NotifyConstants.QUEUE_IMPL);
        // TODO get other configuration also, if any.
    }


    public Queue getQueue() {
        Queue queue = null;
        try {
            queue = newInstance(configuredImplementation);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {

            LOG.error("Unable to load the queue implementation - " + configuredImplementation, e);
        }
        if (null == queue) {
            queue = new RabbitMQ();
        }
        return queue;
    }

    /*
     * Code from:
     * http://stackoverflow.com/questions/18251299/creating-java-object-from-class-name-with-constructor-which-contains-parameters/18251429#18251429
     */
    private <T> T newInstance(final String className, final Object... args)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // Derive the parameter types from the parameters themselves.
        Class[] types = new Class[args.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = args[i].getClass();
        }
        return (T) Class.forName(className).getConstructor(types).newInstance(args);
    }
}
