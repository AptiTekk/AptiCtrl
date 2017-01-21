/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.entities;


import com.aptitekk.aptictrl.ApplicationContextProvider;
import com.aptitekk.aptictrl.core.domain.entities.propertyValidators.BooleanPropertyValidator;
import com.aptitekk.aptictrl.core.domain.entities.propertyValidators.MaxLengthPropertyValidator;
import com.aptitekk.aptictrl.core.domain.entities.propertyValidators.PropertyValidator;
import com.aptitekk.aptictrl.core.domain.entities.propertyValidators.TimeZonePropertyValidator;
import com.aptitekk.aptictrl.core.util.EqualsHelper;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.context.ApplicationContext;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
public class Property implements Serializable {

    public enum Group {

        PERSONALIZATION("Personalization", null),
        REGISTRATION("Registration", null),
        GOOGLE_SIGN_IN("Google Sign In", null),
        DATE_TIME("Date And Time", null);

        private String friendlyName;
        private Class<? extends ChangeListener> propertyGroupChangeListenerClass;

        Group(String friendlyName, Class<? extends ChangeListener> propertyGroupChangeListenerClass) {
            this.friendlyName = friendlyName;
            this.propertyGroupChangeListenerClass = propertyGroupChangeListenerClass;
        }

        public String getFriendlyName() {
            return friendlyName;
        }

        public List<Key> getKeys() {
            Key[] allKeys = Key.values();
            List<Key> groupKeys = new ArrayList<>();
            for (Key key : allKeys) {
                if (key.getGroup().equals(this))
                    groupKeys.add(key);
            }

            return groupKeys;
        }

        public void firePropertiesChangedEvent() {
            if (propertyGroupChangeListenerClass != null) {
                ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
                ChangeListener bean = applicationContext.getBean(propertyGroupChangeListenerClass);
                bean.onPropertiesChanged(this);
            }
        }

        public interface ChangeListener {
            void onPropertiesChanged(Group propertyGroup);
        }
    }

    public enum Key {

        PERSONALIZATION_ORGANIZATION_NAME("Organization Name", null, Group.PERSONALIZATION, new MaxLengthPropertyValidator(64)),

        REGISTRATION_ENABLED("User Registration Enabled", "true", Group.REGISTRATION, new BooleanPropertyValidator()),

        GOOGLE_SIGN_IN_ENABLED("Google Sign-In Enabled", "false", Group.GOOGLE_SIGN_IN, new BooleanPropertyValidator()),
        GOOGLE_SIGN_IN_WHITELIST("Allowed Google Sign-In Domain Names (Comma Separated)", "gmail.com, example.org", Group.GOOGLE_SIGN_IN, new MaxLengthPropertyValidator(256)),

        DATE_TIME_TIMEZONE("Timezone", "America/Denver", Group.DATE_TIME, new TimeZonePropertyValidator());

        private String fieldLabel;
        private final String defaultValue;
        private final Group group;
        private PropertyValidator propertyValidator;

        Key(String fieldLabel, String defaultValue, Group group, PropertyValidator propertyValidator) {
            this.fieldLabel = fieldLabel;
            this.defaultValue = defaultValue;
            this.group = group;
            this.propertyValidator = propertyValidator;
        }

        public String getFieldLabel() {
            return fieldLabel;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public Group getGroup() {
            return group;
        }

        public PropertyValidator getPropertyValidator() {
            return propertyValidator;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Key propertyKey;

    private String propertyValue;

    private static final long serialVersionUID = 1L;

    public Key getPropertyKey() {
        return this.propertyKey;
    }

    public void setPropertyKey(Key key) {
        this.propertyKey = key;
    }

    public String getPropertyValue() {
        return this.propertyValue;
    }

    public void setPropertyValue(String value) {
        this.propertyValue = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldLabel() {
        return this.propertyKey.getFieldLabel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (!(o instanceof Property)) return false;

        Property other = (Property) o;

        return EqualsHelper.areEquals(getPropertyKey(), other.getPropertyKey())
                && EqualsHelper.areEquals(getPropertyValue(), other.getPropertyValue());
    }

    @Override
    public int hashCode() {
        return EqualsHelper.calculateHashCode(getPropertyKey(), getPropertyValue());
    }
}
