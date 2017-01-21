/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {HerokuDomain} from "./heroku-domain.model";
export interface HerokuApp {

    id?: string;

    name?: string;

    domainName?: HerokuDomain;

    createdAt?: string;

    createStatus?: string;

    ownerEmail?: string;

    webUrl?: string;

    stack?: string;

    requestedStack?: string;

    repoMigrateStatus?: string;

    gitUrl?: string;

    buildpackProvidedDescription?: string;

    releasedAt?: Date;

    slugSize?: number;

    repoSize?: number;

    dynos?: number;

    workers?: number;

    maintenanceModeStatus?: boolean;

}