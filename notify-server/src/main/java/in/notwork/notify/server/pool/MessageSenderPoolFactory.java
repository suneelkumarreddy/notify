package in.notwork.notify.server.pool;

import in.notwork.notify.client.util.Instance;
import in.notwork.notify.server.sender.MessageSender;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static in.notwork.notify.server.pool.MessageSenderPoolFactoryHelper.getConfiguredImplementation;
import static in.notwork.notify.server.pool.MessageSenderPoolFactoryHelper.loadConfiguration;

/**
 * @author rishabh.
 */
public class MessageSenderPoolFactory extends BaseKeyedPooledObjectFactory<MessageType, MessageSender> {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderPoolFactory.class);

    @Override
    public MessageSender create(MessageType messageType) throws Exception {
        LOG.debug("Creating an instance for message type: {0}", messageType);
        String configuredImplementation = getConfiguredImplementation(messageType);
        Map<String, String> config = loadConfiguration(messageType);
        try {
            MessageSender messageSender = Instance.newInstance(configuredImplementation, config);
            return messageSender;
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {

            LOG.error("Unable to create the MessageSender implementation - {0}", configuredImplementation, e);
            throw e;
        }
    }

    @Override
    public PooledObject<MessageSender> wrap(MessageSender messageSender) {
        return new DefaultPooledObject<MessageSender>(messageSender);
    }

    @Override
    public PooledObject<MessageSender> makeObject(MessageType messageType) throws Exception {
        return wrap(create(messageType));
    }

    @Override
    public void destroyObject(MessageType messageType, PooledObject<MessageSender> pooledObject) throws Exception {
        LOG.debug("Destroying {0} for message type: {1}, as it is no longer needed by the pool.",
                pooledObject.toString(), messageType);
        pooledObject.getObject().destroy();
    }

    @Override
    public boolean validateObject(MessageType messageType, PooledObject<MessageSender> pooledObject) {
        LOG.debug("Validating {0} from pool. Using key - {1}", pooledObject, messageType);
        return pooledObject.getObject().isValid();
    }

    @Override
    public void activateObject(MessageType messageType, PooledObject<MessageSender> pooledObject) throws Exception {
        // no-op
    }

    @Override
    public void passivateObject(MessageType messageType, PooledObject<MessageSender> pooledObject) throws Exception {
        // no-op
    }
}
