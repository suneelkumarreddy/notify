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
        LOG.info("Preparing the MessageSender pool...");

        initPool();

        int poolSizeEmail = PropertiesUtil.getIntProperty(POOL_SIZE_EMAIL);
        int poolSizeSms = PropertiesUtil.getIntProperty(POOL_SIZE_SMS);
        int poolSizePush = PropertiesUtil.getIntProperty(POOL_SIZE_PUSH);

        try {
            for (int i = 0; i < poolSizeEmail; ++i) {
                pool.addObject(MessageType.EMAIL);
            }
            LOG.info("Email Sender pool created.");
            for (int i = 0; i < poolSizeSms; ++i) {
                pool.addObject(MessageType.SMS);
            }
            LOG.info("Sms Sender pool created.");
            for (int i = 0; i < poolSizePush; ++i) {
                pool.addObject(MessageType.PUSH);
            }
            LOG.info("Notification Sender pool created.");
        } catch (Exception e) {
            LOG.error("Error while creating the pool of MessageSender objects.", e);
        }
    }

    private void initPool() {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        LOG.debug("config.setTestOnBorrow(true)");
        LOG.debug("config.setTestOnReturn(true)");

        MessageSenderPoolFactory poolFactory = new MessageSenderPoolFactory();
        pool = new MessageSenderPool(poolFactory, config);

        pool.setMinEvictableIdleTimeMillis(1000);
        pool.setTimeBetweenEvictionRunsMillis(600);
        pool.setMaxIdlePerKey(1);
        pool.setMaxTotal(30);
        pool.setMaxTotalPerKey(10);

        LOG.debug("pool.setMinEvictableIdleTimeMillis(1000)");
        LOG.debug("pool.setTimeBetweenEvictionRunsMillis(600)");
        LOG.debug("pool.setMaxIdlePerKey(1)");
        LOG.debug("pool.setMaxTotal(30)");
        LOG.debug("pool.setMaxTotalPerKey(10)");
    }

    public MessageSenderPool getPool() {
        return pool;
    }
}
