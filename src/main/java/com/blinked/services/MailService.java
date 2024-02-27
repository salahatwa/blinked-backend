package com.blinked.services;

import static com.blinked.utils.MailConstants.CONTENT_IS_HTML;
import static com.blinked.utils.MailConstants.EMAIL_SUCCESSFULLY_SENT_TO;
import static com.blinked.utils.MailConstants.ERROR_SENDING_EMAIL_MESSAGE;
import static com.blinked.utils.MailConstants.ERROR_SMTP_AUTH;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;

import com.blinked.apis.requests.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {

	private final TemplateEngine engine;
	private final JavaMailSender sender;

	@Autowired
	public MailService(TemplateEngine engine, JavaMailSender sender) {
		this.engine = engine;
		this.sender = sender;
	}

	public void send(Email email) {
		try {
			MimeMessage message = createMessage(email);
			sender.send(message);
			log.info(EMAIL_SUCCESSFULLY_SENT_TO, email.getDestination());
		} catch (MailAuthenticationException exception) {
			log.error(ERROR_SMTP_AUTH);
		} catch (MessagingException | MailException exception) {
			log.error(ERROR_SENDING_EMAIL_MESSAGE, exception);
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, ERROR_SENDING_EMAIL_MESSAGE);
		}
	}

	private MimeMessage createMessage(Email email) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String html = engine.process(email.getTemplate(), email.getContext());

		helper.setTo(email.getDestination());
		helper.setSubject(email.getSubject());
		helper.setText(html, CONTENT_IS_HTML);

		return message;
	}
}
