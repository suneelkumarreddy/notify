package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;

import java.util.HashMap;

/**
 * @author rishabh.
 */
public class SmsSender extends MessageSender {

    public SmsSender(HashMap<String, String> config) {
        super(config);
    }

    @Override
    public void send(MessageProto.Message message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
