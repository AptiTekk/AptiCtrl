/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.rest.dtos;

public class UserDTO {

    public Long id;

    public String username;

    public static class WithNewPassword extends UserDTO {
        public String newPassword;
    }

}

