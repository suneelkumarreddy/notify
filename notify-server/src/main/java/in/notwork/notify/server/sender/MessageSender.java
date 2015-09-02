package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;

/**
 * @author rishabh.
 */
public interface MessageSender {
    void send(MessageProto.Message message);
}
