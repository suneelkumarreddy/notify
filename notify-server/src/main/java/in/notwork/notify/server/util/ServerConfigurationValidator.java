package in.notwork.notify.server.util;

import in.notwork.notify.client.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static in.notwork.notify.client.util.NotifyConstants.*;

/**
 * @author rishabh.
 */
public final class ServerConfigurationValidator {

    private static final Logger LOG = LoggerFactory.getLogger(ServerConfigurationValidator.class);

    private static final String[] keysToCheck = new String[]{
            QUEUE_IMPL, QUEUE_NAME, QUEUE_USERNAME, QUEUE_PASSWORD, QUEUE_HOST,
            RMQ_DURABLE, RMQ_EXCLUSIVE, RMQ_AUTO_DELETE,
            EMAIL_SENDER_IMPL, SMS_SENDER_IMPL, NOTIF_SENDER_IMPL,
            MAIL_USERNAME, MAIL_PASSWORD, MAIL_DEBUG,
            MAIL_SMTP_OVER_SSL, MAIL_SMTP_OVER_TLS,
            MAIL_SMTP_AUTH, MAIL_SMTP_HOST, MAIL_SMTP_PORT,
            POOL_SIZE_EMAIL, POOL_SIZE_SMS, POOL_SIZE_PUSH
    };

    private static ServerConfigurationValidator ourInstance = new ServerConfigurationValidator();

    public static ServerConfigurationValidator getInstance() {
        return ourInstance;
    }

    private ServerConfigurationValidator() {
    }

    public void validate() throws IllegalStateException {
        LOG.debug("Validating the server properties...");
        validateForEmptyOrMultipleValues();
        validateSMTPProperties();
    }

    private void validateSMTPProperties() {
        final boolean tlsFlag = PropertiesUtil.getBooleanProperty(MAIL_SMTP_OVER_TLS);
        final boolean sslFlag = PropertiesUtil.getBooleanProperty(MAIL_SMTP_OVER_SSL);
        if (tlsFlag && sslFlag) {
            LOG.error("Both SMTP-TLS and SMTP-SSL are enabled");
            throw new IllegalStateException("Cannot enable both SMTP-TLS and SMTP-SSL");
        }
        if (!tlsFlag && !sslFlag) {
            LOG.error("Both SMTP-TLS and SMTP-SSL are disabled. Please enable one.");
            throw new IllegalStateException("Cannot disable both SMTP-TLS and SMTP-SSL. Please enable one.");
        }
        if (tlsFlag) {
            checkProperty(MAIL_SMTP_STARTTTLS_ENABLE);
        }
        if (sslFlag) {
            checkProperty(MAIL_SMTP_SOCK_FACTORY_PORT);
            checkProperty(MAIL_SMTP_SOCK_FACTORY_CLASS);
        }
    }

    private void validateForEmptyOrMultipleValues() {
        for (int i = 0; i < keysToCheck.length; i++) {
            try {
                checkProperty(keysToCheck[i]);
            } catch (ClassCastException e) {
                LOG.error("It seems more than one value has been configured for property: {}", keysToCheck[i]);
                throw new IllegalStateException("Multiple values configured for property: " + keysToCheck[i], e);
            }
        }
    }

    private void checkProperty(final String key) {
        if (!isValidProperty(key)) {
            LOG.error("Property {} cannot be empty.", key);
            throw new IllegalStateException("Property " + key + " cannot be empty");
        }
    }

    private boolean isValidProperty(final String key) {
        boolean flag = true;
        final String property = PropertiesUtil.getProperty(key);
        if (null == property || property.isEmpty()) {
            flag = false;
        }
        return flag;
    }
}
