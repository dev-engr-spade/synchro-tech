package com.synchrotech.commandcenter.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "testsecretkeytestsecretkeytestsecretkey");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 3600000L); // 1 hour
    }

    @Test
    void generateAndValidateToken_success() {
        String token = jwtUtil.generateToken("userId", "username", "tenantId", new HashMap<>());
        Claims claims = jwtUtil.validateToken(token).getBody();
        assertEquals("userId", claims.getSubject());
        assertEquals("username", claims.get("username"));
        assertEquals("tenantId", claims.get("tenantId"));
    }

    @Test
    void getUserId_returnsSubject() {
        String token = jwtUtil.generateToken("userId", "username", "tenantId", new HashMap<>());
        assertEquals("userId", jwtUtil.getUserId(token));
    }
} 