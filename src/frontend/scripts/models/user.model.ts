/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

export interface User {
    id: number;

    username: string;

    /**
     * New Password - not sent by server, but is accepted by server.
     */
    newPassword: string;

    /**
     * Confirmation password - for client use only, not sent or accepted by server.
     */
    confirmPassword: string;
}