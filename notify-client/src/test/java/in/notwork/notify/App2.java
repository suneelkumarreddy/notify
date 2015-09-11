package in.notwork.notify;

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
//            Notify.send(new Notification().channel("wow").message("bow").build());
            Email email = new Email()
                    .to("john.doe@mailinator.com")
                    .from("lily.jane@mailinator.com")
                    .bcc("funny.jack@mailinator.com")
                    .cc("weeping.tom@mailinator.com")
                    .cc("mary.anne@mailinator.com")
                    .subject("Hello All!").body("Hi, it's been a long time!");
            Notify.send(email.build());

            Notification notif = new Notification().channel("").message("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
