/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.services.auth;

import com.aptitekk.aptictrl.core.domain.entities.User;
import com.aptitekk.aptictrl.core.domain.repositories.UserRepository;
import com.aptitekk.aptictrl.core.services.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private static final String COOKIE_NAME = "APTICTRL_AUTH";

    private final HttpServletRequest request;
    private final CookieService cookieService;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(HttpServletRequest request, CookieService cookieService, UserRepository userRepository) {
        this.request = request;
        this.cookieService = cookieService;
        this.userRepository = userRepository;
    }

    /**
     * Stores the User ID within an encrypted Cookie, saved in the user's browser.
     *
     * @param user     The User to store.
     * @param response The servlet response to save the Cookie in.
     */
    public void setCurrentUser(User user, HttpServletResponse response) {
        cookieService.storeEncryptedCookie(COOKIE_NAME, user.getId() + "", response);
    }

    /**
     * Retrieves the User from the Cookie saved on the user's browser (if one exists).
     *
     * @return The User from the Cookie, or null if it could not be read / found.
     */
    public User getCurrentUser() {
        String data = cookieService.getDataFromEncryptedCookie(COOKIE_NAME, request);
        if (data == null)
            return null;

        try {
            Long userId = Long.parseLong(data);
            return userRepository.find(userId);
        } catch (NumberFormatException ignored) {
        }

        return null;
    }

    /**
     * Determines if the user is signed in or not.
     *
     * @return True if the user is signed in, false otherwise.
     */
    public boolean isUserSignedIn() {
        return getCurrentUser() != null;
    }

    /**
     * Signs the current user out by deleting its Cookie.
     *
     * @param response The HttpServletResponse to store the deletion cookie in.
     */
    public void signOutCurrentUser(HttpServletResponse response) {
        cookieService.deleteCookie(COOKIE_NAME, response);
    }

}
