package com.pregame.gametesting.controller;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailSender {
    public static void send(String toEmail, String subject, String body) throws MessagingException {
        final String fromEmail = "PreGameDevs@gmail.com"; // Email address
        final String password = "eydu elhd rosj oxgy";    // App password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);
    }
}

