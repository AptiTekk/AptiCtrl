import {ModuleWithProviders} from "@angular/core";
import {RouterModule} from "@angular/router";
import {FrontPageComponent, SignInComponent, SecurePageComponent, DashboardPageComponent} from "../page-components";
import {FrontPageGuard, SecureGuard} from "./guards";

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