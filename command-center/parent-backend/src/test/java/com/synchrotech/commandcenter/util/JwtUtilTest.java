package com.synchrotech.commandcenter.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class JwtUtilTest {
    @Test
    void generateAndValidateToken() {
        JwtUtil jwtUtil = new JwtUtil();
        // Set secret and expiration via reflection
        setField(jwtUtil, "jwtSecret", "mysecretkeymysecretkeymysecretkeymysecretkey");
        setField(jwtUtil, "jwtExpirationMs", 100000L);
        String token = jwtUtil.generateToken("userId", "username", "tenantId", Map.of());
        assertNotNull(token);
        assertEquals("userId", jwtUtil.getUserId(token));
    }

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