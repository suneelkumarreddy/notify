package in.notwork.notify.client.util;

/**
 * Constants used in Notify.
 *
 * @author rishabh.
 */
public class NotifyConstants {

    // Generic constants.
    public static final String QUEUE_IMPL = "queue.impl";
    public static final String QUEUE_NAME = "queue.name";
    public static final String QUEUE_USERNAME = "queue.username";
    public static final String QUEUE_PASSWORD = "queue.password";
    public static final String QUEUE_HOST = "queue.host";

    // Constants specific to RabbitMQ implementation.
    public static final String RMQ_DURABLE = "rmq.queue.durable";
    public static final String RMQ_EXCLUSIVE = "rmq.queue.exclusive";
    public static final String RMQ_AUTO_DELETE = "rmq.queue.autoDelete";

    public static final String EMAIL_SENDER_IMPL = "emailSender.impl";
    public static final String SMS_SENDER_IMPL = "smsSender.impl";
    public static final String NOTIF_SENDER_IMPL = "notificationSender.impl";

    public static final String MAIL_USERNAME = "mail.username";
    public static final String MAIL_PASSWORD = "mail.password";
    public static final String MAIL_DEBUG = "mail.debug";

    public static final String MAIL_SMTP_OVER_SSL = "mail.smtp.over.ssl";
    public static final String MAIL_SMTP_OVER_TLS = "mail.smtp.over.tls";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_SOCK_FACTORY_PORT = "mail.smtp.socketFactory.port";
    public static final String MAIL_SMTP_SOCK_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";

    public static final String POOL_SIZE_EMAIL = "pool.size.email";
    public static final String POOL_SIZE_SMS = "pool.size.sms";
    public static final String POOL_SIZE_PUSH = "pool.size.push";

    public static final String FAYE_HOST = "faye.host";
    public static final String FAYE_PORT = "faye.port";

    public static final String PROXY_HOST = "proxy.host";
    public static final String PROXY_PORT = "proxy.port";
}
