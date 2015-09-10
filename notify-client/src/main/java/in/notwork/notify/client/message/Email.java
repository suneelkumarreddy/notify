package in.notwork.notify.client.message;

import com.google.protobuf.ByteString;
import in.notwork.notify.protos.MessageProto;

/**
 * Utility class to build your Email message.
 * It does not perform any validations on the provided values.
 *
 * @author rishabh.
 */
public class Email extends Builder {

    /**
     * Constructor.
     * Sets the message type to SMS.
     */
    public Email() {
        super(MessageType.EMAIL);
    }

    /**
     * Set the "to" email field.
     * Call this method multiple times to set multiple values in "to" field of email.
     *
     * @param emailId The email id to send the email to.
     * @return Email
     */
    public Email to(final String emailId) {
        mergeEmail(
                this.builder,
                MessageProto.Email.newBuilder()
                        .addToEmail(emailId)
                        .build()
        );
        return this;
    }

    /**
     * Set the "cc" email field.
     * Call this method multiple times to set multiple values in "cc" field of email.
     *
     * @param emailId The email id to send the copy of the email to.
     * @return Email
     */
    public Email cc(final String emailId) {
        mergeEmail(
                this.builder,
                MessageProto.Email.newBuilder()
                        .addCcEmail(emailId)
                        .build()
        );
        return this;
    }

    /**
     * Set the "bcc" email field.
     * Call this method multiple times to set multiple values in "bcc" field of email.
     *
     * @param emailId The email id to send the blind copy of the email to.
     * @return Email
     */
    public Email bcc(final String emailId) {
        mergeEmail(
                this.builder,
                MessageProto.Email.newBuilder()
                        .addBccEmail(emailId)
                        .build()
        );
        return this;
    }

    /**
     * Set the "from" email field.
     * This method, if called multiple times, will override the previous set value.
     *
     * @param emailId The email id of the sender.
     * @return Email
     */
    public Email from(final String emailId) {
        mergeEmail(
                this.builder,
                MessageProto.Email.newBuilder()
                        .setFromEmail(emailId)
                        .build()
        );
        return this;
    }

    /**
     * Set the "subject" of the email.
     * This method, if called multiple times, will override the previous set value.
     *
     * @param subject The subject of the email.
     * @return Email
     */
    public Email subject(final String subject) {
        mergeEmail(
                this.builder,
                MessageProto.Email.newBuilder()
                        .setSubject(subject)
                        .build()
        );
        return this;
    }

    /**
     * Set the "body" of the email.
     * This method, if called multiple times, will override the previous set value.
     *
     * @param body The body of the email.
     * @return Email
     */
    public Email body(final String body) {
        mergeEmail(
                this.builder,
                MessageProto.Email.newBuilder()
                        .setBody(body)
                        .build()
        );
        return this;
    }

    /**
     * Add an attachment to the email.
     * To add multiple attachments, call this method multiple times.
     *
     * @param filename    The name of the attachment.
     * @param content     The attachment in byte array format.
     * @param contentType The MIME type of the attachment.
     * @return Email
     */
    public Email attach(final String filename, final byte[] content, final String contentType) {
        mergeEmail(
                this.builder,
                MessageProto.Email.newBuilder()
                        .addAttachment(
                                MessageProto.Attachment.newBuilder()
                                        .setName(filename)
                                        .setContent(ByteString.copyFrom(content))
                                        .setContentType(contentType)
                                        .build()
                        )
                        .build()
        );
        return this;
    }

    /**
     * Set the priority of the email.
     * As of now, this is not being used.
     *
     * @param priority The priority - LOW, MEDIUM or HIGH
     * @return Email
     */
    public Email priority(final MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(
                    BuilderUtility.getPriority(priority)
            );
        }
        return this;
    }

    /**
     * Set the status of the email in the message queue.
     * As of now, this is not being used.
     *
     * @param status The status - IN, OUT, SENT, FAILED
     * @return Email
     */
    public Email status(final MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(
                    BuilderUtility.getStatus(status)
            );
        }
        return this;
    }

    private void mergeEmail(MessageProto.Message.Builder builder, final MessageProto.Email email) {
        if (null != builder) {
            if (builder.hasEmail()) {
                builder.mergeEmail(email);
            } else {
                builder.setEmail(email);
            }
        }
    }
}
