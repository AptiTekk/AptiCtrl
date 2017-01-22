/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

export interface HerokuDyno {

    id: string;

    attach_url?: any;

    command: string;

    created_at: Date;

    name: string;

    app: { id: string, name: string };

    release: { id: string, version: number };

    size: string;

    state: string;

    type: string;

    updated_at: Date;

}