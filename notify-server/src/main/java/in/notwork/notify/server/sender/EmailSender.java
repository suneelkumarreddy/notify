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
 * @author rishabh.
 */
public class EmailSender extends MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(EmailSender.class);

    private Session session;

    public EmailSender(HashMap<String, String> config) {
        super(config);
        prepareSession();
    }

    @Override
    public void send(MessageProto.Message message) {
        LOG.debug("Preparing email...");

        try {

            Message mimeMessage = new MimeMessage(session);

            String fromAddress = message.getSender().getEmailId();
            mimeMessage.setFrom(new InternetAddress(fromAddress));

            String toAddress = message.getReceiver().getEmailId();
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress));

            String[] ccList = new String[message.getReceiver().getCcCount()];
            for (int i = 0; i < message.getReceiver().getCcCount(); i++) {
                ccList[i] = message.getReceiver().getCc(i);
            }

            String ccCsv = getCcList(message);

            String bccCsv = getBccList(message);

            if (null != ccCsv && !ccCsv.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(ccCsv));
            }

            if (null != bccCsv && !bccCsv.isEmpty()) {
                mimeMessage.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(bccCsv));
            }

            String subject = message.getContent().getSubject();
            mimeMessage.setSubject(subject);

            prepareBody(message, mimeMessage);

            Transport.send(mimeMessage);

            LOG.debug("Email sent - SUBJ: {}, TO: {}, FROM: {}", subject, toAddress, fromAddress);

        } catch (MessagingException e) {
            LOG.error("Unable to send email", e);
            throw new RuntimeException(e);
        }

    }

    private void prepareBody(MessageProto.Message message, Message mimeMessage) throws MessagingException {
        Multipart multipart = new MimeMultipart();
        BodyPart part = new MimeBodyPart();
        part.setText(message.getContent().getBody());
        multipart.addBodyPart(part);

        if (message.getContent().getAttachmentCount() > 0) {
            List<MessageProto.Attachment> attachments = message.getContent().getAttachmentList();
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

    @Override
    public void destroy() {
        LOG.debug("Destroying email session...");
        session = null;
    }

    @Override
    public boolean isValid() {
        boolean flag = false;
        if (null != session) {
            flag = true;
        }
        return flag;
    }

    private String getBccList(MessageProto.Message message) {
        int count = message.getReceiver().getBccCount();
        StringBuilder bccCsv = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            bccCsv.append(message.getReceiver().getBcc(i));
            if (i < count - 1) {
                bccCsv.append(",");
            }
        }
        return bccCsv.toString();
    }

    private String getCcList(MessageProto.Message message) {
        int count = message.getReceiver().getCcCount();
        StringBuilder ccCsv = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            ccCsv.append(message.getReceiver().getCc(i));
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
