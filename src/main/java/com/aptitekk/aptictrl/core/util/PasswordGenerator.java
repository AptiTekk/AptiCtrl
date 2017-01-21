/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.util;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
    private static final Random RANDOM = new SecureRandom();
    private static final String VALID_CHARACTERS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

    /**
     * Generates a random String suitable for use as a temporary password.
     *
     * @param length The length of the password, in characters.
     * @return A random password with the specified length.
     */
    public static String generateRandomPassword(int length) {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (RANDOM.nextDouble() * VALID_CHARACTERS.length());
            passwordBuilder.append(VALID_CHARACTERS.charAt(index));
        }
        return passwordBuilder.toString();
    }

}
