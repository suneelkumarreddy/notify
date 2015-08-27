package in.notwork.notify.client.builder;

import in.notwork.notify.protos.MessageProto;

import java.util.Map;
import java.util.UUID;

/**
 * @author rishabh.
 */
public class Message extends AbstractMessage {


    private Message() {

    }

    public MessageProto.Message build() {

        return MessageProto.Message.newBuilder()
                .setSender(
                        MessageProto.Sender.newBuilder()
                                .setName(this._toName)
                                .setPhoneNumber(this._toPhoneNumber)
                                .setEmailId(this._toEmailId)
                                .build()
                )
                .setReceiver(
                        MessageProto.Receiver.newBuilder()
                                .setName(this._fromName)
                                .setPhoneNumber(this._fromPhoneNumber)
                                .setEmailId(this._fromEmailId)
                                .build()
                )
                .setContent(buildContent())
                .setMessageId(this._messageId)
                .setPriority(getPriority(this._priority))
                .setStatus(getStatus(this._status))
                .setType(getType(this._type))
                .build();
    }

    private MessageProto.Content buildContent() {
        MessageProto.Content.Builder contentBuilder = MessageProto.Content.newBuilder();
        if (null != this._subject) {
            contentBuilder.setSubject(this._subject);
        }
        if (null != this._body) {
            contentBuilder.setBody(this._body);
        }
        if (null != this._template) {
            contentBuilder.setTemplateName(this._template);
        }
        if (null != this._messageMap && !this._messageMap.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, String> entry : this._messageMap.entrySet()) {
                contentBuilder.setTemplateMap(i++,
                        MessageProto.MapFieldEntry.newBuilder()
                                .setKey(entry.getKey())
                                .setValue(entry.getValue())
                                .build()
                );
            }
        }
        return contentBuilder.build();
    }

    private MessageProto.Priority getPriority(MessagePriority priority) {
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

    private MessageProto.Type getType(MessageType type) {
        switch (type) {
            case EMAIL:
                return MessageProto.Type.EMAIL;
            case SMS:
                return MessageProto.Type.SMS;
            default:
                return MessageProto.Type.PUSH;
        }
    }

    public static Message email() {
        Message message = new Message();
        message._type = MessageType.EMAIL;
        message._messageId = UUID.randomUUID().toString();
        message._priority = MessagePriority.LOW;
        return message;
    }

    public static Message sms() {
        Message message = new Message();
        message._type = MessageType.SMS;
        message._messageId = UUID.randomUUID().toString();
        message._priority = MessagePriority.LOW;
        return message;
    }

    public static Message push() {
        Message message = new Message();
        message._type = MessageType.PUSH;
        message._messageId = UUID.randomUUID().toString();
        message._priority = MessagePriority.LOW;
        return message;
    }

    public Message to(String name, String emailId) {
        _toName = name;
        _toEmailId = emailId;
        return this;
    }

    public Message to(String phoneNumber) {
        _toPhoneNumber = phoneNumber;
        return this;
    }

    public Message from(String name, String emailId) {
        _fromName = name;
        _fromEmailId = emailId;
        return this;
    }

    public Message from(String phoneNumber) {
        _fromPhoneNumber = phoneNumber;
        return this;
    }

    public Message subject(String subject) {
        _subject = subject;
        return this;
    }

    public Message body(String body) {
        _body = body;
        return this;
    }

    public Message priority(MessagePriority priority) {
        _priority = priority;
        return this;
    }

    public Message status(MessageStatus status) {
        _status = status;
        return this;
    }

    public Message map(Map<String, String> map) {
        _messageMap = map;
        return this;
    }

    public Message template(String templateName) {
        _template = templateName;
        return this;
    }
}
