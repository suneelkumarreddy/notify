package in.notwork.notify.server.routers;

import com.google.protobuf.InvalidProtocolBufferException;
import in.notwork.notify.client.router.Router;
import in.notwork.notify.protos.MessageProto;
import in.notwork.notify.server.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rishabh.
 */
public class MessageRouter implements Router {

    private static final Logger LOG = LoggerFactory.getLogger(MessageRouter.class);

    @Override
    public void routeMessage(byte[] data) {
        try {
            MessageProto.Message message = MessageProto.Message.parseFrom(data);
            MessageSender sender = getSender(message.getType());
            sender.send(message);
        } catch (InvalidProtocolBufferException e) {
            // TODO Remove e.printStackTrace()
            e.printStackTrace();
        }
    }

    private MessageSender getSender(final MessageProto.Type type) {
        MessageSender sender;
        switch (type) {
            case EMAIL:
                sender = getEmailSenderFromPool();
                break;
            case SMS:
                sender = getSmsSenderFromPool();
                break;
            default:
                sender = getNotificationSenderFromPool();
                break;
        }
        return sender;
    }

    private MessageSender getNotificationSenderFromPool() {
        return null;
    }

    private MessageSender getSmsSenderFromPool() {
        return null;
    }

    private MessageSender getEmailSenderFromPool() {
        return null;
    }
}
