/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.databaseConfigurations;

import org.hibernate.boot.model.naming.*;

import java.util.List;
import java.util.StringJoiner;

public class CustomImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    @Override
    public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
        List<Identifier> columnNames = source.getColumnNames();
        StringJoiner stringJoiner = new StringJoiner("_");
        for (Identifier identifier : columnNames) {
            stringJoiner.add(identifier.getCanonicalName());
        }
        return toIdentifier("FK_" + stringJoiner.toString(), source.getBuildingContext());
    }

    @Override
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
        return toIdentifier(super.determineJoinTableName(source).getText().toLowerCase(), source.getBuildingContext());
    }
}
