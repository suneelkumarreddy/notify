package in.notwork.notify.server.pool;

import in.notwork.notify.client.util.PropertiesUtil;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static in.notwork.notify.client.util.NotifyConstants.*;

/**
 * @author rishabh.
 */
public final class MessageSenderPoolController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderPoolController.class);

    private MessageSenderPool pool;

    private static MessageSenderPoolController ourInstance = new MessageSenderPoolController();

    public static MessageSenderPoolController getInstance() {
        return ourInstance;
    }

    private MessageSenderPoolController() {
        preparePools();
    }

    private void preparePools() {
        if (LOG.isInfoEnabled()) {
            LOG.info("Preparing the MessageSender pool...");
        }

        initPool();

        int poolSizeEmail = PropertiesUtil.getIntProperty(POOL_SIZE_EMAIL);
        int poolSizeSms = PropertiesUtil.getIntProperty(POOL_SIZE_SMS);
        int poolSizePush = PropertiesUtil.getIntProperty(POOL_SIZE_PUSH);

        try {
            for (int i = 0; i < poolSizeEmail; ++i) {
                pool.addObject(MessageType.EMAIL);
            }
            if (LOG.isInfoEnabled()) {
                LOG.info("Email Sender pool created.");
            }
            for (int i = 0; i < poolSizeSms; ++i) {
                pool.addObject(MessageType.SMS);
            }
            if (LOG.isInfoEnabled()) {
                LOG.info("Sms Sender pool created.");
            }
            for (int i = 0; i < poolSizePush; ++i) {
                pool.addObject(MessageType.PUSH);
            }
            if (LOG.isInfoEnabled()) {
                LOG.info("Notification Sender pool created.");
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Error while creating the pool of MessageSender objects.", e);
            }
        }
    }

    private void initPool() {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        MessageSenderPoolFactory poolFactory = new MessageSenderPoolFactory();
        pool = new MessageSenderPool(poolFactory, config);

        pool.setMinEvictableIdleTimeMillis(1000);
        pool.setTimeBetweenEvictionRunsMillis(600);

        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        pool.setMaxIdlePerKey(1);
        pool.setMaxTotal(30);
        pool.setMaxTotalPerKey(10);
    }

    public MessageSenderPool getPool() {
        return pool;
    }
}
