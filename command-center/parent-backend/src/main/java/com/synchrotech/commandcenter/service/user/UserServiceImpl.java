package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.repository.user.UserRepository;
import com.synchrotech.commandcenter.security.TenantContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        user.setTenantId(TenantContextHolder.getTenant());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String id, User user) {
        Optional<User> existingOpt = userRepository.findById(id);
        if (existingOpt.isEmpty()) throw new RuntimeException("User not found");
        User existing = existingOpt.get();
        if (!TenantContextHolder.getTenant().equals(existing.getTenantId())) {
            throw new RuntimeException("Unauthorized tenant access");
        }
        // Update fields as needed
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setRoles(user.getRoles());
        existing.setActive(user.isActive());
        existing.setUpdatedAt(System.currentTimeMillis());
        return userRepository.save(existing);
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.filter(u -> TenantContextHolder.getTenant().equals(u.getTenantId()));
    }

    @Override
    public List<User> findByTenantId(String tenantId) {
        return userRepository.findByTenantId(tenantId);
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent() && TenantContextHolder.getTenant().equals(userOpt.get().getTenantId())) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Unauthorized tenant access");
        }
    }
} 