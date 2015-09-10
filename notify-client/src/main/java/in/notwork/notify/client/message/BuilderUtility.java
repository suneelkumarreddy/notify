package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

/**
 * @author rishabh.
 */
final class BuilderUtility {

    private BuilderUtility() {
    }

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
