package in.notwork.notify.server.sender;

import in.notwork.notify.protos.MessageProto;
import in.notwork.notify.server.response.KapSysSMSResponse;
import in.notwork.notify.server.util.KapSysUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link MessageSender} to send SMS.
 *
 * @author rishabh.
 */
public class SmsSender extends MessageSender {

    private static final Log log = LogFactory.getLog(SmsSender.class);

    /**
     * Constructor.
     *
     * @param config Configuration for sending SMS.
     */
    public SmsSender(HashMap<String, String> config) {
        super(config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(MessageProto.Message message) {

        try {
            List<KapSysSMSResponse> kapSysSMSResponseList = KapSysUtil.sendKapSysSMS(config, message.getSms().Receiver().getPhoneNumber(), message.getContent().toString());
            log.info(kapSysSMSResponseList);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        } catch (KeyManagementException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return false;
    }
}
