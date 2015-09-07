package in.notwork.notify.client.message;

import com.google.protobuf.ByteString;
import in.notwork.notify.protos.MessageProto;

/**
 * @author rishabh.
 */
public class Email extends Builder {

    public Email() {
        super(MessageType.EMAIL);
    }

    public Email to(final String name, final String emailId) {
        BuilderUtility.mergeReceiver(
                this.builder,
                MessageProto.Receiver.newBuilder()
                        .setName(name)
                        .setEmailId(emailId)
                        .build()
        );
        return this;
    }

    public Email cc(final String emailId) {
        BuilderUtility.mergeReceiver(
                this.builder,
                MessageProto.Receiver.newBuilder()
                        .addCc(emailId)
                        .build()
        );
        return this;
    }

    public Email bcc(final String emailId) {
        BuilderUtility.mergeReceiver(
                this.builder,
                MessageProto.Receiver.newBuilder()
                        .addBcc(emailId)
                        .build()
        );
        return this;
    }

    public Email from(final String name, final String emailId) {
        BuilderUtility.mergeSender(
                this.builder,
                MessageProto.Sender.newBuilder()
                        .setName(name)
                        .setEmailId(emailId)
                        .build()
        );
        return this;
    }

    public Email subject(final String subject) {
        BuilderUtility.mergeContent(
                this.builder,
                MessageProto.Content.newBuilder()
                        .setSubject(subject)
                        .build()
        );
        return this;
    }

    public Email body(final String body) {
        BuilderUtility.mergeContent(
                this.builder,
                MessageProto.Content.newBuilder()
                        .setBody(body)
                        .build()
        );
        return this;
    }

    public Email attach(final String filename, final byte[] content, final String contentType) {
        BuilderUtility.mergeAttachment(
                this.builder,
                MessageProto.Attachment.newBuilder()
                        .setName(filename)
                        .setContent(ByteString.copyFrom(content))
                        .setContentType(contentType)
                        .build()
        );
        return this;
    }

    public Email priority(final MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(
                    BuilderUtility.getPriority(priority)
            );
        }
        return this;
    }

    public Email status(final MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(
                    BuilderUtility.getStatus(status)
            );
        }
        return this;
    }

}
