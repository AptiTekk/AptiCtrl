/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
export interface HerokuApp {

    id: string;

    archived_at?: any;

    buildpack_provided_description: string;

    build_stack: HerokuAppBuildStack;

    created_at: Date;

    git_url: string;

    maintenance: boolean;

    name: string;

    owner: HerokuAppOwner;

    region: HerokuAppRegion;

    organization?: any;

    space?: any;

    released_at: Date;

    repo_size: number;

    slug_size: number;

    stack: HerokuAppStack;

    updated_at: Date;

    web_url: string;

}

export interface HerokuAppBuildStack {

    id: string;

    name: string;

}

export interface HerokuAppOwner {

    id: string;

    email: string;

}

export interface HerokuAppRegion {

    id: string;

    name: string;

}

export interface HerokuAppStack {

    id: string;

    name: string;

}