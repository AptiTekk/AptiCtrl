/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {ModuleWithProviders} from "@angular/core";
import {RouterModule} from "@angular/router";
import {FrontPageComponent, SignInComponent, SecurePageComponent, DashboardPageComponent} from "../page-components";
import {FrontPageGuard, SecureGuard} from "./guards";
import {HerokuAppPageComponent} from "../page-components/secure-page/heroku-app-page/heroku-app-page.component";

export const routes: ModuleWithProviders = RouterModule.forRoot([
    {
        path: 'secure',
        component: SecurePageComponent,
        children: [
            {
                path: 'dashboard',
                component: DashboardPageComponent
            },
            {
                path: 'app/:appName',
                component: HerokuAppPageComponent
            },
            {
                path: '**',
                redirectTo: 'dashboard'
            }
        ],
        canActivate: [SecureGuard]
    },
    {
        path: '',
        component: FrontPageComponent,
        children: [
            {
                path: 'sign-in',
                component: SignInComponent
            },
            {
                path: '**',
                redirectTo: 'sign-in'
            }
        ],
        canActivate: [FrontPageGuard]
    },
    {
        path: '**',
        redirectTo: ''
    }
]);