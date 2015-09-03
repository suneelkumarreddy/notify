package in.notwork.notify.server.pool;

import in.notwork.notify.client.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static in.notwork.notify.client.util.NotifyConstants.*;

/**
 * @author rishabh.
 */
public final class MessageSenderPoolFactoryHelper {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderPoolFactory.class);

    private MessageSenderPoolFactoryHelper() {
    }

    public static String getConfiguredImplementation(MessageType messageType) {
        String classToUse;
        switch (messageType) {
            case EMAIL:
                classToUse = PropertiesUtil.getProperty(EMAIL_SENDER_IMPL);
                break;
            case SMS:
                classToUse = PropertiesUtil.getProperty(SMS_SENDER_IMPL);
                break;
            default:
                classToUse = PropertiesUtil.getProperty(NOTIF_SENDER_IMPL);
                break;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("For message type: " + messageType + ", using configured implementation: + " + classToUse);
        }
        return classToUse;
    }

    private static Map<String, String> getPushConfiguration() {
        Map<String, String> config = new HashMap<>();
        // TODO Get push config from properties
        return config;
    }

    private static Map<String, String> getSmsConfiguration() {
        Map<String, String> config = new HashMap<>();
        // TODO Get sms config from properties
        return config;
    }

    private static Map<String, String> getEmailConfiguration() {
        Map<String, String> config = new HashMap<>();

        config.put(MAIL_USERNAME, PropertiesUtil.getProperty(MAIL_USERNAME));
        config.put(MAIL_PASSWORD, PropertiesUtil.getProperty(MAIL_PASSWORD));
        config.put(MAIL_DEBUG, PropertiesUtil.getProperty(MAIL_DEBUG));

        config.put(MAIL_SMTP_AUTH, PropertiesUtil.getProperty(MAIL_SMTP_AUTH));
        config.put(MAIL_SMTP_STARTTTLS_ENABLE, PropertiesUtil.getProperty(MAIL_SMTP_STARTTTLS_ENABLE));
        config.put(MAIL_SMTP_HOST, PropertiesUtil.getProperty(MAIL_SMTP_HOST));
        config.put(MAIL_SMTP_SOCK_FACTORY_PORT, PropertiesUtil.getProperty(MAIL_SMTP_SOCK_FACTORY_PORT));
        config.put(MAIL_SMTP_SOCK_FACTORY_CLASS, PropertiesUtil.getProperty(MAIL_SMTP_SOCK_FACTORY_CLASS));
        config.put(MAIL_SMTP_PORT, PropertiesUtil.getProperty(MAIL_SMTP_PORT));

        return config;
    }

    public static Map<String, String> loadConfiguration(MessageType messageType) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loading configuration from properties for " + messageType + " sender...");
        }
        Map<String, String> config = null;
        switch (messageType) {
            case EMAIL:
                config = getEmailConfiguration();
                break;
            case SMS:
                config = getSmsConfiguration();
                break;
            default:
                config = getPushConfiguration();
                break;
        }
        return config;
    }
}
