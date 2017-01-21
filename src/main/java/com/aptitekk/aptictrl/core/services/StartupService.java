/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.services;

import com.aptitekk.aptictrl.core.crypto.PasswordStorage;
import com.aptitekk.aptictrl.core.domain.entities.User;
import com.aptitekk.aptictrl.core.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class StartupService implements Serializable {

    private final LogService logService;
    private final UserRepository userRepository;

    private static final AtomicBoolean started = new AtomicBoolean(false);

    @Autowired
    public StartupService(LogService logService,
                          UserRepository userRepository) {
        this.logService = logService;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // Make sure the aptitekk user exists.
        if (userRepository.findByUsername("aptitekk") == null) {
            try {
                User admin = new User();
                admin.username = "aptitekk";
                admin.hashedPassword = PasswordStorage.createHash("aptitekk");

                userRepository.save(admin);
            } catch (PasswordStorage.CannotPerformOperationException e) {
                logService.logException(getClass(), e, "Could not create aptitekk user's password.");
            }
        }

        started.set(true);
    }

    public static boolean isStarted() {
        return started.get();
    }

}
