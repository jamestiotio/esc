import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.StringWriter;
import java.util.Random;

public class MailClientTest {
    private MailClient client;

    public MailClientTest() {
        client = new MailClient();
    }

    @Before
    public void setupTest() {
        client = new MailClient();
    }

    @After
    public void tearDownTest() {
        client = null;
    }

    // Legitimate values in all fields (only this test should pass)
    @Test
    public void successTest() {
        client.outgoingServerAddress = "mail.sutd.edu.sg";
        client.incomingServerAddress = "imap.sutd.edu.sg";
        client.emailAddress = "james_raphael@mymail.sutd.edu.sg";
        client.subject = "Spectacular Work, Big Boss!";
        assertEquals(true, client.send());
    }

    // Problematic outgoingServerAddress
    @Test
    public void failureTest1() {
        client.outgoingServerAddress = null;
        client.incomingServerAddress = "imap.sutd.edu.sg";
        client.emailAddress = "james_raphael@mymail.sutd.edu.sg";
        client.subject = "Spectacular Work, Big Boss!";
        assertEquals(false, client.send());
    }

    // Problematic incomingServerAddress
    @Test
    public void failureTest2() {
        client.outgoingServerAddress = "mail.sutd.edu.sg";
        client.incomingServerAddress = null;
        client.emailAddress = "james_raphael@mymail.sutd.edu.sg";
        client.subject = "Spectacular Work, Big Boss!";
        assertEquals(false, client.send());
    }

    // Problematic emailAddress
    @Test
    public void failureTest3() {
        client.outgoingServerAddress = "mail.sutd.edu.sg";
        client.incomingServerAddress = "imap.sutd.edu.sg";
        client.emailAddress = null;
        client.subject = "Spectacular Work, Big Boss!";
        assertEquals(false, client.send());
    }

    // Subject size over the maximum size limit
    @Test
    public void failureTest4() {
        Random r = new Random();
        String hugeString;
        StringWriter writer = new StringWriter();

        for (int i = 0; i < 1000000; i++) {
            writer.append(Integer.toString(r.nextInt()));
        }

        hugeString = writer.toString();

        client.outgoingServerAddress = "mail.sutd.edu.sg";
        client.incomingServerAddress = "imap.sutd.edu.sg";
        client.emailAddress = "james_raphael@mymail.sutd.edu.sg";
        client.subject = hugeString;
        assertEquals(false, client.send());
    }

    // Problematic emailAddress
    @Test
    public void failureTest5() {
        client.outgoingServerAddress = "mail.sutd.edu.sg";
        client.incomingServerAddress = "imap.sutd.edu.sg";
        client.emailAddress = "james_raphael";
        client.subject = "Spectacular Work, Big Boss!";
        assertEquals(false, client.send());
    }
}
