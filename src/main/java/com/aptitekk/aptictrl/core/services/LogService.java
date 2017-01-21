/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.services;

import com.mindscapehq.raygun4java.core.RaygunClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
public class LogService {

    private Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    private static final String RAYGUN_API_KEY = System.getenv("RAYGUN_APIKEY");
    private static final RaygunClient RAYGUN_CLIENT = RAYGUN_API_KEY != null ? new RaygunClient(RAYGUN_API_KEY) : null;

    private SpringProfileService springProfileService;

    public LogService(SpringProfileService springProfileService) {
        this.springProfileService = springProfileService;
    }

    public void logInfo(Class clazz, String message) {
        LOGGER.info("[" + clazz.getSimpleName() + "] " + message);
    }

    public void logError(Class clazz, String message) {
        LOGGER.error("[" + clazz.getSimpleName() + "] " + message);
    }

    public void logException(Class clazz, Throwable t, String message) {
        LOGGER.error("[" + clazz.getSimpleName() + "] " + message, t);
        if (RAYGUN_CLIENT != null && springProfileService.isProfileActive(SpringProfileService.Profile.PRODUCTION)) {
            List<String> tags = new ArrayList<>();
            //TODO: tags.add("V. " + AptiBookInfoProvider.getVersion());
            tags.add(clazz.getSimpleName());

            Map<String, String> data = new HashMap<>();
            data.put("message", message);
            RAYGUN_CLIENT.Send(t, tags, data);
        }
    }

    public void logDebug(Class clazz, String message) {
        LOGGER.debug("[" + clazz.getSimpleName() + "] " + message);
    }

}
