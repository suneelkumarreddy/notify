package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;

import java.util.Map;

/**
 * Implementations of this class are responsible
 * to send the message through their respective mechanisms.
 *
 * @author rishabh.
 */
public abstract class MessageSender {

    protected Map<String, String> config;

    public MessageSender(Map<String, String> config) {
        this.config = config;
    }

    /**
     * @param message The protobuf message to send.
     */
    public abstract void send(MessageProto.Message message);

    /**
     * Used by the pool to destroy the object.
     */
    public abstract void destroy();

    /**
     * @return TRUE if this object is still valid to be given out by the pool. Else FALSE.
     */
    public abstract boolean isValid();

}
