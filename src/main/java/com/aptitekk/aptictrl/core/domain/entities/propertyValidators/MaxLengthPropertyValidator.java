/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.entities.propertyValidators;

public class MaxLengthPropertyValidator extends PropertyValidator {

    private int maxLength;

    public MaxLengthPropertyValidator(int maxLength) {
        this(maxLength, "This may only be " + maxLength + " characters long.");
    }

    public MaxLengthPropertyValidator(int maxLength, String validationFailedMessage) {
        super(validationFailedMessage);
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(String inputValue) {
        return inputValue.length() <= maxLength;
    }
}
