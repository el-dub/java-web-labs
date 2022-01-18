package com.kpi.payments.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncoder {

    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    private PasswordEncoder() {
    }
}
