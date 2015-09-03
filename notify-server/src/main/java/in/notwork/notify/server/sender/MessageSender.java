package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;

import java.util.Map;

/**
 * @author rishabh.
 */
public abstract class MessageSender {

    protected Map<String, String> config;

    public MessageSender(Map<String, String> config) {
        this.config = config;
    }

    public abstract void send(MessageProto.Message message);

}
