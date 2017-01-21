/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        if(zonedDateTime == null)
            return null;
        return Date.from(zonedDateTime.toInstant());
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Date date) {
        if(date == null)
            return null;
        return ZonedDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
    }
}
