package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

/**
 * Utility class to build your Notification message.
 * It does not perform any validations on the provided values.
 *
 * @author rishabh.
 */
public class Notification extends Builder {

    /**
     * Constructor.
     */
    public Notification() {
        super(MessageType.PUSH);
    }

    /**
     * Set the channel name on which to send the notification on.
     * This method, if called multiple times, will override the previous set value.
     *
     * @param channelName The name of the channel.
     * @return Notification
     */
    public Notification channel(final String channelName) {
        mergeNotification(
                this.builder,
                MessageProto.Notification.newBuilder()
                        .setChannel(channelName)
                        .build()
        );
        return this;
    }

    /**
     * Set the notification message.
     * This method, if called multiple times, will override the previous set value.
     *
     * @param message The notification message.
     * @return Notification
     */
    public Notification message(final String message) {
        mergeNotification(
                this.builder,
                MessageProto.Notification.newBuilder()
                        .setBody(message)
                        .build()
        );
        return this;
    }

    /**
     * Set the priority of the notification.
     * As of now, this is not being used.
     *
     * @param priority The priority - LOW, MEDIUM or HIGH
     * @return Notification
     */
    public Notification priority(final MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(
                    BuilderUtility.getPriority(priority)
            );
        }
        return this;
    }

    /**
     * Set the status of the notification in the message queue.
     * As of now, this is not being used.
     *
     * @param status The status - IN, OUT, SENT, FAILED
     * @return Notification
     */
    public Notification status(final MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(
                    BuilderUtility.getStatus(status)
            );
        }
        return this;
    }

    private void mergeNotification(MessageProto.Message.Builder builder, final MessageProto.Notification notification) {
        if (null != builder) {
            if (builder.hasNotification()) {
                builder.mergeNotification(notification);
            } else {
                builder.setNotification(notification);
            }
        }
    }
}
