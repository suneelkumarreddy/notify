package in.notwork.notify.client.builder;

import in.notwork.notify.protos.MessageProto;

import java.util.UUID;

/**
 * @author rishabh.
 */
public class Message {

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

    public Message to(String name, String emailId) {
        MessageProto.Receiver receiver =
                MessageProto.Receiver.newBuilder()
                        .setName(name)
                        .setEmailId(emailId)
                        .build();
        mergeReceiver(receiver);
        return this;
    }

    public Message cc(String emailId) {
        mergeReceiver(
                MessageProto.Receiver.newBuilder()
                        .addCc(emailId)
                        .build());
        return this;
    }

    public Message bcc(String emailId) {
        mergeReceiver(MessageProto.Receiver.newBuilder()
                .addBcc(emailId)
                .build());
        return this;
    }

    public Message to(String phoneNumber) {
        MessageProto.Receiver receiver =
                MessageProto.Receiver.newBuilder()
                        .setPhoneNumber(phoneNumber)
                        .build();
        mergeReceiver(receiver);
        return this;
    }

    public Message channel(String channelName) {
        MessageProto.Receiver receiver =
                MessageProto.Receiver.newBuilder()
                        .setName(channelName)
                        .build();
        mergeReceiver(receiver);
        return this;
    }

    public Message from(String name, String emailId) {
        MessageProto.Sender sender = MessageProto.Sender.newBuilder()
                .setName(name)
                .setEmailId(emailId)
                .build();
        mergeSender(sender);
        return this;
    }

    public Message from(String phoneNumber) {
        MessageProto.Sender sender = MessageProto.Sender.newBuilder()
                .setPhoneNumber(phoneNumber)
                .build();
        mergeSender(sender);
        return this;
    }

    public Message subject(String subject) {
        MessageProto.Content content = MessageProto.Content.newBuilder()
                .setSubject(subject)
                .build();
        mergeContent(content);
        return this;
    }

    public Message body(String body) {
        MessageProto.Content content = MessageProto.Content.newBuilder()
                .setBody(body)
                .build();
        mergeContent(content);
        return this;
    }

    public Message message(String body) {
        MessageProto.Content content = MessageProto.Content.newBuilder()
                .setBody(body)
                .build();
        mergeContent(content);
        return this;
    }

    public Message priority(MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(getPriority(priority));
        }
        return this;
    }

    public Message status(MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(getStatus(status));
        }
        return this;
    }
/*

    public Message useMap(Map<String, String> map) {
        MessageProto.Content.Builder contentBuilder = MessageProto.Content.newBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            contentBuilder.addTemplateMap(
                    MessageProto.MapFieldEntry.newBuilder()
                            .setKey(entry.getKey())
                            .setValue(entry.getValue())
            );
        }
        mergeContent(contentBuilder.build());
        return this;
    }

    public Message useTemplate(String templateName) {
        MessageProto.Content content = MessageProto.Content.newBuilder()
                .setTemplateName(templateName)
                .build();
        mergeContent(content);
        return this;
    }
*/

    private static Message initBuilder(MessageType type) {
        Message message = new Message();
        // Set default message id and priority and init builder.
        message.builder = MessageProto.Message.newBuilder()
                .setMessageId(UUID.randomUUID().toString())
                .setPriority(getPriority(MessagePriority.LOW))
                .setType(getType(type));
        return message;
    }

    private void mergeReceiver(MessageProto.Receiver receiver) {
        if (null != this.builder) {
            if (this.builder.hasReceiver()) {
                this.builder.mergeReceiver(receiver);
            } else {
                this.builder.mergeReceiver(receiver);
            }
        }
    }

    private void mergeSender(MessageProto.Sender sender) {
        if (null != this.builder) {
            if (this.builder.hasSender()) {
                this.builder.mergeSender(sender);
            } else {
                this.builder.setSender(sender);
            }
        }
    }

    private static MessageProto.Priority getPriority(MessagePriority priority) {
        switch (priority) {
            case HIGH:
                return MessageProto.Priority.HIGH;
            case MEDIUM:
                return MessageProto.Priority.MEDIUM;
            default:
                return MessageProto.Priority.LOW;
        }
    }

    private MessageProto.Status getStatus(MessageStatus status) {
        switch (status) {
            case IN:
                return MessageProto.Status.IN;
            case OUT:
                return MessageProto.Status.OUT;
            case SENT:
                return MessageProto.Status.SENT;
            default:
                return MessageProto.Status.FAILED;
        }
    }

    private static MessageProto.Type getType(MessageType type) {
        switch (type) {
            case EMAIL:
                return MessageProto.Type.EMAIL;
            case SMS:
                return MessageProto.Type.SMS;
            default:
                return MessageProto.Type.PUSH;
        }
    }

    private void mergeContent(MessageProto.Content content) {
        if (null != this.builder) {
            if (this.builder.hasContent()) {
                this.builder.mergeContent(content);
            } else {
                this.builder.setContent(content);
            }
        }
    }
}
