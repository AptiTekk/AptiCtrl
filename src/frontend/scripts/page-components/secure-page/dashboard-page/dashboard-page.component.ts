/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, trigger, state, style, transition, animate, OnInit} from "@angular/core";
import {APIService} from "../../../services/singleton/api.service";
import {AuthService} from "../../../services/singleton/auth.service";
import {User} from "../../../models/user.model";
import {HerokuService} from "../../../services/singleton/heroku.service";
import {HerokuApp} from "../../../models/heroku-app.model";

@Component({
    selector: 'dashboard-page',
    templateUrl: 'dashboard-page.component.html',
    styleUrls: ['dashboard-page.component.css']
})
export class DashboardPageComponent implements OnInit {

    currentUser: User;
    private apps: HerokuApp[];

    constructor(protected apiService: APIService,
                private authService: AuthService,
                private herokuService: HerokuService) {
    }

    ngOnInit(): void {
        this.authService.getUser().subscribe(user => this.currentUser = user);
        this.herokuService.getApps().subscribe(apps => {
            this.apps = apps;
            this.apps.forEach(app => {
                this.herokuService
                    .isAppInMaintenanceMode(app)
                    .subscribe(
                        status => app.maintenanceModeStatus = status
                    )
            });
        });
    }

    onVisitAppUrl(app: HerokuApp) {
        let win = window.open(app.webUrl, '_blank');
        if (win) {
            win.focus();
        } else {
            alert('The App URL could not be opened. Please allow popups for this website.');
        }
    }
}