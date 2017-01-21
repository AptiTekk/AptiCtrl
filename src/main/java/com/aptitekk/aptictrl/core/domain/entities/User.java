/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.entities;

import com.aptitekk.aptictrl.core.util.EqualsHelper;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "\"user\"")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    public String emailAddress;

    public String firstName;

    public String lastName;

    public String phoneNumber;

    public String location;

    public String hashedPassword;

    public Long getId() {
        return this.id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        if (emailAddress != null)
            emailAddress = emailAddress.toLowerCase();
        this.emailAddress = emailAddress;
    }

    /**
     * Determines if the user is the admin.
     *
     * @return True if the user is the admin, false otherwise.
     */
    public boolean isAdmin() {
        return emailAddress.equalsIgnoreCase("admin");
    }

    /**
     * Gets the full name of the user, or the email address if the first name is empty.
     *
     * @return The user's full name.
     */
    public String getFullName() {
        if (firstName == null || firstName.isEmpty())
            return getEmailAddress();
        else
            return firstName + (lastName == null ? "" : " " + lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (!(o instanceof User)) return false;

        User other = (User) o;

        return EqualsHelper.areEquals(emailAddress, other.emailAddress)
                && EqualsHelper.areEquals(firstName, other.firstName)
                && EqualsHelper.areEquals(lastName, other.lastName)
                && EqualsHelper.areEquals(phoneNumber, other.phoneNumber)
                && EqualsHelper.areEquals(location, other.location)
                && EqualsHelper.areEquals(hashedPassword, other.hashedPassword);
    }

    @Override
    public int hashCode() {
        return EqualsHelper.calculateHashCode(emailAddress, firstName, lastName, phoneNumber, location, hashedPassword);
    }

}
