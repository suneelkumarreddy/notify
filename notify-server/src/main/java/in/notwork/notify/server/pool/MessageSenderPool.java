package in.notwork.notify.server.pool;

import in.notwork.notify.client.message.MessageType;
import in.notwork.notify.server.sender.MessageSender;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * Pool to store the {@link MessageSender}s.
 *
 * @author rishabh.
 */
public class MessageSenderPool extends GenericKeyedObjectPool<MessageType, MessageSender> {

    /**
     * @param factory The factory that will generate the objects for this pool.
     */
    public MessageSenderPool(KeyedPooledObjectFactory<MessageType, MessageSender> factory) {
        super(factory);
    }

    /**
     * @param factory The factory that will generate the objects for this pool.
     * @param config  The configuration for the factory.
     */
    public MessageSenderPool(KeyedPooledObjectFactory<MessageType, MessageSender> factory,
                             GenericKeyedObjectPoolConfig config) {
        super(factory, config);
    }
}
