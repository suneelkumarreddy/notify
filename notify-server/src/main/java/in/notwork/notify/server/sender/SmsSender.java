package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;

import java.util.HashMap;

/**
 * {@link MessageSender} to send SMS.
 *
 * @author rishabh.
 */
public class SmsSender extends MessageSender {

    /**
     * Constructor.
     *
     * @param config Configuration for sending SMS.
     */
    public SmsSender(HashMap<String, String> config) {
        super(config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(MessageProto.Message message) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return false;
    }
}
