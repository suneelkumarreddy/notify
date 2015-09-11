package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

/**
 * Utility class to build your SMS message.
 * It does not perform any validations on the provided values.
 *
 * @author rishabh.
 */
public class Sms extends Builder {

    /**
     * Constructor.
     * Sets the message type to SMS.
     */
    public Sms() {
        super(MessageType.SMS);
    }

    /**
     * Set the phone number to send the SMS to.
     * Call this method multiple times to, set multiple receivers of the SMS.
     *
     * @param phoneNumber The phone number of receiver.
     * @return Sms
     */
    public Sms to(final String phoneNumber) {
        mergeSms(
                this.builder,
                MessageProto.Sms.newBuilder()
                        .addPhoneNumber(phoneNumber)
                        .build()
        );
        return this;
    }

    /**
     * Set the phone number of the sender of the SMS.
     * This method, if called multiple times, will override the previous set value.
     *
     * @param phoneNumber The phone number of the sender.
     * @return
     */
    public Sms from(final String phoneNumber) {
        mergeSms(
                this.builder,
                MessageProto.Sms.newBuilder()
                        .setFromNumber(phoneNumber)
                        .build()
        );
        return this;
    }

    /**
     * Set the "body" of the SMS.
     * This method, if called multiple times, will override the previous set value.
     *
     * @param body The body of the SMS.
     * @return Sms
     */
    public Sms body(final String body) {
        mergeSms(
                this.builder,
                MessageProto.Sms.newBuilder()
                        .setBody(body)
                        .build()
        );
        return this;
    }

    /**
     * Set the priority of the SMS.
     * As of now, this is not being used.
     *
     * @param priority The priority - LOW, MEDIUM or HIGH
     * @return Sms
     */
    public Sms priority(final MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(
                    BuilderUtility.getPriority(priority)
            );
        }
        return this;
    }

    /**
     * Set the status of the SMS in the message queue.
     * As of now, this is not being used.
     *
     * @param status The status - IN, OUT, SENT, FAILED
     * @return Sms
     */
    public Sms status(final MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(
                    BuilderUtility.getStatus(status)
            );
        }
        return this;
    }

    private void mergeSms(MessageProto.Message.Builder builder, final MessageProto.Sms sms) {
        if (null != builder) {
            if (builder.hasSms()) {
                builder.mergeSms(sms);
            } else {
                builder.setSms(sms);
            }
        }
    }
}
