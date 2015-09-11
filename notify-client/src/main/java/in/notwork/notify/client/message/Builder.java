package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

import java.util.UUID;

/**
 * Builder class to build the protobuf message object.
 *
 * @author rishabh.
 */
public class Builder {

    protected MessageProto.Message.Builder builder;

    /**
     * Constructor.
     *
     * @param type The type of message being built - Email, SMS, Notification.
     */
    public Builder(MessageType type) {
        this.builder = init(type);
    }

    /**
     * Initializes the builder with default message id, low priority and message type.
     *
     * @param type The type of message being built - Email, SMS, Notification.
     * @return The Builder object to build the protobuf message object.
     */
    protected MessageProto.Message.Builder init(MessageType type) {
        // Set default message id and priority and init builder.
        return MessageProto.Message.newBuilder()
                .setMessageId(UUID.randomUUID().toString())
                .setPriority(BuilderUtility.getPriority(MessagePriority.LOW))
                .setType(BuilderUtility.getType(type));
    }

    /**
     * @return The final MessageProto.Message object
     * that is required by {@link in.notwork.notify.client.Notify} send() method.
     */
    public MessageProto.Message build() {
        return builder.build();
    }
}
