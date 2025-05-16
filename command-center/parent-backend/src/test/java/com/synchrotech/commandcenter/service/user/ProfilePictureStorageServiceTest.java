package com.synchrotech.commandcenter.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfilePictureStorageServiceTest {
    @InjectMocks ProfilePictureStorageService storageService;

    @BeforeEach
    void setup() {
        // Set fields via reflection for test
        storageService = new ProfilePictureStorageService();
        setField(storageService, "storageType", "local");
        setField(storageService, "localDir", "test-uploads/");
        setField(storageService, "s3Bucket", "test-bucket");
        setField(storageService, "s3Region", "us-east-1");
        setField(storageService, "s3BaseUrl", "https://test-bucket.s3.amazonaws.com/");
    }

    @Test
    void saveLocally_shouldReturnUrl() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "pic.jpg", "image/jpeg", "dummy".getBytes());
        String url = storageService.store("user1", file);
        assertTrue(url.contains("test-uploads/"));
        // Clean up
        new File(url.replaceFirst("/", "")).delete();
    }

    @Test
    void uploadToS3_shouldReturnUrl() throws IOException {
        setField(storageService, "storageType", "s3");
        // S3Client is created inside method, so we can't mock it directly here without refactor
        // This test will just check that the method returns the expected URL format
        MockMultipartFile file = new MockMultipartFile("file", "pic.jpg", "image/jpeg", "dummy".getBytes());
        String url = storageService.store("user1", file);
        assertTrue(url.startsWith("https://test-bucket.s3.amazonaws.com/profile-pictures/"));
    }

    // Helper to set private fields
    private static void setField(Object target, String field, Object value) {
        try {
            java.lang.reflect.Field f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
} 