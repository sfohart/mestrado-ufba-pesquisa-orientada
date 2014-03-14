import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.BeforeClass;
import org.junit.Test;


public class SendMailTest {
	
	
	private static Properties properties;


	@BeforeClass
	public static void testSetup() throws IOException {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties");
		properties = new Properties();
		if (is != null) {
			properties.load(is);
		}
	}
	
	
	@Test
	public void testSendingMail() {
		Session session = Session.getDefaultInstance(properties,
            new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() 
                 {
                       return new PasswordAuthentication(
                    		   properties.getProperty("mail.to"), 
                    		   properties.getProperty("mail.password"));
                 }
            });
		
		session.setDebug(true);
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("teste@gmail.com")); // Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(properties.getProperty("mail.to"));

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Enviando email com JavaMail");// Assunto
			message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
			/** Método para enviar a mensagem criada */
			Transport.send(message);

			System.out.println("Feito!!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
