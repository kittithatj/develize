package com.pim.develize.service;

import com.pim.develize.model.MailModel;
import com.sun.mail.util.MailConnectException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String mail, MailModel content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom("develize.app@gmail.com");
            message.setTo(mail);
            message.setText(content.getMessage(),true);
            message.setSubject(content.getSubject());
            javaMailSender.send(mimeMessage);

            System.out.println("Email Sent Successfully!");
        }
        catch (MessagingException e) {
            System.err.println(e.getMessage());
        }

    }
}
