package com.bijlipay.util;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Service
public class RandomGenerator {
    public static final int DEFAULT_LENGTH = 32;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom secureRandom;
    private final Set<String> generatedStrings;
    public RandomGenerator() throws  NoSuchAlgorithmException {
        this.secureRandom = SecureRandom.getInstanceStrong();
        this.generatedStrings = new HashSet<>();
    }

    public synchronized String generateUniqueRandomString(int length) {
        String randomString;
        do {
            randomString = generateRandomString(length);
        } while (generatedStrings.contains(randomString));
        generatedStrings.add(randomString);
        return randomString;
    }

    private String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(index));
        }
        return stringBuilder.toString();
    }
}
