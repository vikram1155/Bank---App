package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.lti.repository.AccountDao;

@Component
public class EmailService {
	@Autowired
	private MailSender mailSender;
	
	
	public void sendEmailForNewRegistration(String email,String text,String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		//ruchirazope0599@hotmail.com
		//zoperuchira1999@outlook.com
		message.setFrom("januram257@outlook.com");
		message.setTo(email);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
		
	}
}
