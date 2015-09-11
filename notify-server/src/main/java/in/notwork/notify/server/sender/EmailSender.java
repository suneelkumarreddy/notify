package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static in.notwork.notify.client.util.NotifyConstants.*;

/**
 * {@link MessageSender} to send Emails.
 *
 * @author rishabh.
 */
public class EmailSender extends MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(EmailSender.class);

    private Session session;

    /**
     * Constructor.
     *
     * @param config Configuration for sending email.
     */
    public EmailSender(HashMap<String, String> config) {
        super(config);
        prepareSession();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(MessageProto.Message message) {
        LOG.debug("Preparing email...");

        try {

            Message mimeMessage = new MimeMessage(session);

            String fromAddress = message.getEmail().getFromEmail();
            mimeMessage.setFrom(new InternetAddress(fromAddress));

            String toCsv = getToList(message);
            if (!toCsv.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(toCsv));
            }

            String ccCsv = getCcList(message);
            if (!ccCsv.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(ccCsv));
            }

            String bccCsv = getBccList(message);
            if (!bccCsv.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(bccCsv));
            }

            String subject = message.getEmail().getSubject();
            mimeMessage.setSubject(subject);

            prepareBody(message, mimeMessage);

            Transport.send(mimeMessage);

            LOG.debug("Email sent - SUBJ: {}, TO: {}, CC: {}, BCC: {}, FROM: {}",
                    subject, toCsv, ccCsv, bccCsv, fromAddress);

        } catch (MessagingException e) {
            LOG.error("Unable to send email", e);
            throw new RuntimeException(e);
        }

    }

    private void prepareBody(MessageProto.Message message, Message mimeMessage) throws MessagingException {
        Multipart multipart = new MimeMultipart();
        BodyPart part = new MimeBodyPart();
        part.setText(message.getEmail().getBody());
        multipart.addBodyPart(part);

        if (message.getEmail().getAttachmentCount() > 0) {
            List<MessageProto.Attachment> attachments = message.getEmail().getAttachmentList();
            for (MessageProto.Attachment attachment : attachments) {
                BodyPart bodyPart = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(
                        attachment.getContent().toByteArray(),
                        attachment.getContentType());
                bodyPart.setDataHandler(new DataHandler(source));
                bodyPart.setFileName(attachment.getName());
                multipart.addBodyPart(bodyPart);
            }
        }

        mimeMessage.setContent(multipart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        LOG.debug("Destroying email session...");
        session = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        boolean flag = false;
        if (null != session) {
            flag = true;
        }
        return flag;
    }

    private String getToList(MessageProto.Message message) {
        int count = message.getEmail().getToEmailCount();
        StringBuilder toCsv = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            toCsv.append(message.getEmail().getToEmail(i));
            if (i < count - 1) {
                toCsv.append(",");
            }
        }
        return toCsv.toString();
    }

    private String getBccList(MessageProto.Message message) {
        int count = message.getEmail().getBccEmailCount();
        StringBuilder bccCsv = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            bccCsv.append(message.getEmail().getBccEmail(i));
            if (i < count - 1) {
                bccCsv.append(",");
            }
        }
        return bccCsv.toString();
    }

    private String getCcList(MessageProto.Message message) {
        int count = message.getEmail().getCcEmailCount();
        StringBuilder ccCsv = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            ccCsv.append(message.getEmail().getCcEmail(i));
            if (i < count - 1) {
                ccCsv.append(",");
            }
        }
        return ccCsv.toString();
    }

    private void prepareSession() {
        String username = config.get(MAIL_USERNAME);
        String password = config.get(MAIL_PASSWORD);

        Properties properties = new Properties();
        for (Map.Entry<String, String> entry : config.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        session.setDebug(Boolean.parseBoolean(config.get(MAIL_DEBUG)));
    }

}
