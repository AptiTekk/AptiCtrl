/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {HerokuApp} from "./heroku-app.model";

export interface HerokuRelease {

    id: string;

    addon_plan_names: string[];

    app: HerokuApp;

    created_at: Date;

    description: string;

    status: string;

    slug: { id: string };

    updated_at: Date;

    user: { email: string, id: string };

    version: number;

    current: boolean;

}