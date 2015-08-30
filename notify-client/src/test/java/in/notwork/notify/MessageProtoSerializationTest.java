package in.notwork.notify;

import com.google.protobuf.InvalidProtocolBufferException;
import in.notwork.notify.client.message.*;
import in.notwork.notify.protos.MessageProto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class MessageProtoSerializationTest {

    private static final Log log = LogFactory.getLog(MessageProtoSerializationTest.class);

    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code
        log.info("@BeforeClass - oneTimeSetUp");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
        log.info("@AfterClass - oneTimeTearDown");
    }

    @Before
    public void setUp() {
        log.info("@Before - setUp");
    }

    @After
    public void tearDown() {
        log.info("@After - tearDown");
    }

    @Test
    public void testMessagePush() throws InvalidProtocolBufferException {
        MessageProto.Message before04 = new Notification()
                .channel("location")
                .message("Hello World!")
                .build();
        byte[] serialized04 = before04.toByteArray();
        MessageProto.Message after04 = MessageProto.Message.parseFrom(serialized04);
        assertEquals("Test 04", before04, after04);
    }

    @Test
    public void testMessageSms() throws InvalidProtocolBufferException {
        MessageProto.Message before03 = new Sms()
                .to("+911234567890")
                .from("+919876543210")
                .body("Hello Lily!")
                .priority(MessagePriority.MEDIUM)
                .status(MessageStatus.IN)
                .build();
        byte[] serialized03 = before03.toByteArray();
        MessageProto.Message after03 = MessageProto.Message.parseFrom(serialized03);
        assertEquals("Test 03", before03, after03);
    }

    @Test
    public void testMessageEmail() throws InvalidProtocolBufferException {
        MessageProto.Message before02 = new Email()
                .to("John Doe", "john.doe@mailinator.com")
                .cc("angel.jane@mailinator.com")
                .cc("melodrama.tom@mailinator.com")
                .bcc("hero.sam@mailinator.com")
                .bcc("peter.pan@mailinator.com")
                .from("Lily Dee", "lilly.dee@mailinator.com")
                .subject("Hi!")
                .body("Nice dress ;)")
                .build();
        byte[] serialized02 = before02.toByteArray();
        MessageProto.Message after02 = MessageProto.Message.parseFrom(serialized02);
        assertEquals("Test 02", before02, after02);
    }

    @Test
    public void testMessageEmailSimple() throws InvalidProtocolBufferException {
        MessageProto.Message before01 = new Email()
                .to("John Doe", "john.doe@mailinator.com")
                .from("Lily Dee", "lilly.dee@mailinator.com")
                .subject("Hi!")
                .body("Nice dress ;)")
                .build();
        byte[] serialized01 = before01.toByteArray();
        MessageProto.Message after01 = MessageProto.Message.parseFrom(serialized01);
        assertEquals("Test 01", before01, after01);
    }
}
