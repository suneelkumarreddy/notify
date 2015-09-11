package in.notwork.notify.client.router;

/**
 * The implementation of this interface receives the message from the queue.
 *
 * @author rishabh.
 */
public interface Router {

    /**
     * Send the received data to the respective MessageSender.
     *
     * @param data
     * @see in.notwork.notify.server.sender.MessageSender under notify-server module.
     */
    void routeMessage(byte[] data);
}
