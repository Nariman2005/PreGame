package com.pregame.gametesting.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for handling contact message file operations
 */
public class CsvUtil {

    // Define where the TXT file will be stored (user's home directory)
    private static final String MESSAGE_FILE_PATH = System.getProperty("user.home") + File.separator + "contact_messages.txt";

    /**
     * Saves a contact message to the TXT file
     *
     * @param name The sender's name
     * @param email The sender's email address
     * @param subject The message subject
     * @param message The message body
     * @return true if saved successfully, false otherwise
     */
    public static boolean saveContactMessage(String name, String email, String subject, String message) {
        try {
            // Check if file exists and create parent directories if needed
            File messageFile = new File(MESSAGE_FILE_PATH);
            boolean isNewFile = !messageFile.exists();

            // Create parent directories if they don't exist
            if (isNewFile) {
                Path parentDir = Paths.get(messageFile.getParent());
                if (!Files.exists(parentDir)) {
                    Files.createDirectories(parentDir);
                }
            }

            // Open file in append mode
            FileWriter writer = new FileWriter(messageFile, true);

            // Format timestamp
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Write message in a structured text format
            writer.append("=== New Message (" + timestamp + ") ===\n");
            writer.append("From: " + name + "\n");
            writer.append("Email: " + email + "\n");
            writer.append("Subject: " + subject + "\n");
            writer.append("Message:\n" + message + "\n");
            writer.append("==============================\n\n");

            writer.flush();
            writer.close();

            System.out.println("Contact message saved to: " + MESSAGE_FILE_PATH);
            return true;

        } catch (IOException e) {
            System.err.println("Error saving contact message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the path to the message file
     */
    public static String getFilePath() {
        return MESSAGE_FILE_PATH;
    }
}