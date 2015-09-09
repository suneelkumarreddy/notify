package in.notwork.notify.server.pool;

import in.notwork.notify.client.message.MessageType;
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
        LOG.debug("For message type: {}, using configured implementation: {}", messageType, classToUse);
        return classToUse;
    }

    private static Map<String, String> getPushConfiguration() {
        Map<String, String> config = new HashMap<>();
        config.put(FAYE_HOST, PropertiesUtil.getProperty(FAYE_HOST));
        config.put(FAYE_PORT, PropertiesUtil.getProperty(FAYE_PORT));
        String host = PropertiesUtil.getProperty(PROXY_HOST);
        String port = PropertiesUtil.getProperty(PROXY_PORT);
        if (null != host && !host.isEmpty()) {
            config.put(PROXY_HOST, PropertiesUtil.getProperty(PROXY_HOST));
        }
        if (null != port && !port.isEmpty()) {
            config.put(PROXY_PORT, PropertiesUtil.getProperty(PROXY_PORT));
        }
        return config;
    }

    private static Map<String, String> getSmsConfiguration() {
        Map<String, String> config = new HashMap<>();
        config.put(KAPSYS_URL, PropertiesUtil.getProperty(KAPSYS_URL));
        config.put(KAPSYS_WORKING_KEY, PropertiesUtil.getProperty(KAPSYS_WORKING_KEY));
        config.put(KAPSYS_SENDER_ID, PropertiesUtil.getProperty(KAPSYS_SENDER_ID));
        return config;
    }

    private static Map<String, String> getEmailConfiguration() {

        Map<String, String> config = new HashMap<>();

        config.put(MAIL_USERNAME, PropertiesUtil.getProperty(MAIL_USERNAME));
        config.put(MAIL_PASSWORD, PropertiesUtil.getProperty(MAIL_PASSWORD));
        config.put(MAIL_DEBUG, PropertiesUtil.getProperty(MAIL_DEBUG));
        config.put(MAIL_SMTP_AUTH, PropertiesUtil.getProperty(MAIL_SMTP_AUTH));
        config.put(MAIL_SMTP_HOST, PropertiesUtil.getProperty(MAIL_SMTP_HOST));
        config.put(MAIL_SMTP_PORT, PropertiesUtil.getProperty(MAIL_SMTP_PORT));

        final boolean tlsFlag = PropertiesUtil.getBooleanProperty(MAIL_SMTP_OVER_TLS);
        final boolean sslFlag = PropertiesUtil.getBooleanProperty(MAIL_SMTP_OVER_SSL);
        if (tlsFlag) {
            config.put(MAIL_SMTP_OVER_TLS, PropertiesUtil.getProperty(MAIL_SMTP_OVER_TLS));
            config.put(MAIL_SMTP_STARTTTLS_ENABLE, PropertiesUtil.getProperty(MAIL_SMTP_STARTTTLS_ENABLE));
        }
        if (sslFlag) {
            config.put(MAIL_SMTP_OVER_SSL, PropertiesUtil.getProperty(MAIL_SMTP_OVER_SSL));
            config.put(MAIL_SMTP_SOCK_FACTORY_PORT, PropertiesUtil.getProperty(MAIL_SMTP_SOCK_FACTORY_PORT));
            config.put(MAIL_SMTP_SOCK_FACTORY_CLASS, PropertiesUtil.getProperty(MAIL_SMTP_SOCK_FACTORY_CLASS));
        }

        return config;
    }

    public static Map<String, String> loadConfiguration(MessageType messageType) {
        LOG.debug("Loading configuration from properties for {} sender...", messageType);
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
