package in.notwork.notify.server.notify.client.samples;

import in.notwork.notify.client.Notify;
import in.notwork.notify.client.message.Email;
import in.notwork.notify.client.message.Notification;

import java.io.IOException;

/**
 * @author rishabh.
 */
public class App2 {

    public static void main(String[] args) {
        try {
            Notify.send(new Notification().channel("wow").message("bow").build());
            Notify.send(new Email().to("","").from("","").bcc("").cc("").subject("").body("").build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
