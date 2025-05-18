package com.synchrotech.commandcenter.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.UUID;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest.Builder;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class ProfilePictureStorageService {

    @Value("${app.profile-picture.upload-dir:uploads/profile-pictures}")
    private String uploadDir;

    @Value("${app.profile-picture.max-size:5242880}") // 5MB default
    private long maxFileSize;

    @Value("${app.profile-picture.s3.enabled:false}")
    private boolean s3Enabled;

    @Value("${app.profile-picture.s3.bucket:}")
    private String s3Bucket;

    @Value("${app.profile-picture.s3.region:}")
    private String s3Region;

    @Value("${app.profile-picture.s3.access-key:}")
    private String s3AccessKey;

    @Value("${app.profile-picture.s3.secret-key:}")
    private String s3SecretKey;

    @Value("${app.profile-picture.s3.presign-duration-seconds:3600}")
    private long presignDurationSeconds;

    private S3Client s3Client;
    private S3Presigner s3Presigner;
    private boolean s3Initialized = false;

    private void initS3() {
        if (!s3Enabled || s3Initialized) return;
        Region region = Region.of(s3Region);
        AwsCredentialsProvider credentialsProvider;
        if (s3AccessKey != null && !s3AccessKey.isEmpty() && s3SecretKey != null && !s3SecretKey.isEmpty()) {
            credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(s3AccessKey, s3SecretKey));
        } else {
            credentialsProvider = DefaultCredentialsProvider.create();
        }
        s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
        s3Presigner = S3Presigner.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
        s3Initialized = true;
    }

    /**
     * Stores a profile picture for a user in a tenant-specific directory (S3 or local).
     * @param userId The user ID
     * @param tenantId The tenant ID
     * @param file The uploaded file
     * @return The public or pre-signed URL to access the profile picture
     * @throws IOException if file storage fails
     */
    public String store(String userId, String tenantId, MultipartFile file) throws IOException {
        validateFile(file);
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = userId + "-" + UUID.randomUUID() + (extension != null ? "." + extension : "");
        if (s3Enabled) {
            initS3();
            String key = "profile-pictures/" + tenantId + "/" + filename;
            try {
                s3Client.putObject(PutObjectRequest.builder()
                                .bucket(s3Bucket)
                                .key(key)
                                .contentType(file.getContentType())
                                .acl(ObjectCannedACL.PUBLIC_READ) // or use private and presigned URLs
                                .build(),
                        RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
                // Generate a pre-signed URL (recommended for private ACL)
                return generatePresignedUrl(key);
            } catch (S3Exception e) {
                throw new IOException("Failed to upload to S3: " + e.awsErrorDetails().errorMessage(), e);
            }
        } else {
            // Fallback to local storage
            Path tenantPath = Paths.get(uploadDir, tenantId);
            Files.createDirectories(tenantPath);
            Path targetPath = tenantPath.resolve(filename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return "/static/profile-pictures/" + tenantId + "/" + filename;
        }
    }

    /**
     * Deletes a profile picture for a user in a tenant-specific directory (S3 or local).
     * @param tenantId The tenant ID
     * @param filename The filename to delete
     * @throws IOException if deletion fails
     */
    public void delete(String tenantId, String filename) throws IOException {
        if (s3Enabled) {
            initS3();
            String key = "profile-pictures/" + tenantId + "/" + filename;
            try {
                s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(s3Bucket)
                        .key(key)
                        .build());
            } catch (S3Exception e) {
                throw new IOException("Failed to delete from S3: " + e.awsErrorDetails().errorMessage(), e);
            }
        } else {
            Path filePath = Paths.get(uploadDir, tenantId, filename);
            Files.deleteIfExists(filePath);
        }
    }

    private String generatePresignedUrl(String key) {
        if (s3Presigner == null) return null;
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(s3Bucket)
                .key(key)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(presignDurationSeconds))
                .getObjectRequest(getObjectRequest)
                .build();
        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds limit");
        }
        String contentType = file.getContentType();
        if (contentType == null || 
            !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif"))) {
            throw new IllegalArgumentException("Only JPEG, PNG, and GIF images are allowed");
        }
    }
} 