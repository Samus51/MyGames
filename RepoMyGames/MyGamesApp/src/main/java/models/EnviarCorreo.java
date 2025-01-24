package models;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreo {

  public void enviarCorreo(String emailFrom, String passwordFrom, String emailTo, String subject, String content)
      throws MessagingException {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.user", emailFrom);
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

    Session session = Session.getInstance(properties);
    MimeMessage message = new MimeMessage(session);

    message.setFrom(new InternetAddress(emailFrom));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
    message.setSubject(subject);
    message.setText(content, "ISO-8859-1", "html");

    Transport transport = session.getTransport("smtp");
    transport.connect(emailFrom, passwordFrom);
    transport.sendMessage(message, message.getAllRecipients());
    transport.close();
  }
}
