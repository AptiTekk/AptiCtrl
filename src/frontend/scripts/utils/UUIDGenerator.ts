/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

const uuidV4 = require('uuid/v4');

export class UUIDGenerator {

    public static generateUUID(): string {
        return uuidV4();
    }

}