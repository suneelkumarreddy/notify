package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

/**
 * Helper for the Builder class to build the protobuf message object.
 *
 * @author rishabh.
 */
final class BuilderUtility {

    private BuilderUtility() {
    }

    /**
     * Converts {@link MessagePriority} to {@link MessageProto.Priority}
     *
     * @param priority The {@link MessagePriority} enum.
     * @return {@link MessageProto.Priority} enum.
     */
    static MessageProto.Priority getPriority(final MessagePriority priority) {
        MessageProto.Priority protoPriority;
        switch (priority) {
            case HIGH:
                protoPriority = MessageProto.Priority.HIGH;
                break;
            case MEDIUM:
                protoPriority = MessageProto.Priority.MEDIUM;
                break;
            default:
                protoPriority = MessageProto.Priority.LOW;
                break;
        }
        return protoPriority;
    }

    /**
     * Converts {@link MessageStatus} to {@link MessageProto.Status}
     *
     * @param status The {@link MessageStatus} enum.
     * @return {@link MessageProto.Status} enum.
     */
    static MessageProto.Status getStatus(final MessageStatus status) {
        MessageProto.Status protoStatus;
        switch (status) {
            case IN:
                protoStatus = MessageProto.Status.IN;
                break;
            case OUT:
                protoStatus = MessageProto.Status.OUT;
                break;
            case SENT:
                protoStatus = MessageProto.Status.SENT;
                break;
            default:
                protoStatus = MessageProto.Status.FAILED;
                break;
        }
        return protoStatus;
    }

    /**
     * Converts {@link MessageType} to {@link MessageProto.Type}
     *
     * @param type The {@link MessageType} enum.
     * @return {@link MessageProto.Type} enum.
     */
    static MessageProto.Type getType(final MessageType type) {
        MessageProto.Type protoType;
        switch (type) {
            case EMAIL:
                protoType = MessageProto.Type.EMAIL;
                break;
            case SMS:
                protoType = MessageProto.Type.SMS;
                break;
            default:
                protoType = MessageProto.Type.PUSH;
                break;
        }
        return protoType;
    }
}
