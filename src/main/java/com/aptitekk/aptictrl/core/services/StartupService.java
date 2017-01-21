/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.services;

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

    private static final AtomicBoolean started = new AtomicBoolean(false);

    @Autowired
    public StartupService(LogService logService) {
        this.logService = logService;
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        started.set(true);
    }

    public static boolean isStarted() {
        return started.get();
    }

}
