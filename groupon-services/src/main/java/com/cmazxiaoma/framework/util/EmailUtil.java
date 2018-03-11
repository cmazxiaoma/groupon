package com.cmazxiaoma.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 邮件工具类
 */
public class EmailUtil {
	private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	private static final ResourceBundle EMAIL_PROPS = ResourceBundle.getBundle("email", Locale.SIMPLIFIED_CHINESE);

	public static final int CONTENT_TYPE_TEXT = 1;

	public static final int CONTENT_TYPE_HTML = 2;

	/**
	 * 发送邮件
	 * @param recipients 收件人列表
	 * @param subject 主题
	 * @param content 正文
	 */
	public static void sendEmail(String[] recipients, String subject, String content, int contentType) {
		if (recipients == null || recipients.length == 0) {
			logger.info("发送邮件，收件人列表为空！");
			return;
		}

		if (!StringUtils.hasText(subject)) {
			logger.info("发送邮件，邮件主题为空！");
			return;
		}

		if (!StringUtils.hasText(content)) {
			logger.info("发送邮件，邮件正文为空！");
			return;
		}

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", EMAIL_PROPS.getString("mail.smtp.host"));
			props.put("mail.smtp.port", EMAIL_PROPS.getString("mail.smtp.port"));
			props.put("mail.smtp.socketFactory.class", EMAIL_PROPS.getString("mail.smtp.socketFactory.class"));
			props.put("mail.smtp.socketFactory.port", EMAIL_PROPS.getString("mail.smtp.socketFactory.port"));
			props.put("mail.smtp.socketFactory.fallback", EMAIL_PROPS.getString("mail.smtp.socketFactory.fallback"));
			props.put("mail.smtp.auth", EMAIL_PROPS.getString("mail.smtp.auth"));


			Session session = Session.getInstance(props, new EmailAuthenticator(EMAIL_PROPS.getString("username"), EMAIL_PROPS.getString("password")));
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(EMAIL_PROPS.getString("from")));

			InternetAddress[] address = new InternetAddress[recipients.length];
			for (int i = 0; i < address.length; i++) {
				address[i] = new InternetAddress(recipients[i]);
			}

			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			if (contentType == CONTENT_TYPE_TEXT) {
				msg.setText(content);
			} else {
				msg.setDataHandler(new DataHandler(new ByteArrayDataSource(content, "text/html")));
			}

			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();

			Exception ex = mex;
			do {
				if (ex instanceof SendFailedException) {
					SendFailedException sfex = (SendFailedException) ex;

					Address[] invalid = sfex.getInvalidAddresses();
					if (invalid != null) {
						logger.info("Invalid Addresses:");
						for (int i = 0; i < invalid.length; i++) {
							logger.info("	" + invalid[i]);
						}
					}

					Address[] validUnsent = sfex.getValidUnsentAddresses();
					if (validUnsent != null) {
						logger.info("ValidUnsent Addresses:");
						for (int i = 0; i < validUnsent.length; i++) {
							logger.info("	" + validUnsent[i]);
						}
					}

					Address[] validSent = sfex.getValidSentAddresses();
					if (validSent != null) {
						logger.info("ValidSent Addresses:");
						for (int i = 0; i < validSent.length; i++)
							logger.info("	" + validSent[i]);
					}
				}
				
				if (ex instanceof MessagingException) {
					ex = ((MessagingException) ex).getNextException();
				} else {
					ex = null;
				}
			} while (ex != null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class EmailAuthenticator extends Authenticator {
		private String username;
		private String password;
		
		public EmailAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}
}