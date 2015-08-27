package in.notwork.notify.client.builder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rishabh.
 */
public class AbstractMessage {

    protected String _toEmailId;
    protected String _fromEmailId;
    protected String _toPhoneNumber;
    protected String _fromPhoneNumber;
    protected String _toName;
    protected String _fromName;
    protected String _subject;
    protected String _body;
    protected String _messageId;
    protected MessageType _type;
    protected MessageStatus _status;
    protected MessagePriority _priority;
    protected Map<String, String> _messageMap = new HashMap<>();
    protected String _template;
}
