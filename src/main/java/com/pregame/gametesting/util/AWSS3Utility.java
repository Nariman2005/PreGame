package com.pregame.gametesting.util;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AWSS3Utility {
    private static final Logger logger = Logger.getLogger(AWSS3Utility.class.getName());
    private static final String BUCKET_NAME;
    private static final String ACCESS_KEY;
    private static final String SECRET_KEY;
    private static final Region REGION;
    private static final String BASE_URL;
    private static final S3Client s3Client;

    static {
        Properties props = new Properties();
        try (InputStream input = AWSS3Utility.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            props.load(input);

            // Load configuration from properties file
            BUCKET_NAME = props.getProperty("aws.s3.bucket");
            ACCESS_KEY = props.getProperty("aws.s3.accessKey");
            SECRET_KEY = props.getProperty("aws.s3.secretKey");
            REGION = Region.of(props.getProperty("aws.s3.region"));
            BASE_URL = "https://" + BUCKET_NAME + ".s3." + REGION.id() + ".amazonaws.com/";

            // Initialize S3 client
            s3Client = S3Client.builder()
                    .region(REGION)
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                    .build();

            System.out.println("[AWSS3Utility] AWS S3 client initialized for bucket: " + BUCKET_NAME);
        } catch (IOException e) {
            System.err.println("[AWSS3Utility] Failed to load properties: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Get the S3 bucket name used by the application
     * @return The bucket name
     */
    public static String getBucketName() {
        return BUCKET_NAME;
    }

    /**
     * Uploads a file to AWS S3 and returns the download URL
     */
    public static String uploadFile(InputStream inputStream, String fileName, String contentType, long fileSize) {
        try {
            logger.info("[AWSS3Utility] Starting file upload to AWS S3");
            logger.info("[AWSS3Utility] File: " + fileName + ", Size: " + fileSize + " bytes, Type: " + contentType);

            // Create unique file key
            String uniqueId = UUID.randomUUID().toString();
            String sanitizedFileName = sanitizeFileName(fileName);
            String fileKey = "uploads/" + uniqueId + "_" + sanitizedFileName;

            // Configure upload request without ACL (for buckets with Object Ownership set to "Bucket owner enforced")
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileKey)
                    .contentType(contentType)
                    // Removed ACL setting as the bucket doesn't allow ACLs
                    .build();

            // Upload file to S3
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, fileSize));

            logger.info("[AWSS3Utility] File uploaded successfully to S3: " + fileKey);

            // Return the full URL to the uploaded file
            String downloadUrl = BASE_URL + fileKey;
            logger.info("[AWSS3Utility] Generated download URL: " + downloadUrl);
            return downloadUrl;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "[AWSS3Utility] ERROR uploading to S3: " + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }

    /**
     * Sanitize a filename to remove invalid characters
     */
    private static String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "unnamed_file";
        }
        return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    /**
     * Contains metadata about an S3 object
     */
    public static class S3ObjectInfo {
        private final long contentLength;
        private final String contentType;
        private final String eTag;

        public S3ObjectInfo(long contentLength, String contentType, String eTag) {
            this.contentLength = contentLength;
            this.contentType = contentType;
            this.eTag = eTag;
        }

        public long getContentLength() {
            return contentLength;
        }

        public String getContentType() {
            return contentType;
        }

        public String getETag() {
            return eTag;
        }
    }

    /**
     * Get metadata information about an S3 object
     * @param key The key of the S3 object
     * @return S3ObjectInfo containing metadata or null if object doesn't exist
     */
    public static S3ObjectInfo getObjectInfo(String key) {
        try {
            // Remove leading slash from the key if present, as S3 keys don't have leading slashes
            if (key != null && key.startsWith("/")) {
                key = key.substring(1);
                logger.info("[AWSS3Utility] Removed leading slash from key, using: " + key);
            }

            logger.info("[AWSS3Utility] Getting object info for key: " + key + " in bucket: " + BUCKET_NAME);

            software.amazon.awssdk.services.s3.model.HeadObjectRequest headObjectRequest =
                    software.amazon.awssdk.services.s3.model.HeadObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(key)
                            .build();

            software.amazon.awssdk.services.s3.model.HeadObjectResponse metadata =
                    s3Client.headObject(headObjectRequest);

            logger.info("[AWSS3Utility] Found object in S3: " + key + ", content type: " + metadata.contentType() + ", size: " + metadata.contentLength() + " bytes");

            return new S3ObjectInfo(
                    metadata.contentLength(),
                    metadata.contentType(),
                    metadata.eTag()
            );
        } catch (software.amazon.awssdk.services.s3.model.NoSuchKeyException e) {
            // Object not found
            logger.warning("[AWSS3Utility] Object not found in S3: " + key);
            return null;
        } catch (software.amazon.awssdk.services.s3.model.S3Exception e) {
            // Other error
            logger.severe("[AWSS3Utility] S3 error while checking object: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Download a file from S3
     * @param key The key of the S3 object
     * @return InputStream to the S3 object or null if not found
     */
    public static InputStream downloadFile(String key) {
        try {
            // Remove leading slash from the key if present, as S3 keys don't have leading slashes
            if (key != null && key.startsWith("/")) {
                key = key.substring(1);
                logger.info("[AWSS3Utility] Removed leading slash from key, using: " + key);
            }

            logger.info("[AWSS3Utility] Downloading file with key: " + key + " from bucket: " + BUCKET_NAME);

            software.amazon.awssdk.services.s3.model.GetObjectRequest getObjectRequest =
                    software.amazon.awssdk.services.s3.model.GetObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(key)
                            .build();

            // Get object as input stream directly
            InputStream result = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
            logger.info("[AWSS3Utility] Successfully opened stream for file: " + key);
            return result;
        } catch (software.amazon.awssdk.services.s3.model.NoSuchKeyException e) {
            // Object not found
            logger.warning("[AWSS3Utility] Object not found in S3 during download: " + key);
            return null;
        } catch (software.amazon.awssdk.services.s3.model.S3Exception e) {
            // Other error
            logger.severe("[AWSS3Utility] S3 error while downloading: " + e.getMessage());
            throw e;
        }
    }
}

