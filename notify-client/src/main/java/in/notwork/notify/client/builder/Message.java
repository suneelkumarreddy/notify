package in.notwork.notify.client.builder;

import in.notwork.notify.protos.MessageProto;

import java.util.UUID;

/**
 * @author rishabh.
 */
public final class Message {

    private MessageProto.Message.Builder builder;

    private Message() {

    }

    public MessageProto.Message build() {
        return builder.build();
    }

    public static Message email() {
        return initBuilder(MessageType.EMAIL);
    }

    public static Message sms() {
        return initBuilder(MessageType.SMS);
    }

    public static Message push() {
        return initBuilder(MessageType.PUSH);
    }

    public Message to(final String name, final String emailId) {
        mergeReceiver(
                MessageProto.Receiver.newBuilder()
                        .setName(name)
                        .setEmailId(emailId)
                        .build()
        );
        return this;
    }

    public Message cc(final String emailId) {
        mergeReceiver(
                MessageProto.Receiver.newBuilder()
                        .addCc(emailId)
                        .build()
        );
        return this;
    }

    public Message bcc(final String emailId) {
        mergeReceiver(
                MessageProto.Receiver.newBuilder()
                        .addBcc(emailId)
                        .build()
        );
        return this;
    }

    public Message to(final String phoneNumber) {
        mergeReceiver(
                MessageProto.Receiver.newBuilder()
                        .setPhoneNumber(phoneNumber)
                        .build()
        );
        return this;
    }

    public Message channel(final String channelName) {
        mergeReceiver(
                MessageProto.Receiver.newBuilder()
                        .setName(channelName)
                        .build()
        );
        return this;
    }

    public Message from(final String name, final String emailId) {
        mergeSender(
                MessageProto.Sender.newBuilder()
                        .setName(name)
                        .setEmailId(emailId)
                        .build()
        );
        return this;
    }

    public Message from(final String phoneNumber) {
        mergeSender(
                MessageProto.Sender.newBuilder()
                        .setPhoneNumber(phoneNumber)
                        .build()
        );
        return this;
    }

    public Message subject(final String subject) {
        mergeContent(
                MessageProto.Content.newBuilder()
                        .setSubject(subject)
                        .build()
        );
        return this;
    }

    public Message body(final String body) {
        mergeContent(
                MessageProto.Content.newBuilder()
                        .setBody(body)
                        .build()
        );
        return this;
    }

    public Message message(final String body) {
        mergeContent(
                MessageProto.Content.newBuilder()
                        .setBody(body)
                        .build()
        );
        return this;
    }

    public Message priority(final MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(getPriority(priority));
        }
        return this;
    }

    public Message status(final MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(getStatus(status));
        }
        return this;
    }

    private static Message initBuilder(final MessageType type) {
        final Message message = new Message();
        // Set default message id and priority and init builder.
        message.builder = MessageProto.Message.newBuilder()
                .setMessageId(UUID.randomUUID().toString())
                .setPriority(getPriority(MessagePriority.LOW))
                .setType(getType(type));
        return message;
    }

    private void mergeReceiver(final MessageProto.Receiver receiver) {
        if (null != this.builder) {
            if (this.builder.hasReceiver()) {
                this.builder.mergeReceiver(receiver);
            } else {
                this.builder.mergeReceiver(receiver);
            }
        }
    }

    private void mergeSender(final MessageProto.Sender sender) {
        if (null != this.builder) {
            if (this.builder.hasSender()) {
                this.builder.mergeSender(sender);
            } else {
                this.builder.setSender(sender);
            }
        }
    }

    private static MessageProto.Priority getPriority(final MessagePriority priority) {
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

    private MessageProto.Status getStatus(final MessageStatus status) {
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

    private static MessageProto.Type getType(final MessageType type) {
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

    private void mergeContent(final MessageProto.Content content) {
        if (null != this.builder) {
            if (this.builder.hasContent()) {
                this.builder.mergeContent(content);
            } else {
                this.builder.setContent(content);
            }
        }
    }
}
