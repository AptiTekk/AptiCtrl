/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.services;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CookieService {

    private static final String ENCRYPTION_KEY = "xof@SqzU5z5X&m3qaJMSj$3WNo@JmYPmog*L%dZooLI3ldVnoc";

    private final SpringProfileService springProfileService;
    private final LogService logService;

    @Autowired
    public CookieService(SpringProfileService springProfileService, LogService logService) {
        this.springProfileService = springProfileService;
        this.logService = logService;
    }

    /**
     * Securely encrypts data for Cookies.
     *
     * @param data The cookie data to encrypt.
     * @return The encrypted cookie data.
     */
    private String encryptCookieData(String data, int maxAgeSeconds) {
        if (data == null)
            return null;

        // Get the current time and add the max age to it to determine when this cookie will expire.
        LocalDateTime expireDateTime = LocalDateTime.now();
        expireDateTime = expireDateTime.plusSeconds(maxAgeSeconds);

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(ENCRYPTION_KEY);
        try {
            // Store the data and the expires date together.
            return basicTextEncryptor.encrypt(data + "EXPIRES" + expireDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } catch (EncryptionOperationNotPossibleException e) {
            if (e.getMessage() != null)
                logService.logException(getClass(), e, "Could not encrypt auth cookie");
            else
                logService.logError(getClass(), "Could not encrypt auth cookie.");
            return null;
        }
    }

    /**
     * Decrypts cookie data.
     *
     * @param encryptedData The encrypted cookie data.
     * @return The decrypted cookie data as a string.
     */
    private String decryptCookieData(String encryptedData) {
        if (encryptedData == null)
            return null;

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(ENCRYPTION_KEY);
        try {
            String decrypted = basicTextEncryptor.decrypt(encryptedData);

            //Check for encrypted expires time
            String[] split = decrypted.split("EXPIRES");
            if (split.length != 2)
                return null;
            else {
                //Check to make sure the current time is not after the expires time.
                try {
                    LocalDateTime expiresDateTime = LocalDateTime.parse(split[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                    // Return null if the current time is after the expires time (this cookie has expired, and the user is still trying to use it).
                    if (LocalDateTime.now().isAfter(expiresDateTime))
                        return null;

                    // All checked out, return the data.
                    return split[0];
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        } catch (EncryptionOperationNotPossibleException e) {
            if (e.getMessage() != null)
                logService.logException(getClass(), e, "Could not decrypt auth cookie");
            else
                logService.logError(getClass(), "Could not decrypt auth cookie.");
            return null;
        }
    }

    public void storeEncryptedCookie(String key, String value, HttpServletResponse response) {
        if (key == null || value == null)
            return;

        Cookie cookie = new Cookie(key, encryptCookieData(value, 608400));

        if (springProfileService.isProfileActive(SpringProfileService.Profile.PRODUCTION)) {
            cookie.setSecure(true);
        }

        cookie.setMaxAge(608400);
        cookie.setPath("/api/");
        response.addCookie(cookie);
    }

    public String getDataFromEncryptedCookie(String key, HttpServletRequest request) {
        if (request == null)
            return null;

        if (request.getCookies() == null)
            return null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(key)) {
                return decryptCookieData(cookie.getValue());
            }
        }

        return null;
    }

    public void deleteCookie(String key, HttpServletResponse response) {
        if (key == null)
            return;

        Cookie cookie = new Cookie(key, null);

        cookie.setMaxAge(0);
        cookie.setPath("/api/");
        response.addCookie(cookie);
    }

}
