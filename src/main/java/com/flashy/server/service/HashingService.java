package com.flashy.server.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashingService {

    public static final int ROUNDS = 12;

    public String getSalt() {
        return BCrypt.gensalt(ROUNDS);
    }
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, getSalt());
    }

    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
 }
