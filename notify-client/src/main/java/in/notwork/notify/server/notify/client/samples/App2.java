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
//            Notify.send(new Notification().channel("wow").message("bow").build());
            Email email = new Email()
                    .to("Suneel Kumar Reddy", "suneelkumar.reddy@symphonyteleca.com")
                    .from("Suneel Kumar Reddy", "reddysuneelkumar@gmail.com")
                    .subject("Hello Madhu!").body("Hi, it's been a long time!");
            Notify.send(email.build());

            Notification notif = new Notification().channel("").message("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
