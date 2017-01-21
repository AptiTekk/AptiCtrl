/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.entities.propertyValidators;

import java.time.ZoneId;

public class TimeZonePropertyValidator extends PropertyValidator {

    public TimeZonePropertyValidator() {
        super("The timezone provided is not recognized.");
    }

    @Override
    public boolean isValid(String inputValue) {
        try {
            ZoneId zoneId = ZoneId.of(inputValue);
            return zoneId != null;
        } catch (Exception e) {
            return false;
        }
    }
}
