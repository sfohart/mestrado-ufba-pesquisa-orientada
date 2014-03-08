package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean(name="mailMB")
@ViewScoped
public class SendMailManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1321840413856596153L;
	
	public class MailOptions implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5416955965983507032L;
		private String contactName;
		private String contactAddress;
		private String contactSubject;
		private String messageText;
		
		private boolean sendCopy;

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getContactAddress() {
			return contactAddress;
		}

		public void setContactAddress(String contactAddress) {
			this.contactAddress = contactAddress;
		}
		
		public String getContactSubject() {
			return contactSubject;
		}
		
		public void setContactSubject(String contactSubject) {
			this.contactSubject = contactSubject;
		}

		public String getMessageText() {
			return messageText;
		}

		public void setMessageText(String messageText) {
			this.messageText = messageText;
		}

		public boolean isSendCopy() {
			return sendCopy;
		}

		public void setSendCopy(boolean sendCopy) {
			this.sendCopy = sendCopy;
		}
		
	}
	
	private MailOptions mailOptions;
	private boolean successSendingMail;
	private Properties properties;
	
	
	public SendMailManagedBean() {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties");
		properties = new Properties();
		
		if (is != null) {
			try {
				properties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.successSendingMail = false;
		clearMail();
	}
	
	public MailOptions getMailOptions() {
		return mailOptions;
	}
	
	public void setMailOptions(MailOptions mailOptions) {
		this.mailOptions = mailOptions;
	}
	
	public boolean isSuccessSendingMail() {
		return successSendingMail;
	}
	
	public void setSuccessSendingMail(boolean successSendingMail) {
		this.successSendingMail = successSendingMail;
	}

	public void sendMail(ActionEvent event) {		
		
		try {
			
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

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(getMailOptions().getContactAddress())); // Remetente

			message.setSentDate(new Date());
			
			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(properties.getProperty("mail.to"));
			
			if (getMailOptions().isSendCopy()) {
				Address[] cc = InternetAddress.parse(getMailOptions().getContactAddress());
				message.addRecipients(RecipientType.CC, cc);
			}

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(getMailOptions().getContactSubject());
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(String.format("Email enviado por %s:", getMailOptions().getContactName()));
			buffer.append("\n");
			buffer.append(getMailOptions().getMessageText());
			
			message.setText(buffer.toString());
			
			Transport.send(message);

			FacesMessage successMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem enviada com sucesso", "Obrigado pela sua contribuição!");
			FacesContext.getCurrentInstance().addMessage(
					null, 
					successMessage
					);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
						
		clearMail();
		
		this.successSendingMail = true;
	}
	
	public void clearMail(ActionEvent event) {
		clearMail();
	}

	private void clearMail() {
		this.mailOptions = new MailOptions();
	}

}
