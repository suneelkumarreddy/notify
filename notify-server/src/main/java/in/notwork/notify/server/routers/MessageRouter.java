package in.notwork.notify.server.routers;

import com.google.protobuf.InvalidProtocolBufferException;
import in.notwork.notify.client.message.MessageType;
import in.notwork.notify.client.router.Router;
import in.notwork.notify.protos.MessageProto;
import in.notwork.notify.server.pool.MessageSenderPoolController;
import in.notwork.notify.server.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static in.notwork.notify.client.message.MessageType.*;

/**
 * Implementation of {@link Router}.
 * This class is responsible to send the message to respective {@link MessageSender}
 * based on the {@link MessageType}.
 *
 * @author rishabh.
 */
public class MessageRouter implements Router {

    private static final Logger LOG = LoggerFactory.getLogger(MessageRouter.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void routeMessage(final byte[] data) {
        try {
            final MessageProto.Message message = MessageProto.Message.parseFrom(data);
            final MessageSender sender = getSender(message.getType());
            sender.send(message);
            returnSenderToPool(message.getType(), sender);
        } catch (InvalidProtocolBufferException e) {
            LOG.error("Unable to parse the message received from queue.", e);
        } catch (IllegalStateException e) {
            LOG.error("Error returning message sender to the pool.", e);
        } catch (Exception e) {
            LOG.error("Unable to get message sender object from pool", e);
        }
    }

    private void returnSenderToPool(final MessageProto.Type type, final MessageSender sender) {
        switch (type) {
            case EMAIL:
                MessageSenderPoolController.getInstance().getPool().returnObject(EMAIL, sender);
                break;
            case SMS:
                MessageSenderPoolController.getInstance().getPool().returnObject(SMS, sender);
                break;
            default:
                MessageSenderPoolController.getInstance().getPool().returnObject(PUSH, sender);
                break;
        }
    }

    private MessageSender getSender(final MessageProto.Type type) throws Exception {
        MessageSender sender;
        switch (type) {
            case EMAIL:
                sender = getMessageSenderFromPool(EMAIL);
                break;
            case SMS:
                sender = getMessageSenderFromPool(SMS);
                break;
            default:
                sender = getMessageSenderFromPool(PUSH);
                break;
        }
        return sender;
    }

    private MessageSender getMessageSenderFromPool(final MessageType messageType) throws Exception {
        return MessageSenderPoolController.getInstance().getPool().borrowObject(messageType);
    }

}
