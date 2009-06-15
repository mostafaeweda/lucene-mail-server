package test;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

public class Main {
  public static void main(String[] args) throws AddressException, MessagingException {
    String from = "mostafa_eweda17@yahoo.com";
    String to = "mostafa_eweda17@yahoo.com";
    String subject = "Hi There...";
    String text = "How are you?";

    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.some-domain.com");
    properties.put("mail.smtp.port", "25");
    Session session = Session.getDefaultInstance(properties, null);

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(subject);
    message.setText(text);

    Transport.send(message);
  }
}