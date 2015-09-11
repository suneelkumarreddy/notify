package in.notwork.notify;

import com.google.protobuf.InvalidProtocolBufferException;
import in.notwork.notify.protos.MessageProto;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class MessageProtoTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MessageProtoTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MessageProtoTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        MessageProto.Message before = MessageProto.Message.newBuilder()
                .setEmail(
                        MessageProto.Email.newBuilder()
                                .addToEmail("john.doe@mailinator.com")
                                .setFromEmail("lily.dee@mailinator.com")
                                .setSubject("Hello!")
                                .setBody("Hello Lily!")
                                .build()
                )
                .setMessageId(UUID.randomUUID().toString())
                .setPriority(MessageProto.Priority.LOW)
                .setStatus(MessageProto.Status.IN)
                .setType(MessageProto.Type.EMAIL)
                .build();

        // System.out.println(before.toString());

        byte[] serialized = before.toByteArray();

        MessageProto.Message after = null;
        try {
            after = MessageProto.Message.parseFrom(serialized);
        } catch (InvalidProtocolBufferException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }

        assertEquals(before, after);
    }
}
