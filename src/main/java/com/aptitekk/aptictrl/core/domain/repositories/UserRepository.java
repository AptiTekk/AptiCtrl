/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.repositories;

import com.aptitekk.aptictrl.core.crypto.PasswordStorage;
import com.aptitekk.aptictrl.core.domain.entities.User;
import com.aptitekk.aptictrl.core.domain.repositories.annotations.EntityRepository;

import javax.persistence.PersistenceException;

@EntityRepository
public class UserRepository extends EntityRepositoryAbstract<User> {

    /**
     * Finds User Entity by its username.
     *
     * @param username The username address of the User to search for.
     * @return A User Entity with the specified username, or null if one does not exist.
     */
    public User findByUsername(String username) {
        if (username == null) {
            return null;
        }
        try {
            return entityManager
                    .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username.toLowerCase())
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    /**
     * Determines if the credentials are correct.
     *
     * @param username The username of the user to check.
     * @param password The password of the user to check (raw).
     * @return The User if the credentials are correct, or null if they are not.
     */
    public User findUserWithCredentials(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        try {
            User user = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username.toLowerCase())
                    .getSingleResult();
            if (user != null && user.hashedPassword != null) {
                if (PasswordStorage.verifyPassword(password, user.hashedPassword))
                    return user;
            }
        } catch (PersistenceException | PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
            return null;
        }
        return null;
    }

}
