package in.notwork.notify.server.notify.client.samples;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import net.jodah.lyra.Connections;
import net.jodah.lyra.config.Config;
import net.jodah.lyra.config.RecoveryPolicies;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;


/**
 * @author rishabh.
 */
public class App {

    private final static String QUEUE_NAME = "myQueue";

    public static void main(String[] args) throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException {

        Config config = new Config().withRecoveryPolicy(RecoveryPolicies.recoverAlways());

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // TODO Figure out how to use SSL
        // factory.useSslProtocol();

//        Connection connection = factory.newConnection();
        Connection connection = Connections.create(factory, config);
        Channel channel = connection.createChannel();

        // TODO Make this configurable from properties file
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 1; i <= 500; ) {
//        int i = 1;
//        boolean flag = true;
//        while (flag) {
            String message = "Hello World " + i++;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

}
