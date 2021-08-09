package com.bdular.inventorytracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailService {

    @Autowired
    JavaMailSender emailSender;

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("noreply@inventorytracker.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        File file = new File(pathToAttachment);
        FileSystemResource systemResource = new FileSystemResource(file);
        helper.addAttachment(file.getName(), systemResource);
        emailSender.send(message);
    }
}
