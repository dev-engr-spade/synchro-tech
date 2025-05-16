package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.dto.request.user.UserCreateRequest;
import com.synchrotech.commandcenter.dto.request.user.UserUpdateRequest;
import com.synchrotech.commandcenter.dto.request.user.BulkUserCreateRequest;
import com.synchrotech.commandcenter.service.user.UserService;
import com.synchrotech.commandcenter.service.user.ProfilePictureStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ProfilePictureStorageService profilePictureStorageService;

    @Test
    void createUser_shouldReturnUser() throws Exception {
        UserCreateRequest req = new UserCreateRequest();
        req.setUsername("testuser");
        req.setEmail("test@example.com");
        req.setFirstName("Test");
        req.setLastName("User");
        req.setTenantId("tenant1");
        req.setActive(true);
        User user = User.builder().id("1").username("testuser").email("test@example.com").firstName("Test").lastName("User").tenantId("tenant1").active(true).build();
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\":\"testuser\"," +
                        "\"email\":\"test@example.com\"," +
                        "\"firstName\":\"Test\"," +
                        "\"lastName\":\"User\"," +
                        "\"tenantId\":\"tenant1\"," +
                        "\"active\":true" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        UserUpdateRequest req = new UserUpdateRequest();
        req.setId("1");
        req.setFirstName("Updated");
        req.setLastName("User");
        req.setActive(true);
        User user = User.builder().id("1").firstName("Updated").lastName("User").active(true).build();
        Mockito.when(userService.updateUser(Mockito.eq("1"), Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\":\"1\"," +
                        "\"firstName\":\"Updated\"," +
                        "\"lastName\":\"User\"," +
                        "\"active\":true" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    void getUser_shouldReturnUser() throws Exception {
        User user = User.builder().id("1").username("testuser").build();
        Mockito.when(userService.findById("1")).thenReturn(Optional.of(user));
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getUser_notFound() throws Exception {
        Mockito.when(userService.findById("2")).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void deleteUser_shouldCallService() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
        Mockito.verify(userService).deleteUser("1");
    }

    @Test
    void bulkCreateUsers_shouldReturnUsers() throws Exception {
        BulkUserCreateRequest req = new BulkUserCreateRequest();
        User user = User.builder().id("1").username("testuser").build();
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(post("/api/users/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"tenantId\":\"tenant1\"," +
                        "\"users\":[{" +
                        "\"username\":\"testuser\"," +
                        "\"email\":\"test@example.com\"," +
                        "\"firstName\":\"Test\"," +
                        "\"lastName\":\"User\"," +
                        "\"active\":true" +
                        "}]" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateStatus_shouldUpdateUser() throws Exception {
        User user = User.builder().id("1").active(false).build();
        Mockito.when(userService.findById("1")).thenReturn(Optional.of(user));
        Mockito.when(userService.updateUser(Mockito.eq("1"), Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(put("/api/users/1/status?active=false"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProfile_shouldUpdateProfile() throws Exception {
        User user = User.builder().id("1").firstName("Test").build();
        Mockito.when(userService.updateUser(Mockito.eq("1"), Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(put("/api/users/1/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\":\"1\"," +
                        "\"firstName\":\"Test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void uploadProfilePicture_local_shouldReturnUrl() throws Exception {
        User user = User.builder().id("user123").build();
        Mockito.when(userService.findById("user123")).thenReturn(Optional.of(user));
        Mockito.when(userService.updateUser(Mockito.eq("user123"), Mockito.any(User.class))).thenReturn(user);
        Mockito.when(profilePictureStorageService.store(Mockito.eq("user123"), Mockito.any())).thenReturn("/uploads/profile-pictures/user123_pic.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "pic.jpg", MediaType.IMAGE_JPEG_VALUE, "dummy image content".getBytes());
        mockMvc.perform(multipart("/api/users/user123/profile-picture").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("/uploads/profile-pictures/user123_pic.jpg"));
    }

    @Test
    void uploadProfilePicture_s3_shouldReturnUrl() throws Exception {
        User user = User.builder().id("user123").build();
        Mockito.when(userService.findById("user123")).thenReturn(Optional.of(user));
        Mockito.when(userService.updateUser(Mockito.eq("user123"), Mockito.any(User.class))).thenReturn(user);
        Mockito.when(profilePictureStorageService.store(Mockito.eq("user123"), Mockito.any())).thenReturn("https://bucket.s3.amazonaws.com/profile-pictures/user123_pic.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "pic.jpg", MediaType.IMAGE_JPEG_VALUE, "dummy image content".getBytes());
        mockMvc.perform(multipart("/api/users/user123/profile-picture").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("https://bucket.s3.amazonaws.com/profile-pictures/user123_pic.jpg"));
    }

    @Test
    void uploadProfilePicture_emptyFile_shouldReturnBadRequest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "pic.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);
        mockMvc.perform(multipart("/api/users/user123/profile-picture").file(file))
                .andExpect(status().isBadRequest());
    }
} 