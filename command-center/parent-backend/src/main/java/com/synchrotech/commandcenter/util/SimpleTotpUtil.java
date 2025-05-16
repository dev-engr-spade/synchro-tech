package com.synchrotech.commandcenter.util;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import java.security.SecureRandom;

public class SimpleTotpUtil {
    public static String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public static String getTotpCode(String secret) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secret);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public static boolean validateTotp(String secret, String code) {
        return getTotpCode(secret).equals(code);
    }
} 