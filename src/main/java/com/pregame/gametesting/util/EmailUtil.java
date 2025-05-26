package com.pregame.gametesting.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {

    // Configure these values based on your email provider
    private static final String HOST = "smtp.gmail.com"; // Change this to your SMTP server
    private static final String PORT = "587";
    private static final String USERNAME = "your-email@gmail.com"; // Change to your email
    private static final String PASSWORD = "your-app-password"; // Change to your app password
    private static final String FROM_EMAIL = "your-email@gmail.com"; // Change to your email
    private static final String FROM_NAME = "PreGame Testing Platform"; // Change to your name

    /**
     * Sends an email with the feedback information
     *
     * @param to Recipient's email address
     * @param subject Email subject
     * @param messageText Email text content
     * @param attachment Optional attachment bytes
     * @param attachmentName Optional attachment name
     * @return Whether the email was sent successfully
     */
    public static boolean sendFeedbackEmail(String to, String subject, String messageText,
                                          byte[] attachment, String attachmentName) {
        try {
            // Set up mail server properties
            Properties props = new Properties();
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Create a session with authentication
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            // Create message
            Message message = new MimeMessage(session);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // Create message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageText, "text/html");

            // Create multipart message for attachment support
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Add attachment if provided
            if (attachment != null && attachment.length > 0 && attachmentName != null) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setContent(attachment, "application/octet-stream");
                attachmentPart.setFileName(attachmentName);
                multipart.addBodyPart(attachmentPart);
            }

            // Set the multipart content
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Format the feedback content as HTML for the email body
     */
    public static String formatFeedbackContent(int gameId, int gamerId, String feedbackText) {
        return "<html><body>" +
               "<h2>New Feedback Received</h2>" +
               "<p><strong>Game ID:</strong> " + gameId + "</p>" +
               "<p><strong>From Gamer ID:</strong> " + gamerId + "</p>" +
               "<p><strong>Feedback:</strong></p>" +
               "<div style='background-color: #f8f9fa; border-radius: 5px; padding: 15px; margin: 10px 0;'>" +
               feedbackText.replace("\n", "<br/>") +
               "</div>" +
               "<p>This email was sent from the PreGame Testing Platform.</p>" +
               "</body></html>";
    }
}
