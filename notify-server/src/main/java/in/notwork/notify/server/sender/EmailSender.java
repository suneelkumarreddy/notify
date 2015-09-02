package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;

import java.util.Map;

/**
 * @author rishabh.
 */
public class EmailSender extends MessageSender {

    public EmailSender (Map<String, String> config) {
        super(config);
    }

    @Override
    public void send(MessageProto.Message message) {
        
    }
}
