package cf.epiphania.foresight.util;

import org.jasypt.util.text.BasicTextEncryptor;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailOTP {

    private static final String host = "smtp-mail.outlook.com";
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static String sendOTP(String from, String to, String username, String password) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props);

        Random random = new Random();
        String otp = random.nextInt(10) + Integer.toString(random.nextInt(10)) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);

        MimeMessage message = makeEmail(session, otp, from, to, username);

        pool.submit(() -> {
            try {
                sendEmail(session, from, password, message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(otp);

        return otp;
    }

    public static MimeMessage makeEmail(Session session, String otp, String from, String to, String username) throws Exception {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from, "Foresight", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
        message.setSubject("[Foresight] " + otp + " is your one-time password");

        String timeStamp = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH).format(new Date(new Date().getTime() + 15 * 60 * 1000));
        message.setText("Hello " + username + ",\n" + "Please use " + otp + " to complete the registration process.\n" + "The code will expire in 15 minutes (" + timeStamp + ")");
        message.saveChanges();
        return message;
    }

    private static void sendEmail(Session session, String from, String password, MimeMessage message) throws Exception {
        try (Transport transport = session.getTransport()) {
            transport.connect(from, password);
            transport.sendMessage(message, message.getAllRecipients());
        }
    }

    public static void main(String[] args) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("Fedele@130");

        String username = encryptor.encrypt("WYX20080130");

        System.out.println(username);
    }

}
