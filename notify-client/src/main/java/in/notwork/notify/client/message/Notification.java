package in.notwork.notify.client.message;

import in.notwork.notify.protos.MessageProto;

/**
 * @author rishabh.
 */
public class Notification extends Builder {

    public Notification() {
        super(MessageType.PUSH);
    }

    public Notification channel(final String channelName) {
        BuilderUtility.mergeReceiver(
                this.builder,
                MessageProto.Receiver.newBuilder()
                        .setName(channelName)
                        .build()
        );
        return this;
    }

    public Notification message(final String body) {
        BuilderUtility.mergeContent(
                this.builder,
                MessageProto.Content.newBuilder()
                        .setBody(body)
                        .build()
        );
        return this;
    }

    public Notification priority(final MessagePriority priority) {
        if (null != this.builder) {
            this.builder.setPriority(
                    BuilderUtility.getPriority(priority)
            );
        }
        return this;
    }

    public Notification status(final MessageStatus status) {
        if (null != this.builder) {
            this.builder.setStatus(
                    BuilderUtility.getStatus(status)
            );
        }
        return this;
    }
}
