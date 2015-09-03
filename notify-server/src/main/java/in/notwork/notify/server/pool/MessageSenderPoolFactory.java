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
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating an instance for message type: " + messageType);
        }
        String configuredImplementation = getConfiguredImplementation(messageType);
        Map<String, String> config = loadConfiguration(messageType);
        try {
            MessageSender messageSender = Instance.newInstance(configuredImplementation, config);
            return messageSender;
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {

            LOG.error("Unable to create the MessageSender implementation - " + configuredImplementation, e);
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("Destroying " + pooledObject + " for message type: " + messageType
                    + ", as it is no longer needed by the pool.");
        }
        pooledObject.getObject().destroy();
    }

    @Override
    public boolean validateObject(MessageType messageType, PooledObject<MessageSender> pooledObject) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Validating " + pooledObject + " from pool. Using key - " + messageType);
        }
        return pooledObject.getObject().isValid();
    }

    @Override
    public void activateObject(MessageType messageType, PooledObject<MessageSender> pooledObject) throws Exception {
        // pooledObject.getObject().activate();
    }

    @Override
    public void passivateObject(MessageType messageType, PooledObject<MessageSender> pooledObject) throws Exception {
        // pooledObject.getObject().deactivate();
    }
}
