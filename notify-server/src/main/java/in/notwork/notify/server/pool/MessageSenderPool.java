package in.notwork.notify.server.pool;

import in.notwork.notify.client.message.MessageType;
import in.notwork.notify.server.sender.MessageSender;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author rishabh.
 */
public class MessageSenderPool extends GenericKeyedObjectPool<MessageType, MessageSender> {

    public MessageSenderPool(KeyedPooledObjectFactory<MessageType, MessageSender> factory) {
        super(factory);
    }

    public MessageSenderPool(KeyedPooledObjectFactory<MessageType, MessageSender> factory,
                             GenericKeyedObjectPoolConfig config) {
        super(factory, config);
    }
}
