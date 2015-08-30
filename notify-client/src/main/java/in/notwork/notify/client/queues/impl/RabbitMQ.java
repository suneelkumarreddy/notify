package in.notwork.notify.client.queues.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import in.notwork.notify.client.queues.Queue;
import in.notwork.notify.client.util.PropertiesUtil;
import static in.notwork.notify.client.util.NotifyConstants.*;
import net.jodah.lyra.Connections;
import net.jodah.lyra.config.Config;
import net.jodah.lyra.config.RecoveryPolicies;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rishabh.
 */
public class RabbitMQ implements Queue {

    private static final Log LOG = LogFactory.getLog(RabbitMQ.class);

    protected String configuredQueue;
    protected String host;
    protected String username;
    protected String password;

    protected boolean durable;
    protected boolean autoDelete;
    protected boolean exclusive;

    private Channel channel;
    private Connection connection;

    public RabbitMQ() {
        initProperties();
    }

    private void initProperties() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Reading queue properties...");
        }
        configuredQueue = PropertiesUtil.getProperty(QUEUE_NAME);
        host = PropertiesUtil.getProperty(QUEUE_HOST);
        username = PropertiesUtil.getProperty(QUEUE_USERNAME);
        password = PropertiesUtil.getProperty(QUEUE_PASSWORD);
        durable = PropertiesUtil.getBooleanProperty(RMQ_DURABLE);
        autoDelete = PropertiesUtil.getBooleanProperty(RMQ_AUTO_DELETE);
        exclusive = PropertiesUtil.getBooleanProperty(RMQ_EXCLUSIVE);
        if (LOG.isDebugEnabled()) {
            LOG.debug("queue:" + configuredQueue
                    + "|host:" + host + "|username:" + username
                    + "|durable:" + durable + "|autodelete:" + autoDelete
                    + "|exclusive:" + exclusive);
        }
    }

    @Override
    public void connect() throws IOException, TimeoutException {

        Config config = new Config().withRecoveryPolicy(RecoveryPolicies.recoverAlways());

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);

        // TODO Figure out how to use SSL
        // factory.useSslProtocol();

        connection = Connections.create(factory, config);
        channel = connection.createChannel();

        channel.queueDeclare(configuredQueue, durable, exclusive, autoDelete, null);
    }

    @Override
    public void put(byte[] bytes) throws IOException {
        if (null == channel) {
            throw new IOException("Connection unavailable. Please check if you are connected to the queue.");
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Putting message to queue - " + configuredQueue);
            }
            channel.basicPublish("", configuredQueue, null, bytes);
        }
    }

    @Override
    public void disconnect() throws IOException, TimeoutException {
        if (null == channel || null == connection) {
            throw new IOException("Connection unavailable. Please check if you are connected to the queue.");
        } else {
            channel.close();
            connection.close();
        }
    }
}
