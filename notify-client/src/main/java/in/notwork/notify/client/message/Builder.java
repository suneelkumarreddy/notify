package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

import java.util.UUID;

/**
 * @author rishabh.
 */
public class Builder {

    protected MessageProto.Message.Builder builder;

    public Builder(MessageType type) {
        this.builder = init(type);
    }

    protected MessageProto.Message.Builder init(MessageType type) {
        // Set default message id and priority and init builder.
        return MessageProto.Message.newBuilder()
                .setMessageId(UUID.randomUUID().toString())
                .setPriority(BuilderUtility.getPriority(MessagePriority.LOW))
                .setType(BuilderUtility.getType(type));
    }

    public MessageProto.Message build() {
        return builder.build();
    }
}
