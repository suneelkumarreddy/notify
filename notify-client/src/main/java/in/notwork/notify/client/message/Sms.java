package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

/**
 * @author rishabh.
 */
public class Sms extends Builder {

    public Sms() {
        super(MessageType.SMS);
    }

    public Sms to(final String phoneNumber) {
        BuilderUtility.mergeReceiver(
                this.builder,
                MessageProto.Receiver.newBuilder()
                        .setPhoneNumber(phoneNumber)
                        .build()
        );
        return this;
    }

    public Sms from(final String phoneNumber) {
        BuilderUtility.mergeSender(
                this.builder,
                MessageProto.Sender.newBuilder()
                        .setPhoneNumber(phoneNumber)
                        .build()
        );
        return this;
    }

    public Sms body(final String body) {
        BuilderUtility.mergeContent(
                this.builder,
                MessageProto.Content.newBuilder()
                        .setBody(body)
                        .build()
        );
        return this;
    }

    public Sms priority(final MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(
                    BuilderUtility.getPriority(priority)
            );
        }
        return this;
    }

    public Sms status(final MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(
                    BuilderUtility.getStatus(status)
            );
        }
        return this;
    }

}
