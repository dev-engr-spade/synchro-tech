package com.synchrotech.commandcenter.repository.user;

import com.synchrotech.commandcenter.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindByTenantId() {
        User user = User.builder()
            .username("repoTest")
            .email("repo@example.com")
            .tenantId("tenantRepo")
            .build();
        userRepository.save(user);
        List<User> found = userRepository.findByTenantId("tenantRepo");
        assertFalse(found.isEmpty());
        assertEquals("repoTest", found.get(0).getUsername());
    }
} 