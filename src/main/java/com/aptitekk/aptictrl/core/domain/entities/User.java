/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.entities;

import com.aptitekk.aptictrl.core.util.EqualsHelper;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "\"user\"")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    public String username;

    public String hashedPassword;

    public Long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (!(o instanceof User)) return false;

        User other = (User) o;

        return EqualsHelper.areEquals(username, other.username)
                && EqualsHelper.areEquals(hashedPassword, other.hashedPassword);
    }

    @Override
    public int hashCode() {
        return EqualsHelper.calculateHashCode(username, hashedPassword);
    }

}
