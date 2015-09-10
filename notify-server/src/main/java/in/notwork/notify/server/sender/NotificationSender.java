package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static in.notwork.notify.client.util.NotifyConstants.*;

/**
 * @author rishabh.
 */
public class NotificationSender extends MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationSender.class);

    private BayeuxClient client;

    public NotificationSender(final HashMap<String, String> config) {
        super(config);
        prepareBayeuxConnection();
    }

    private void prepareBayeuxConnection() {
        final String host = config.get(FAYE_HOST);
        final String port = config.get(FAYE_PORT);
        client = new BayeuxClient(
                "http://" + host + ":" + port + "/",
                LongPollingTransport.create(null, getProxySettings())
        );
        client.handshake();
        client.waitFor(1000, BayeuxClient.State.CONNECTED);
    }

    @Override
    public void send(final MessageProto.Message message) {
        LOG.debug("Preparing push notification...");
        final String channelName = message.getNotification().getChannel();
        final String content = message.getNotification().getBody();
        final ClientSessionChannel channel = client.getChannel(channelName);
        channel.publish(content);
        if (LOG.isDebugEnabled()) {
            channel.subscribe(
                    (channel1, message1)
                            -> LOG.debug("Pushed <" + channel1 + "> " + message1.getData())
            );
        }
    }

    @Override
    public void destroy() {
        client.waitFor(5000, BayeuxClient.State.DISCONNECTED);
        client.disconnect();
        client = null;
    }

    @Override
    public boolean isValid() {
        boolean flag = false;
        if (null != client) {
            flag = client.isConnected();
        }
        return flag;
    }

    private HttpClient getProxySettings() {
        final HttpClient httpClient = new HttpClient();
        if (null != config.get(PROXY_HOST) && null != config.get(PROXY_PORT)) {
            LOG.debug("Configuring proxy for faye...");
            final Address proxy = new Address(
                    config.get(PROXY_HOST),
                    Integer.parseInt(config.get(PROXY_PORT)));
            httpClient.setProxy(proxy);
        }
        return httpClient;
    }
}
